package explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.exploration.BooleanExplorationNegation;
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
    public void testPlusOneCallExplorer() throws Exception {
        ResourcesManager manager = new ResourcesManager();
        Runner.setup(Resources.class, ResourcesCallable.class, manager, "Numerical", Integer.class);
        CallExplorer explorer = new CallExplorer(new IntegerExplorationPlusOne());

        explorer.initLogger();

        PerturbationLocation location = Resources.__L7;

        PerturbationEngine.loggers.put("TestLogger", new LoggerImpl());
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.runReference(0, location);

        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getCalls(location));
        assertEquals(0, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

//        assert output de reference

        //reinit logger
        PerturbationEngine.loggers.get("TestLogger").remove(location);
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.run(0, location);

        //100 call because 10 * 10
        assertEquals(100, PerturbationEngine.loggers.get("TestLogger").getCalls(location));

        //only 10 perturbations as the reference
        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

//        assert on output after perturbation 1
//
//        assert on output after perturbation 2
//
//        assert on output after perturbation 3
    }

}
