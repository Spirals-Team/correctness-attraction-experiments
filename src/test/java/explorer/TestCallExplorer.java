package explorer;

import experiment.Runner;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import resources.Resources;
import resources.ResourcesCallable;
import resources.ResourcesManager;

import static org.junit.Assert.assertEquals;

/**
 * Created by spirals on 30/04/16.
 */
public class TestCallExplorer {

    @org.junit.Test
    public void testCallExplorer() throws Exception {
        ResourcesManager manager = new ResourcesManager();
        Runner.setup(Resources.class, ResourcesCallable.class, manager, "Numerical", Integer.class);
        CallExplorer explorer = new CallExplorer(new IntegerExplorationPlusOne());

        explorer.initLogger();

        PerturbationLocation location = Resources.__L7;

        PerturbationEngine.loggers.put("TestLogger", new LoggerImpl());
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.run(0, location);

        //110 because 10 times for the reference run, and then 10 * 10 for each nth calls
        assertEquals(110, PerturbationEngine.loggers.get("TestLogger").getCalls(location));

        //only 10 perturbations as the reference
        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));
    }
}
