

package perturbation.log;

import java.util.HashMap;
import java.util.Map;
import perturbation.location.PerturbationLocation;

public class LoggerImpl implements Logger {
    private Map<PerturbationLocation, Integer> numberOfCallsPerLocation = new HashMap<PerturbationLocation, Integer>();

    private Map<PerturbationLocation, Integer> numberOfEnactionsPerLocation = new HashMap<PerturbationLocation, Integer>();

    @Override
    public void logOn(PerturbationLocation location) {
        LoggerImpl.this.numberOfCallsPerLocation.put(location, 0);
        LoggerImpl.this.numberOfEnactionsPerLocation.put(location, 0);
    }

    @Override
    public void remove(PerturbationLocation location) {
        LoggerImpl.this.numberOfCallsPerLocation.remove(location);
    }

    @Override
    public void logCall(PerturbationLocation location) {
        if (LoggerImpl.this.numberOfCallsPerLocation.containsKey(location))
            LoggerImpl.this.numberOfCallsPerLocation.put(location, ((LoggerImpl.this.numberOfCallsPerLocation.get(location)) + 1));
        
    }

    @Override
    public void logEnaction(PerturbationLocation location) {
        if (numberOfEnactionsPerLocation.containsKey(location))
            LoggerImpl.this.numberOfEnactionsPerLocation.put(location, ((LoggerImpl.this.numberOfEnactionsPerLocation.get(location)) + 1));
        
    }

    @Override
    public int getCalls(PerturbationLocation location) {
        return LoggerImpl.this.numberOfCallsPerLocation.get(location);
    }

    @Override
    public int getEnactions(PerturbationLocation location) {
        return LoggerImpl.this.numberOfEnactionsPerLocation.get(location);
    }

    @Override
    public boolean isLogging(PerturbationLocation location) {
        return LoggerImpl.this.numberOfCallsPerLocation.containsKey(location);
    }

    @Override
    public void reset() {
        LoggerImpl.this.numberOfCallsPerLocation.clear();
        LoggerImpl.this.numberOfEnactionsPerLocation.clear();
    }
}

