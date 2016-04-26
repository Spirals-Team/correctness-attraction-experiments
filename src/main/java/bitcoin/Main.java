package bitcoin;

import experiment.*;
import org.bitcoinj.kits.WalletAppKit;
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
        Runner.setup(WalletAppKit.class, BitcoinCallable.class, new BitcoinManager(), "Numerical", Tuple.class);
        Runner.run(new IntegerAdd1RndEnactorExplorerImpl(new AddNPerturbatorImpl(1)));
        System.exit(0);
//        Runner.runExplorers();
    }

    private static void oldRun(){
        Runner.sizeOfEachTask = 3;
        Runner.numberOfTask = 2;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        BitcoinManager manager = new BitcoinManager();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Oracle oracle = manager.getOracle();

        for (int i = 0 ; i < 10 ; i++) {
            System.out.println(i);
            BitcoinCallable callable = new BitcoinCallable(manager.get(0));

            Future future = executor.submit(callable);
            try {
                Object output = (future.get(60, TimeUnit.SECONDS));
                boolean assertion = oracle.assertPerturbation(manager.get(0), output);
                System.out.println(assertion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        System.exit(0);

    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        Runner.sizeOfEachTask = 3;
        Runner.numberOfTask = 2;
        Runner.verbose = true;
        run();

    }

}
