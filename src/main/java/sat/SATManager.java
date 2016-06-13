package sat;

import experiment.*;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.core.Solver;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.ISolver;


/**
 * Created by bdanglot on 09/06/16.
 */
public class SATManager extends ManagerImpl<String, int[]> {

    private final static String PATH = "resources/sat/CBS_k3_n100_m403_b10_";

    private final static String EXT = ".cnf";

    public SATManager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public SATManager(int nbTask, int sizeTask, int seed) {
        super(seed);
        super.CUP = Solver.class;
        super.initialize(nbTask, sizeTask);
    }

    @Override
    protected String generateOneTask() {
        return PATH + super.tasks.size() + EXT;
    }

    @Override
    public CallableImpl<String, int[]> getCallable(String input) {
        return new CallableImpl<String, int[]>(input) {
            @Override
            public int[] call() throws Exception {
                ISolver solver = SolverFactory.newDefault();//Build a Solver.
                solver.setTimeout(Main.numberOfSecondsToWait);
                Reader reader = new DimacsReader(solver);
                Solver task = (Solver) reader.parseInstance(input);
                return task.findModel();
            }
        };
    }

    @Override
    public Oracle<String, int[]> getOracle() {
        return (input, output) -> {
            try {
                ISolver solver = SolverFactory.newDefault();//Build a Solver.
                Reader reader = new DimacsReader(solver);
                Solver task = (Solver) reader.parseInstance(input);
                for (int i = 0; i < task.nConstraints(); i++) {
                    IConstr ithConstr = task.getIthConstr(i);
                    boolean lineValue = false;
                    for (int j = 0; j < ithConstr.size(); j++) {
                        int variable = ithConstr.get(j) >> 1;
                        boolean isNeg = (ithConstr.get(j) & 1) != 0;
                        boolean result = output[variable - 1] * (isNeg ? -1 : 1) > 0;
                        lineValue |= result;
                    }
                    if (!lineValue) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
    }

    @Override
    public String getName() {
        return "sat";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " sat problems of 100 variables, 403 clauses\n" +
                "download http://www.cs.ubc.ca/~hoos/SATLIB/benchm.html\n" +
                super.locations.size() + " perturbations points\n";
    }

    @Override
    public String getTask(int indexOfTask) {
        return super.tasks.get(indexOfTask);
    }

}
