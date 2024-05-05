package org.esprit.insurance.health;

import org.esprit.insurance.Insurance;
import org.esprit.insurance.coverage.Coverage;
import org.esprit.model.User;

import java.util.List;

public class HealthInsurance extends Insurance {

    private List<User> members;

    @Override
    public Object get() {
        return members;
    }

    @Override
    public void set(Object o) {
        if (o instanceof List<?>)
            members = (List<User>) o;
        else members = null;
    }

    @Override
    public double calculateAmount() {
        double total = 0.0;

        for (Coverage healthInsurances : coverages) {
            total += healthInsurances.getPrice();
        }

        return (total * 12) * members.size();
    }
}
