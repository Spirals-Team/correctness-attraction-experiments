package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.campaign.Campaign;
import perturbation.PerturbationEngine;
import perturbation.enactor.Enactor;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.Perturbator;

/**
 * Created by beyni on 30/04/16.
 */
public class RandomExplorer extends ExplorerImpl {

    private float[] randomRates;

    private int seedOfRandomEnactor = 32;

    private Enactor[][] enactorsOfLocationPerRandomRates;

    private int numberOfRepeat;

    public RandomExplorer(Campaign campaign) {
        this(campaign, new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f,0.5f ,0.9f}, 5);
    }

    public RandomExplorer(Campaign campaign, float [] randomRates) {
        this(campaign, randomRates, 5);
    }

    public RandomExplorer(Campaign campaign, int numberOfRepeat) {
        this(campaign, new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f,0.5f ,0.9f}, numberOfRepeat);
    }

    public RandomExplorer(Campaign campaign, float [] randomRates, int repeat) {
        super(campaign,  "RandomExplorer");
        if (randomRates.length > 1)
            this.randomRates = randomRates;
        else
            this.randomRates = new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f,0.5f ,0.9f};

        this.numberOfRepeat = repeat;

        this.enactorsOfLocationPerRandomRates = new Enactor[Runner.locations.size()][this.randomRates.length];

        for (int indexLocation = 0 ; indexLocation < Runner.locations.size() ; indexLocation++) {
            for (int indexRandom = 0 ; indexRandom < this.randomRates.length ; indexRandom++) {
                this.enactorsOfLocationPerRandomRates[indexLocation][indexRandom] =
                    new RandomEnactorImpl(seedOfRandomEnactor, this.randomRates[indexRandom]);
            }
        }

        PerturbationEngine.loggers.put(super.name, new LoggerImpl());

    }

    @Override
    public void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator) {
        location.setPerturbator(perturbator);
        for (int indexRandomRate = 0 ; indexRandomRate < this.randomRates.length ; indexRandomRate++)
            runOneRandomRate(indexOfTask, location, indexRandomRate);
    }

    private void runOneRandomRate(int indexOfTask, PerturbationLocation location, int randomRate) {
        int indexLocation = Runner.locations.indexOf(location);
        location.setEnactor(enactorsOfLocationPerRandomRates[indexLocation][randomRate]);
        for (int i = 0 ; i < numberOfRepeat ; i++) {
            PerturbationEngine.loggers.get(super.name).logOn(location);
            Tuple result = Runner.runPerturbation(indexOfTask);
            Logger.log(Runner.locations.indexOf(location), indexOfTask, randomRate, result, super.name);
            PerturbationEngine.loggers.get(super.name).reset();
        }
    }
}
