package bitcoin;

import experiment.*;
import org.bitcoinj.crypto.PBKDF2SHA512;
import org.bitcoinj.kits.WalletAppKit;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.AddOnePerturbatorImpl;
import quicksort.QuickSortCallableImpl;
import quicksort.QuickSortInstr;
import quicksort.QuickSortManager;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by spirals on 25/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run Bitcoin...");
        Runner.setup(PBKDF2SHA512.class, BitcoinCallable.class, new BitcoinManager(), "Numerical", Tuple.class);
        Runner.locations = Runner.locations.subList(18,23);
        Runner.run(new AddOneExplorerImpl());
//        Runner.run(new IntegerAdd1RndEnactorExplorerImpl(new AddNPerturbatorImpl(1), 2, new float[]{0.01f,0.1f,0.9f}));
        ((BitcoinManager)Runner.manager).stop();
        System.exit(0);
//        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);

        Runner.numberOfSecondsToWait = 60;
        Runner.sizeOfEachTask = 3;
        Runner.numberOfTask = 2;
        Runner.verbose = true;

        run();

    }

}
