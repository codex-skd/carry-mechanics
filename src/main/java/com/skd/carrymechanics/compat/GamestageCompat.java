package com.skd.carrymechanics.compat;

public class GamestageCompat {

    public static boolean isLoaded() {
        try {
            Class.forName("net.darkhax.gamestages.GameStageHelper");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean hasStage(Object player, String stage) {
        try {
            Class<?> helper = Class.forName("net.darkhax.gamestages.GameStageHelper");
            var method = helper.getMethod("hasStage", net.minecraft.world.entity.player.Player.class, String.class);
            return (boolean) method.invoke(null, player, stage);
        } catch (Exception e) {
            return false;
        }
    }
}