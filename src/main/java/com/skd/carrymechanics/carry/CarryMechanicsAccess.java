package com.skd.carrymechanics.carry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraftforge.common.capabilities.Capability;

public class CarryMechanicsAccess {
    public static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    public static Capability<CarryDataHolder> getCapability() {
        return CarryDataCapability.CARRY_DATA;
    }
}
