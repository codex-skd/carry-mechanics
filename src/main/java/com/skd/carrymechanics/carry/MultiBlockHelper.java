package com.skd.carrymechanics.carry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Detection helpers for blocks made of two vertically stacked halves.
 * <p>
 * A half-like property is the vanilla {@link BlockStateProperties#DOUBLE_BLOCK_HALF}, a
 * boolean property named bottom/top/lower/upper, or an enum property whose serialized
 * values are exactly lower/upper or bottom/top.
 */
public final class MultiBlockHelper {

    private static final Set<String> LOWER_UPPER = Set.of("lower", "upper");
    private static final Set<String> BOTTOM_TOP = Set.of("bottom", "top");

    private MultiBlockHelper() {
    }

    /** The property that identifies the half, plus whether the state is the lower one. */
    public static final class HalfInfo {
        private final Property<?> property;
        private final boolean lower;

        private HalfInfo(Property<?> property, boolean lower) {
            this.property = property;
            this.lower = lower;
        }

        public Property<?> getProperty() {
            return property;
        }

        public boolean isLower() {
            return lower;
        }
    }

    /** Both halves of a vertical pair. Positions are immutable. */
    public static final class Pair {
        private final BlockPos lowerPos;
        private final BlockPos upperPos;
        private final BlockState lowerState;
        private final BlockState upperState;

        private Pair(BlockPos lowerPos, BlockPos upperPos, BlockState lowerState, BlockState upperState) {
            this.lowerPos = lowerPos;
            this.upperPos = upperPos;
            this.lowerState = lowerState;
            this.upperState = upperState;
        }

        public BlockPos getLowerPos() {
            return lowerPos;
        }

        public BlockPos getUpperPos() {
            return upperPos;
        }

        public BlockState getLowerState() {
            return lowerState;
        }

        public BlockState getUpperState() {
            return upperState;
        }
    }

    public static boolean hasHalfLikeProperty(BlockState state) {
        return getHalfInfo(state) != null;
    }

    public static boolean isLowerHalf(BlockState state) {
        HalfInfo info = getHalfInfo(state);
        return info != null && info.isLower();
    }

    /**
     * Finds the vertical partner of the block at {@code pos}, if any. A lower half only
     * looks above and an upper half only looks below, and the partner must have the
     * opposite half value with every other property equal. This prevents two machines
     * stacked directly on top of each other from cross-matching.
     */
    public static Optional<Pair> findVerticalPair(BlockGetter level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        HalfInfo info = getHalfInfo(state);
        if (info == null) return Optional.empty();

        BlockPos partnerPos = info.isLower() ? pos.above() : pos.below();
        BlockState partnerState = level.getBlockState(partnerPos);
        if (!isMatchingPartner(state, info, partnerState)) return Optional.empty();

        BlockPos immutablePos = pos.immutable();
        BlockPos immutablePartner = partnerPos.immutable();
        if (info.isLower()) {
            return Optional.of(new Pair(immutablePos, immutablePartner, state, partnerState));
        }
        return Optional.of(new Pair(immutablePartner, immutablePos, partnerState, state));
    }

    /**
     * Returns true when the block is considered "large": a single block whose visual shape
     * bounds are wider than 1.0 in X or Z or taller than 1.0 in Y and it does not form a
     * vertical half pair (collision-only extras like fence/wall posts are ignored because
     * getShape() returns the visual/outline shape), or a horizontal multi-part pair such as a
     * bed (head/foot-like properties). Double chests (left/right chest types) are not large.
     */
    public static boolean isLargeBlock(Level level, BlockPos pos, BlockState state) {
        if (findVerticalPair(level, pos).isPresent()) return false;

        if (hasHorizontalHeadFootPair(state)) return true;

        try {
            VoxelShape shape = state.getShape(level, pos);
            if (!shape.isEmpty()) {
                AABB bounds = shape.bounds();
                if (bounds.maxX - bounds.minX > 1.0 || bounds.maxZ - bounds.minZ > 1.0 || bounds.maxY - bounds.minY > 1.0) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Detects horizontal head/foot pairs generically (e.g. vanilla beds). Any enum property
     * whose serialized values include "head"/"foot", or a boolean property named head/foot,
     * is treated as a horizontal pair. Left/right-style properties (double chests) do not
     * match and remain non-large.
     */
    private static boolean hasHorizontalHeadFootPair(BlockState state) {
        if (state.hasProperty(BedBlock.PART)) return true;
        for (Property<?> prop : state.getProperties()) {
            if (prop instanceof EnumProperty<?> enumProperty) {
                boolean hasHead = false;
                boolean hasFoot = false;
                for (Object value : enumProperty.getPossibleValues()) {
                    String name = ((StringRepresentable) value).getSerializedName();
                    if ("head".equals(name)) hasHead = true;
                    if ("foot".equals(name)) hasFoot = true;
                }
                if (hasHead && hasFoot) return true;
            } else if (prop instanceof BooleanProperty booleanProperty) {
                String name = booleanProperty.getName();
                if ("head".equals(name) || "foot".equals(name)) return true;
            }
        }
        return false;
    }

    /** Returns the half information of a state, or {@code null} when it is not half-like. */
    public static HalfInfo getHalfInfo(BlockState state) {
        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
            DoubleBlockHalf value = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            return new HalfInfo(BlockStateProperties.DOUBLE_BLOCK_HALF, value == DoubleBlockHalf.LOWER);
        }

        for (Property<?> prop : state.getProperties()) {
            HalfInfo info = getHalfInfo(state, prop);
            if (info != null) return info;
        }
        return null;
    }

    private static HalfInfo getHalfInfo(BlockState state, Property<?> prop) {
        if (prop instanceof BooleanProperty booleanProperty) {
            String name = booleanProperty.getName();
            boolean value = state.getValue(booleanProperty);
            if (name.equals("bottom") || name.equals("lower")) return new HalfInfo(prop, value);
            if (name.equals("top") || name.equals("upper")) return new HalfInfo(prop, !value);
            return null;
        }

        if (prop instanceof EnumProperty<?> enumProperty) {
            Set<String> names = new HashSet<>();
            for (Object value : enumProperty.getPossibleValues()) {
                names.add(((StringRepresentable) value).getSerializedName());
            }
            if (!names.equals(LOWER_UPPER) && !names.equals(BOTTOM_TOP)) return null;

            Object current = state.getValue((Property) enumProperty);
            String currentName = ((StringRepresentable) current).getSerializedName();
            boolean lower = currentName.equals("lower") || currentName.equals("bottom");
            return new HalfInfo(prop, lower);
        }

        return null;
    }

    private static boolean isMatchingPartner(BlockState state, HalfInfo info, BlockState partner) {
        if (partner.getBlock() != state.getBlock()) return false;

        HalfInfo partnerInfo = getHalfInfo(partner);
        if (partnerInfo == null) return false;
        if (partnerInfo.isLower() == info.isLower()) return false;

        return sameOtherProperties(state, info.getProperty(), partner, partnerInfo.getProperty());
    }

    private static boolean sameOtherProperties(BlockState state, Property<?> stateHalf,
                                               BlockState partner, Property<?> partnerHalf) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.equals(stateHalf)) continue;
            if (!partner.hasProperty(prop)) return false;
            if (!partner.getValue(prop).equals(state.getValue(prop))) return false;
        }
        for (Property<?> prop : partner.getProperties()) {
            if (prop.equals(partnerHalf)) continue;
            if (!state.hasProperty(prop)) return false;
        }
        return true;
    }
}
