package com.skd.carrymechanics.carry;

import net.minecraft.world.entity.player.Player;

public class CarryDataManager {

    public static CarryData getCarryData(Player player) {
        var attachment = player.getData(CarryMechanicsAccess.getAttachmentType());
        return attachment;
    }

    public static void setCarryData(Player player, CarryData data) {
        data.setSelected(player.getInventory().selected);
        data.setTick(player.tickCount);
        player.setData(CarryMechanicsAccess.getAttachmentType(), data);
    }
}