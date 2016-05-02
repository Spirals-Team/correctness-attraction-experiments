package experiment.exploration;

import experiment.Runner;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.util.ArrayList;

/**
 * Created by beyni on 01/05/16.
 */
public class IntegerExplorationPlusOne extends ExplorationImpl {

    public IntegerExplorationPlusOne() {
        super.names = new String[1];
        super.names[0] = "PONE";
        perturbators = new ArrayList<>();
        perturbators.add(new AddNPerturbatorImpl(1));

        super.columnName = "Perturbator";

        super.name = "Plus One";
        super.header = "Exploration Plus One\n";

        super.header += Runner.locations.size() + " perturbation point\n";
        super.header += "PONE : plus one perturbator\n";
    }

}
