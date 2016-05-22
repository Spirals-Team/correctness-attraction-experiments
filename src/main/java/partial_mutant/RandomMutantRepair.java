package partial_mutant;

import experiment.OracleManager;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.RandomExplorer;
import perturbation.enactor.AlwaysEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;
import zip.LZWInstr;
import zip.ZipCallableImpl;
import zip.ZipManager;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * Created by bdanglot on 20/05/16.
 */
public class RandomMutantRepair {

    private List<PerturbationLocation> partialMutant;

    private MutantExplorer explorer;

    private Class<?> CUP;

    private OracleManager manager;

    private Class<?> callable;

    private Constructor callableConstructor;

    private Class<?>[] inputTypes;

    private Map<PerturbationLocation , List<Integer>> indexSuccess;

    private Map<PerturbationLocation , List<Integer>> indexFailure;

    public RandomMutantRepair(int nbTask, Class<?> CUP, OracleManager manager, Class<?> callable, Class<?>... inputTypes) {
        explorer = new MutantExplorer(CUP, manager, callable, inputTypes);
        explorer.setNumberOfTask(nbTask);
        partialMutant = explorer.getPartialMutant();
        this.CUP = CUP;
        this.manager = manager;
        this.callable = callable;
        try {
            this.callableConstructor = callable.getConstructor(inputTypes);
        } catch (NoSuchMethodException e) {e.printStackTrace();}
        this.inputTypes = inputTypes;
        this.indexSuccess = explorer.getIndexSuccessTaskPerPartialMutant();
        this.indexFailure = explorer.getIndexFailureTaskPerPartialMutant();
    }

    public void run() {
        this.indexSuccess.keySet().forEach(this::runLocation);
    }

    private void runLocation(PerturbationLocation location) {
        location.setEnactor(new AlwaysEnactorImpl());
        location.setPerturbator(new AddNPerturbatorImpl(1));
        List<PerturbationLocation> locations = PerturbationLocationImpl.getLocationFromClass(CUP);
        locations.remove(location);
        Runner.setup(CUP, callable, manager, "Numerical", inputTypes);
        Runner.verbose = true;
        System.out.println(location);
        this.runSpecificTask(this.indexSuccess.get(location), "Success_"+location.getLocationIndex(), locations);
        this.runSpecificTask(this.indexFailure.get(location), "Failure_"+location.getLocationIndex(), locations);
        location.setEnactor(new NeverEnactorImpl());
        location.setPerturbator(new NothingPerturbatorImpl());
    }

    private void runSpecificTask(List<Integer> task, String name, List<PerturbationLocation> locations) {
        Runner.task = task;
        System.out.println(task.size());
        Runner.numberOfTask = task.size();
        Runner.locations = locations;
        RandomExplorer rndExplorer = new RandomExplorer(new IntegerExplorationPlusOne(null));
        rndExplorer.name = name;
        Runner.run(rndExplorer , locations);
    }

    public static void main(String[] args) {
        int nbTask = 500;
        RandomMutantRepair repair = new RandomMutantRepair(nbTask, LZWInstr.class,
                new ZipManager(nbTask, 32), ZipCallableImpl.class, String.class);
        repair.run();
    }

}
