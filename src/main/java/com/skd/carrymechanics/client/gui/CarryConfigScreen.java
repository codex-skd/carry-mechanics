package com.skd.carrymechanics.client.gui;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.config.CarryConfig;
import com.skd.carrymechanics.config.ConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Forge 1.20.1 has no auto-generated config screen (NeoForge's ConfigurationScreen),
 * so this lists every Carry Mechanics config value with a toggle or a text field.
 */
public class CarryConfigScreen extends Screen {

    private static final String LANG_PREFIX = CarryMechanics.MODID + ".configuration.";

    private final Screen parent;
    private final List<PendingValue<?>> pending = new ArrayList<>();
    private ConfigList list;

    public CarryConfigScreen(Screen parent) {
        super(Component.translatable(LANG_PREFIX + "title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        pending.clear();
        list = new ConfigList(minecraft, width, height, 32, height - 32, 24);

        list.addHeader(Component.translatable(LANG_PREFIX + "section.carry_mechanics.common.toml"));
        addDouble(ConfigData.COMMON_MAX_DISTANCE);
        addDouble(ConfigData.COMMON_MAX_ENTITY_HEIGHT);
        addDouble(ConfigData.COMMON_MAX_ENTITY_WIDTH);
        addDouble(ConfigData.COMMON_BLOCK_SLOWNESS_MULTIPLIER);
        addDouble(ConfigData.COMMON_ENTITY_SLOWNESS_MULTIPLIER);
        addBoolean(ConfigData.COMMON_HEAVY_TILES);
        addBoolean(ConfigData.COMMON_HEAVY_ENTITIES);
        addBoolean(ConfigData.COMMON_PICKUP_ALL_BLOCKS);
        addBoolean(ConfigData.COMMON_PICKUP_HOSTILE_MOBS);
        addBoolean(ConfigData.COMMON_SLOWNESS_IN_CREATIVE);
        addBoolean(ConfigData.COMMON_ALLOW_BABIES);
        addBoolean(ConfigData.COMMON_STACKABLE_ENTITIES);
        addInt(ConfigData.COMMON_MAX_ENTITY_STACK_LIMIT);
        addBoolean(ConfigData.COMMON_HIT_WHILE_CARRYING);
        addBoolean(ConfigData.COMMON_DROP_CARRIED_WHEN_HIT);
        addBoolean(ConfigData.COMMON_PICKUP_UNBREAKABLE_BLOCKS);
        addBoolean(ConfigData.COMMON_USE_SCRIPTS);
        addBoolean(ConfigData.COMMON_ENTITY_SIZE_MATTERS_STACKING);
        addBoolean(ConfigData.COMMON_USE_WHITELIST_BLOCKS);
        addBoolean(ConfigData.COMMON_USE_WHITELIST_ENTITIES);
        addBoolean(ConfigData.COMMON_USE_WHITELIST_STACKING);

        list.addHeader(Component.translatable(LANG_PREFIX + "section.carry_mechanics.client.toml"));
        addBoolean(ConfigData.CLIENT_RENDER_ARMS);
        addBoolean(ConfigData.CLIENT_RENDER_NAME_TAG);

        addRenderableWidget(list);
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> onClose())
                .bounds(width / 2 - 100, height - 27, 200, 20).build());
    }

    private void addBoolean(ForgeConfigSpec.BooleanValue value) {
        PendingValue<Boolean> entry = new PendingValue<>(value, safeGet(value, false));
        pending.add(entry);
        Button button = Button.builder(onOff(entry.current), b -> {
            entry.current = !entry.current;
            b.setMessage(onOff(entry.current));
        }).bounds(0, 0, 80, 20).build();
        list.addValue(label(value), button);
    }

    private void addInt(ForgeConfigSpec.IntValue value) {
        addNumber(value, safeGet(value, 0), Integer::parseInt);
    }

    private void addDouble(ForgeConfigSpec.DoubleValue value) {
        addNumber(value, safeGet(value, 0.0), Double::parseDouble);
    }

    private <T extends Comparable<? super T>> void addNumber(ForgeConfigSpec.ConfigValue<T> value, T initial,
                                                            Function<String, T> parser) {
        PendingValue<T> entry = new PendingValue<>(value, initial);
        pending.add(entry);
        EditBox box = new EditBox(font, 0, 0, 78, 18, label(value));
        box.setValue(String.valueOf(initial));
        box.setResponder(text -> {
            try {
                T parsed = parser.apply(text.trim());
                if (isValid(value, parsed)) {
                    entry.current = parsed;
                    box.setTextColor(0xE0E0E0);
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
            box.setTextColor(0xFF5555);
        });
        list.addValue(label(value), box);
    }

    private static boolean isValid(ForgeConfigSpec.ConfigValue<?> value, Object candidate) {
        for (ForgeConfigSpec spec : List.of(CarryConfig.COMMON_SPEC, CarryConfig.CLIENT_SPEC)) {
            Object valueSpec = spec.getSpec().get(value.getPath());
            if (valueSpec instanceof ForgeConfigSpec.ValueSpec vs) return vs.test(candidate);
        }
        return true;
    }

    private static <T> T safeGet(ForgeConfigSpec.ConfigValue<T> value, T fallback) {
        try {
            return value.get();
        } catch (IllegalStateException e) {
            // Config not loaded yet (e.g. opened from the main menu before any world).
            return value.getDefault() != null ? value.getDefault() : fallback;
        }
    }

    private static Component onOff(boolean on) {
        return on ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF;
    }

    private static Component label(ForgeConfigSpec.ConfigValue<?> value) {
        String name = value.getPath().get(value.getPath().size() - 1);
        String key = LANG_PREFIX + name;
        return I18n.exists(key) ? Component.translatable(key) : Component.literal(name);
    }

    @Override
    public void onClose() {
        boolean commonChanged = false;
        boolean clientChanged = false;
        for (PendingValue<?> entry : pending) {
            if (entry.apply()) {
                if (entry.value.getPath().get(0).equals("client")) clientChanged = true;
                else commonChanged = true;
            }
        }
        if (commonChanged) saveSpec(CarryConfig.COMMON_SPEC);
        if (clientChanged) saveSpec(CarryConfig.CLIENT_SPEC);
        minecraft.setScreen(parent);
    }

    private static void saveSpec(ForgeConfigSpec spec) {
        try {
            if (spec.isLoaded()) spec.save();
        } catch (Exception e) {
            CarryMechanics.LOGGER.warn("Could not save Carry Mechanics config", e);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFF);
    }

    private static final class PendingValue<T> {
        final ForgeConfigSpec.ConfigValue<T> value;
        final T initial;
        T current;

        PendingValue(ForgeConfigSpec.ConfigValue<T> value, T initial) {
            this.value = value;
            this.initial = initial;
            this.current = initial;
        }

        boolean apply() {
            if (current == null || current.equals(initial)) return false;
            try {
                value.set(current);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private class ConfigList extends ContainerObjectSelectionList<ConfigList.Entry> {

        ConfigList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
            super(minecraft, width, height, y0, y1, itemHeight);
        }

        void addHeader(Component text) {
            addEntry(new Entry(text, null, true));
        }

        void addValue(Component text, AbstractWidget widget) {
            addEntry(new Entry(text, widget, false));
        }

        @Override
        public int getRowWidth() {
            return 320;
        }

        @Override
        protected int getScrollbarPosition() {
            return width / 2 + 170;
        }

        class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            private final Component text;
            private final AbstractWidget widget;
            private final boolean header;

            Entry(Component text, AbstractWidget widget, boolean header) {
                this.text = text;
                this.widget = widget;
                this.header = header;
            }

            @Override
            public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height,
                               int mouseX, int mouseY, boolean hovering, float partialTick) {
                if (header) {
                    guiGraphics.drawCenteredString(font, text, left + width / 2, top + 6, 0xFFFF55);
                    return;
                }
                guiGraphics.drawString(font, text, left, top + 6, 0xFFFFFF);
                widget.setX(left + width - 82);
                widget.setY(top + 1);
                widget.render(guiGraphics, mouseX, mouseY, partialTick);
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return widget == null ? List.of() : List.of(widget);
            }

            @Override
            public List<? extends NarratableEntry> narratables() {
                return widget == null ? List.of() : List.of(widget);
            }
        }
    }
}
