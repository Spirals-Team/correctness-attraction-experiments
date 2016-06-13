

package perturbation.enactor;

import perturbation.log.LoggerImpl;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import java.util.Random;

public class RandomNCallEnactor implements Enactor {
    private Random random;

    private PerturbationLocation location;

    private int bound;

    public RandomNCallEnactor(int bound, PerturbationLocation location, int seed) {
        RandomNCallEnactor.this.random = new Random(seed);
        RandomNCallEnactor.this.location = location;
        RandomNCallEnactor.this.bound = bound;
    }

    public RandomNCallEnactor(int bound, PerturbationLocation location) {
        RandomNCallEnactor.this.random = new Random(23);
        RandomNCallEnactor.this.location = location;
        RandomNCallEnactor.this.bound = bound;
        PerturbationEngine.loggers.put("NCallEnactor", new LoggerImpl());
        PerturbationEngine.loggers.get("NCallEnactor").logOn(location);
    }

    @Override
    public boolean shouldBeActivated() {
        return false;
    }
}

