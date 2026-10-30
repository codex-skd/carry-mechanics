package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.scripting.CarryScript;
import com.skd.carrymechanics.scripting.ScriptManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundSyncScriptsPacket(Tag serialized) {

    // FriendlyByteBuf only writes CompoundTags, the script list is a ListTag: wrap it.
    public void encode(FriendlyByteBuf buf) {
        CompoundTag wrapper = new CompoundTag();
        wrapper.put("scripts", serialized);
        buf.writeNbt(wrapper);
    }

    public static ClientboundSyncScriptsPacket decode(FriendlyByteBuf buf) {
        CompoundTag wrapper = buf.readNbt();
        return new ClientboundSyncScriptsPacket(wrapper != null ? wrapper.get("scripts") : null);
    }

    public static void handle(ClientboundSyncScriptsPacket packet, Supplier<NetworkEvent.Context> context) {
        if (packet.serialized() != null) {
            var scripts = CarryScript.CODEC.listOf()
                    .parse(NbtOps.INSTANCE, packet.serialized())
                    .getOrThrow(false, msg -> { throw new RuntimeException("Script sync: " + msg); });
            ScriptManager.setScripts(scripts);
        }
        context.get().setPacketHandled(true);
    }
}
