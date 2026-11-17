package com.skd.carrymechanics.scripting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;

public record CarryScript(
        int priority,
        ScriptObject object,
        ScriptConditions conditions,
        ScriptRender render,
        ScriptEffects effects
) {
    public static final Codec<CarryScript> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("priority", 0).forGetter(CarryScript::priority),
                    ScriptObject.CODEC.fieldOf("object").forGetter(CarryScript::object),
                    ScriptConditions.CODEC.optionalFieldOf("conditions", ScriptConditions.EMPTY).forGetter(CarryScript::conditions),
                    ScriptRender.CODEC.optionalFieldOf("render", ScriptRender.EMPTY).forGetter(CarryScript::render),
                    ScriptEffects.CODEC.optionalFieldOf("effects", ScriptEffects.EMPTY).forGetter(CarryScript::effects)
            ).apply(instance, CarryScript::new));

    public boolean fulfillsConditions(ServerPlayer player) {
        return conditions.matches(player);
    }

    public record ScriptObject(
            String name,
            String matchExpression,
            ObjectType type
    ) {
        public static final Codec<ScriptObject> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("name").forGetter(ScriptObject::name),
                        Codec.STRING.fieldOf("match").forGetter(ScriptObject::matchExpression),
                        ObjectType.CODEC.fieldOf("type").forGetter(ScriptObject::type)
                ).apply(instance, ScriptObject::new));
    }

    public enum ObjectType {
        BLOCK, ENTITY;
        public static final Codec<ObjectType> CODEC = Codec.STRING.xmap(
                name -> name.equalsIgnoreCase("entity") ? ENTITY : BLOCK,
                type -> type.name().toLowerCase());
    }

    public record ScriptConditions(
            String gamestage,
            String advancement,
            int xpLevel,
            int gamemode,
            String scoreboard,
            String nbt,
            boolean isEmpty
    ) {
        public static final ScriptConditions EMPTY = new ScriptConditions("", "", 0, -1, "", "", true);
        public static final Codec<ScriptConditions> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.optionalFieldOf("gamestage", "").forGetter(ScriptConditions::gamestage),
                        Codec.STRING.optionalFieldOf("advancement", "").forGetter(ScriptConditions::advancement),
                        Codec.INT.optionalFieldOf("xpLevel", 0).forGetter(ScriptConditions::xpLevel),
                        Codec.INT.optionalFieldOf("gamemode", -1).forGetter(ScriptConditions::gamemode),
                        Codec.STRING.optionalFieldOf("scoreboard", "").forGetter(ScriptConditions::scoreboard),
                        Codec.STRING.optionalFieldOf("nbt", "").forGetter(ScriptConditions::nbt)
                ).apply(instance, (gs, adv, xp, gm, sb, nbt) ->
                        new ScriptConditions(gs, adv, xp, gm, sb, nbt, false)));
        public boolean matches(ServerPlayer player) {
            return isEmpty || true;
        }
    }

    public record ScriptRender(
            String nameOverride,
            String type,
            int renderLeftArm,
            int renderLeftLeg,
            int renderRightArm,
            int renderRightLeg,
            int renderHead,
            int renderBody,
            int renderCape,
            int renderScale
    ) {
        public static final ScriptRender EMPTY = new ScriptRender("", "item", 0, 0, 0, 0, 0, 0, 0, 100);
        public static final Codec<ScriptRender> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.optionalFieldOf("nameOverride", "").forGetter(ScriptRender::nameOverride),
                        Codec.STRING.optionalFieldOf("type", "item").forGetter(ScriptRender::type),
                        Codec.INT.optionalFieldOf("renderLeftArm", 0).forGetter(ScriptRender::renderLeftArm),
                        Codec.INT.optionalFieldOf("renderLeftLeg", 0).forGetter(ScriptRender::renderLeftLeg),
                        Codec.INT.optionalFieldOf("renderRightArm", 0).forGetter(ScriptRender::renderRightArm),
                        Codec.INT.optionalFieldOf("renderRightLeg", 0).forGetter(ScriptRender::renderRightLeg),
                        Codec.INT.optionalFieldOf("renderHead", 0).forGetter(ScriptRender::renderHead),
                        Codec.INT.optionalFieldOf("renderBody", 0).forGetter(ScriptRender::renderBody),
                        Codec.INT.optionalFieldOf("renderCape", 0).forGetter(ScriptRender::renderCape),
                        Codec.INT.optionalFieldOf("renderScale", 100).forGetter(ScriptRender::renderScale)
                ).apply(instance, ScriptRender::new));
    }

    public record ScriptEffects(
            String commandInit,
            String commandLoop,
            String commandPlace
    ) {
        public static final ScriptEffects EMPTY = new ScriptEffects("", "", "");
        public static final Codec<ScriptEffects> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.optionalFieldOf("commandInit", "").forGetter(ScriptEffects::commandInit),
                        Codec.STRING.optionalFieldOf("commandLoop", "").forGetter(ScriptEffects::commandLoop),
                        Codec.STRING.optionalFieldOf("commandPlace", "").forGetter(ScriptEffects::commandPlace)
                ).apply(instance, ScriptEffects::new));
    }
}