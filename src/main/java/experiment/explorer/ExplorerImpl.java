package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.exploration.Exploration;
import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.NothingPerturbatorImpl;
import perturbation.perturbator.Perturbator;

import java.util.List;

/**
 * Created by beyni on 30/04/16.
 */
public abstract class ExplorerImpl implements Explorer {

    protected List<Perturbator> perturbators;

    protected Exploration exploration;

    public String name;

    public ExplorerImpl(Exploration exploration, String name) {
        this.exploration = exploration;
        this.perturbators = exploration.getPerturbators();
        this.name = name;
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        for (Perturbator perturbator : this.perturbators)
            runOnePerturbator(indexOfTask, location, perturbator);

        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    @Override
    public String getTypeOfExploration() {
        return exploration.getType();
    }

    @Override
    public String toString() {
        return this.exploration.getName()+"_"+this.name;
    }

    public abstract void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator);

}
