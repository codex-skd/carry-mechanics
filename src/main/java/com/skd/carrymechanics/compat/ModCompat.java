package com.skd.carrymechanics.compat;

import com.skd.carrymechanics.carry.ModelOverrideHandler;

public class ModCompat {

    public static void handleMessage(String type, String data) {
        switch (type) {
            case "blacklistBlock":
            case "whitelistBlock":
            case "blacklistEntity":
            case "whitelistEntity":
            case "blacklistStacking":
            case "whitelistStacking":
                break;
            case "override":
                ModelOverrideHandler.parseAndAdd(data);
                break;
            case "script":
                break;
            case "pickupCondition":
                com.skd.carrymechanics.pickupcondition.PickupConditionHandler.add(data);
                break;
        }
    }
}