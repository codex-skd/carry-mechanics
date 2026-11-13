package com.skd.carrymechanics.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigData {
    public double maxDistance = 2.5;
    public double maxEntityHeight = 2.5;
    public double maxEntityWidth = 1.5;
    public boolean heavyTiles = true;
    public boolean heavyEntities = true;
    public boolean pickupAllBlocks = false;
    public boolean pickupHostileMobs = false;
    public boolean slownessInCreative = true;
    public boolean allowBabies = false;
    public boolean stackableEntities = true;
    public int maxEntityStackLimit = 10;
    public boolean hitWhileCarrying = false;
    public boolean dropCarriedWhenHit = false;
    public boolean pickupUnbreakableBlocks = false;
    public boolean useScripts = true;
    public boolean entitySizeMattersStacking = true;
    public double blockSlownessMultiplier = 1.0;
    public double entitySlownessMultiplier = 1.0;
    public boolean useWhitelistBlocks = false;
    public boolean useWhitelistEntities = false;
    public boolean useWhitelistStacking = false;
    public boolean allowTwoTallBlocks = true;
    public boolean allowLargeBlocks = false;
    public List<String> allowedBlocks = new ArrayList<>();
    public List<String> blockedBlocks = new ArrayList<>();
    public boolean renderArms = true;
    public boolean renderNameTag = true;
}
