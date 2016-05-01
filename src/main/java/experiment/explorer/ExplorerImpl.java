package experiment.explorer;

import experiment.campaign.Campaign;
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

    protected Campaign campaign;

    public ExplorerImpl(Campaign campaign) {
        this.campaign = campaign;
        this.perturbators = campaign.getPerturbators();
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        for (Perturbator perturbator : this.perturbators)
            runOnePerturbator(indexOfTask, location, perturbator);

        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    public abstract void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator);

    @Override
    public void log() {

    }
}
