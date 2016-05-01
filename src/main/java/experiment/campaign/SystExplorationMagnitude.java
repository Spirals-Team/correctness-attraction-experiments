package experiment.campaign;

import experiment.Runner;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.util.ArrayList;

/**
 * Created by beyni on 01/05/16.
 */
public class SystExplorationMagnitude extends CampaignImpl {

    public SystExplorationMagnitude(int ...magnitudes) {
        if (magnitudes.length > 0)
            super.magnitudes = magnitudes;
        else
            super.magnitudes = new int[]{1,2,5,10,20,50};

        super.names = new String[super.magnitudes.length];
        perturbators = new ArrayList<>();

        super.columnName = "Magnitude";

        super.name = "AddM";
        super.header = "Systematical Exploration Plus Magnitude\n";
        super.header += "magnitude value : ";

        for (int indexMagnitude = 0 ; indexMagnitude < super.magnitudes.length ; indexMagnitude++) {
            perturbators.add(new AddNPerturbatorImpl(super.magnitudes[indexMagnitude]));
            super.names[indexMagnitude] = "" + super.magnitudes[indexMagnitude];
            super.header +=  super.magnitudes[indexMagnitude] + " ";
        }

        super.header += "\n";
        super.header += Runner.locations.size() + " perturbation point\n";
        super.header += "N Execution Enactor\n";
        super.header += "PONE : Numerical Perturbator\n";
    }
}
