package explorer;

import experiment.Runner;
import experiment.exploration.BooleanExplorationNegation;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import resources.Resources;
import resources.ResourcesCallableBoolean;
import resources.ResourcesCallableInt;
import resources.ResourcesManager;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by spirals on 30/04/16.
 */
public class TestCallExplorer {



    @org.junit.Test
    public void testPlusOneCallExplorer() throws Exception {
        ResourcesManager manager = new ResourcesManager();
        Runner.setup(Resources.class, ResourcesCallableInt.class, manager, "Numerical", Integer.class);
        CallExplorer explorer = new CallExplorer(new IntegerExplorationPlusOne());

        explorer.initLogger();

        PerturbationLocation location = Resources.__L16;

        PerturbationEngine.loggers.put("TestLogger", new LoggerImpl());
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.runReference(0, location);

        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getCalls(location));
        assertEquals(0, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

        Field fieldOutputs = Runner.class.getDeclaredField("outputs");
        fieldOutputs.setAccessible(true);
        List<Object> output = (List<Object>) fieldOutputs.get(null);

        assertEquals(10, output.get(0));

        //reinit logger
        PerturbationEngine.loggers.get("TestLogger").remove(location);
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.run(0, location);

        //100 call because 10 * 10
        assertEquals(100, PerturbationEngine.loggers.get("TestLogger").getCalls(location));

        //only 10 perturbations as the reference
        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

        //perturbation 1
        assertEquals(11, output.get(1));
        //perturbation 2
        assertEquals(11, output.get(2));
        //perturbation 8
        assertEquals(11, output.get(8));
        //perturbation 9
        assertEquals(11, output.get(9));

    }

    @org.junit.Test
    public void testBooleanNegationCallExplorer() throws Exception {
        ResourcesManager manager = new ResourcesManager();
        Runner.setup(Resources.class, ResourcesCallableBoolean.class, manager, "Boolean", Boolean.class);
        CallExplorer explorer = new CallExplorer(new BooleanExplorationNegation());

        explorer.initLogger();

        PerturbationLocation location = Resources.__L7;

        PerturbationEngine.loggers.put("TestLogger", new LoggerImpl());
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.runReference(0, location);

        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getCalls(location));
        assertEquals(0, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

        Field fieldOutputs = Runner.class.getDeclaredField("outputs");
        fieldOutputs.setAccessible(true);
        List<Object> output = (List<Object>) fieldOutputs.get(null);

        assertEquals(true, output.get(0));

        //reinit logger
        PerturbationEngine.loggers.get("TestLogger").remove(location);
        PerturbationEngine.loggers.get("TestLogger").logOn(location);

        explorer.run(0, location);

        //100 call because 10 * 10
        assertEquals(100, PerturbationEngine.loggers.get("TestLogger").getCalls(location));

        //only 10 perturbations as the reference
        assertEquals(10, PerturbationEngine.loggers.get("TestLogger").getEnactions(location));

        //perturbation 1
        assertEquals(false, output.get(1));
        //perturbation 2
        assertEquals(false, output.get(2));
        //perturbation 8
        assertEquals(false, output.get(8));
        //perturbation 9
        assertEquals(false, output.get(9));

    }

}
