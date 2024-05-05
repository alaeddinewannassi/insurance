package org.esprit.insurance.vehicle;

import org.esprit.insurance.Insurance;
import org.esprit.insurance.coverage.Coverage;
import org.esprit.model.Vehicle;

public class VehicleInsurance extends Insurance {

    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Object get() {
        return vehicle;
    }

    @Override
    public void set(Object o) {
        if (o instanceof Vehicle)
            vehicle = (Vehicle) o;
        else vehicle = null;
    }

    @Override
    public double calculateAmount() {
        double monthly = 0.0;

        for (Coverage vehicleCoverage : coverages) {
            monthly += vehicleCoverage.getPrice();

            if (vehicle.getOwner().getAge() < 35) {
                monthly = monthly + (monthly * 0.02);
            }
        }

        return monthly * 12;
    }
}
