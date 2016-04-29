package experiment;

import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.InvPerturbatorImpl;

import java.util.HashMap;

/**
 * Created by spirals on 25/04/16.
 */
public class BooleanInvRndEnactorExplorerImpl extends IntegerAdd1RndEnactorExplorerImpl {

    public BooleanInvRndEnactorExplorerImpl(float... randomRates) {
        super(new InvPerturbatorImpl(), randomRates);

        perturbatorName = "INV : Boolean Perturbator";

        header = "Exploration Random\n";
        header += "random Rates : ";

        for (int indexOfRandomRate = 0; indexOfRandomRate < super.randomRates.length; indexOfRandomRate++) {
            header += super.randomRates[indexOfRandomRate] + " ";
        }
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += repeat + " repetition of each point of each task\n";
        header += perturbatorName+ ", Random Enactor, seed :" + seedOfRandomEnactor + "\n";

        path = "BooleanInvRndEnactorExplorer";

    }
}
