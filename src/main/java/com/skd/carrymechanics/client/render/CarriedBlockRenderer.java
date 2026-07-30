package com.skd.carrymechanics.client.render;

import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class CarriedBlockRenderer {

    private CarriedBlockRenderer() {
    }

    public static ItemStack resolveStack(CarryData data) {
        if (data == null || !data.isCarrying(CarryData.CarryType.BLOCK)) return ItemStack.EMPTY;
        BlockState blockState = data.getBlock();
        if (blockState.isAir()) return ItemStack.EMPTY;
        return new ItemStack(blockState.getBlock().asItem());
    }

    public static void resolve(ItemStackRenderState output, CarryData data, ItemDisplayContext context, LivingEntity entity) {
        ItemStack stack = resolveStack(data);
        if (stack.isEmpty()) {
            output.clear();
            return;
        }
        Minecraft.getInstance().getItemModelResolver().updateForLiving(output, stack, context, entity);
    }
}
