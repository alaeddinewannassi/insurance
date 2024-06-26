package org.esprit.insurance;

import org.esprit.insurance.coverage.Coverage;

import java.util.ArrayList;
import java.util.List;

public abstract class Insurance {

    protected List<Coverage> coverages = new ArrayList<>();

    public abstract Object get();

    public abstract void set(Object o);

    public List<Coverage> getCoverages() {
        return coverages;
    }

    public void addCoverage(Coverage coverage) {
        coverages.add(coverage);
    }

    public void removeCoverage(Coverage coverage) {
        coverages.remove(coverage);
    }

    public abstract double calculateAmount();
}
