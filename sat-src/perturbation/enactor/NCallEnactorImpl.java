

package perturbation.enactor;

import perturbation.log.LoggerImpl;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;

public class NCallEnactorImpl implements Enactor {
    private int n = 0;

    private PerturbationLocation location;

    public NCallEnactorImpl(int n, PerturbationLocation location) {
        NCallEnactorImpl.this.n = n;
        NCallEnactorImpl.this.location = location;
        location.setEnactor(NCallEnactorImpl.this);
        PerturbationEngine.loggers.put("NCallEnactor", new LoggerImpl());
        PerturbationEngine.loggers.get("NCallEnactor").logOn(location);
    }

    @Override
    public boolean shouldBeActivated() {
        return (PerturbationEngine.loggers.get("NCallEnactor").isLogging(location)) && ((PerturbationEngine.loggers.get("NCallEnactor").getCalls(location)) == (NCallEnactorImpl.this.n));
    }
}

