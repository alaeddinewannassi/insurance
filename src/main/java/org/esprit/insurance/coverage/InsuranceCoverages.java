package org.esprit.insurance.coverage;

import java.util.ArrayList;
import java.util.List;

public class InsuranceCoverages {
    public static List<Coverage> healthCoverages() {
        return new ArrayList<>() {{
            add(new Coverage("Insurance application", "The proposal, written or oral, of the candidate insured for the risk he wishes to insure. It is usually done by completing a written application on a form provided by the insurance partner and signed by the prospective insured. If the proposal is accepted by the insurance partner, the insurance contract is concluded.", 600));
            add(new Coverage("Hospital care", "It covers the expenses incurred in the hospital. In order to compensate the insured, the original invoices need to be submitted. The insured is compensated for the actual expenses incurred in the hospital.", 120));
        }};
    }

    public static List<Coverage> vehicleCoverages() {
        return new ArrayList<>() {{
            add(new Coverage("Theft", "The total theft of the vehicle is covered. The compensation paid corresponds to the current commercial value of the car on the day of theft.", 100));
            add(new Coverage("Roadside assistance", "In the event that the insured car is immobilized due to any damage, roadside assistance undertakes to repair the damage on the spot, if this is possible. If it is not, he will undertake its transport to a nearby repair shop or repatriate it to the insured's seat.", 300));
        }};
    }

    public static List<Coverage> residenceCoverages() {
        return new ArrayList<>() {{
            add(new Coverage("Fire", "Material damage caused by fire, lightning and explosion is covered.", 200));
            add(new Coverage("Earthquake", "Damages caused to your home by an earthquake are covered.", 300));
        }};
    }
}
