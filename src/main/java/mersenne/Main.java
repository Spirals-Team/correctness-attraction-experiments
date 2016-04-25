package mersenne;

import experiment.Runner;
import experiment.Util;

/**
 * Created by spirals on 19/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run Mersenne...");
        Runner.setup(MersenneTwisterInstr.class, MersenneCallableImpl.class, new MersenneManager(),"Numerical", Long.class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        run();
    }

}
