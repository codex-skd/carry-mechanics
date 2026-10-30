package com.skd.carrymechanics.carry;

/**
 * Mutable per-player container for CarryData, exposed through the carry_data capability.
 */
public class CarryDataHolder {
    private CarryData data = new CarryData();

    public CarryData get() { return data; }

    public void set(CarryData data) { this.data = data != null ? data : new CarryData(); }
}
