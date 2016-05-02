package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.exploration.Exploration;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.NothingPerturbatorImpl;
import perturbation.perturbator.Perturbator;

import java.util.List;

/**
 * Created by beyni on 30/04/16.
 */
public abstract class ExplorerImpl implements Explorer {

    protected List<Perturbator> perturbators;

    protected Exploration exploration;

    public final String name;

    public ExplorerImpl(Exploration exploration, String name) {
        this(exploration, name, 1);
    }

    public ExplorerImpl(Exploration exploration, String name, int numberOfEnactor) {
        this.exploration = exploration;
        this.perturbators = exploration.getPerturbators();
        this.name = name;
        Logger.init(Runner.locations.size(), Runner.numberOfTask, this.perturbators.size(), numberOfEnactor);
    }



    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        for (Perturbator perturbator : this.perturbators)
            runOnePerturbator(indexOfTask, location, perturbator);

        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    public abstract void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator);

}
