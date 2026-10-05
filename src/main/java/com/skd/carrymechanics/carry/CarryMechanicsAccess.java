package com.skd.carrymechanics.carry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skd.carrymechanics.CarryMechanics;
import net.neoforged.neoforge.attachment.AttachmentType;

public class CarryMechanicsAccess {
    public static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    public static AttachmentType<CarryData> getAttachmentType() {
        return CarryMechanics.CARRY_DATA.get();
    }
}