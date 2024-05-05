package org.esprit.insurance;

public enum InsuranceType {
    HEALTH("Health"), VEHICLE("Vehicle"), RESIDENCE("Residence");

    private final String label;

    InsuranceType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
