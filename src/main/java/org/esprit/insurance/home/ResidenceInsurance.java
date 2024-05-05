package org.esprit.insurance.home;

import org.esprit.insurance.Insurance;
import org.esprit.insurance.coverage.Coverage;
import org.esprit.model.Residence;

public class ResidenceInsurance extends Insurance {
    private Residence residence;

    public Residence getResidence() {
        return residence;
    }

    public void setHome(Residence residence) {
        this.residence = residence;
    }

    @Override
    public Object get() {
        return residence;
    }

    @Override
    public void set(Object o) {
        if (o instanceof Residence)
            residence = (Residence) o;
        else residence = null;
    }

    @Override
    public double calculateAmount() {
        double monthly = 0.0;

        for (Coverage homeCoverage : coverages) {
            monthly += homeCoverage.getPrice();

            if (residence.getSqrm() > 100) {
                monthly = monthly + (monthly * 0.01);
            }
        }

        return (monthly * 12) * residence.getInhabitants().size();
    }
}

