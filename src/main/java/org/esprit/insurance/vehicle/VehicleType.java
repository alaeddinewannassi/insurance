package org.esprit.insurance.vehicle;

public enum VehicleType {
    CAR("Car"), MOTORCYCLE("Motorcycle"), FARM_MACHINERY("Agricultural Machinery");

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
