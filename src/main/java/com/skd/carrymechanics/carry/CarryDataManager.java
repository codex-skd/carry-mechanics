package com.skd.carrymechanics.carry;

import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CarryDataManager {

    private static final Map<UUID, CarryData> DATA = new ConcurrentHashMap<>();

    public static CarryData getCarryData(Player player) {
        return DATA.computeIfAbsent(player.getUUID(), u -> new CarryData());
    }

    public static CarryData get(UUID uuid) {
        return DATA.computeIfAbsent(uuid, u -> new CarryData());
    }

    public static void setCarryData(Player player, CarryData data) {
        data.setSelected(player.getInventory().getSelectedSlot());
        data.setTick(player.tickCount);
        DATA.put(player.getUUID(), data);
        if (!player.level().isClientSide()) {
            CarryDataSyncHandler.broadcast((net.minecraft.server.level.ServerPlayer) player);
        }
    }

    public static void set(UUID uuid, CarryData data) {
        DATA.put(uuid, data);
    }

    public static void remove(UUID uuid) {
        DATA.remove(uuid);
    }

    public static Iterable<Map.Entry<UUID, CarryData>> entrySet() {
        return DATA.entrySet();
    }
}
