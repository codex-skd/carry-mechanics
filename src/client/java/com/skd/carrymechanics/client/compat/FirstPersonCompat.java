package com.skd.carrymechanics.client.compat;

import com.skd.carrymechanics.carry.CarryMechanicsAccess;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.Method;

/**
 * Optional compatibility with the "First Person Model" mod (modid "firstperson").
 * First Person is not on the compile classpath, so everything is accessed through
 * reflection and every call is fail-safe: on any error the compat layer reports
 * "not active" and the logger is used at most once.
 */
public final class FirstPersonCompat {

    private static final String MOD_ID = "firstperson";
    private static final String API_CLASS = "dev.tr7zw.firstperson.api.FirstPersonAPI";

    private static Boolean loaded;
    private static boolean methodsResolved;
    private static Method isEnabledMethod;
    private static Method isRenderingPlayerMethod;
    private static boolean errorLogged;

    private FirstPersonCompat() {
    }

    public static boolean isLoaded() {
        if (loaded == null) {
            boolean result;
            try {
                result = FabricLoader.getInstance().isModLoaded(MOD_ID);
            } catch (Throwable t) {
                logOnce(t);
                result = false;
            }
            loaded = result;
        }
        return loaded;
    }

    public static boolean isActive() {
        if (!isLoaded()) return false;
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.getCameraType() != CameraType.FIRST_PERSON) return false;
        } catch (Throwable t) {
            logOnce(t);
            return false;
        }
        return Boolean.TRUE.equals(invoke(resolveIsEnabled()));
    }

    public static boolean isRenderingBody() {
        if (!isLoaded()) return false;
        return Boolean.TRUE.equals(invoke(resolveIsRenderingPlayer()));
    }

    /**
     * True when carry pose/arm changes must be skipped: First Person Model is active and the
     * given player is the local player in first person. In that case mutating the pose or the
     * arm rotations makes the first person body jitter or fills the camera with the arms.
     * Never throws.
     */
    public static boolean shouldSkipCarryPoseFor(Player player) {
        if (player == null) return false;
        if (!isLoaded()) return false;
        try {
            if (player != Minecraft.getInstance().player) return false;
        } catch (Throwable t) {
            logOnce(t);
            return false;
        }
        return isActive();
    }

    private static void resolveMethods() {
        if (methodsResolved) return;
        methodsResolved = true;
        try {
            Class<?> api = Class.forName(API_CLASS);
            isEnabledMethod = api.getMethod("isEnabled");
            isRenderingPlayerMethod = api.getMethod("isRenderingPlayer");
        } catch (Throwable t) {
            logOnce(t);
        }
    }

    private static Method resolveIsEnabled() {
        resolveMethods();
        return isEnabledMethod;
    }

    private static Method resolveIsRenderingPlayer() {
        resolveMethods();
        return isRenderingPlayerMethod;
    }

    private static Object invoke(Method method) {
        if (method == null) return null;
        try {
            return method.invoke(null);
        } catch (Throwable t) {
            logOnce(t);
            return null;
        }
    }

    private static void logOnce(Throwable t) {
        if (errorLogged) return;
        errorLogged = true;
        CarryMechanicsAccess.LOGGER.warn("First Person Model compatibility is unavailable; " +
                "falling back to the default first person render.", t);
    }
}
