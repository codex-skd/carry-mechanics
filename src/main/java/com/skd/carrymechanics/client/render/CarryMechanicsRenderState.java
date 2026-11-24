package com.skd.carrymechanics.client.render;

import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.world.entity.player.Player;

public interface CarryMechanicsRenderState {
    CarryData carry_mechanics$getCarryData();
    void carry_mechanics$setCarryData(CarryData data);
    Player carry_mechanics$getPlayer();
    void carry_mechanics$setPlayer(Player player);
}
