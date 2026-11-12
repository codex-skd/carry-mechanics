package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.client.render.CarryMechanicsRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class PlayerRenderStateMixin implements CarryMechanicsRenderState {
    @Unique private CarryData carry_mechanics$data;
    @Unique private Player carry_mechanics$player;

    @Override
    public CarryData carry_mechanics$getCarryData() { return carry_mechanics$data; }
    @Override
    public void carry_mechanics$setCarryData(CarryData data) { this.carry_mechanics$data = data; }
    @Override
    public Player carry_mechanics$getPlayer() { return carry_mechanics$player; }
    @Override
    public void carry_mechanics$setPlayer(Player player) { this.carry_mechanics$player = player; }
}
