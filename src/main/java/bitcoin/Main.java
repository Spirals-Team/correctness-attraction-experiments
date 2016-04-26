package bitcoin;

import experiment.Oracle;
import experiment.Runner;

import java.util.concurrent.*;

/**
 * Created by spirals on 25/04/16.
 */
public class Main {


    public static void main(String[] args) {

        Runner.sizeOfEachTask = 2;
        Runner.numberOfTask = 1;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        BitcoinManager manager = new BitcoinManager();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Oracle oracle = manager.getOracle();

        BitcoinCallable callable = new BitcoinCallable(manager.get(0));

        Future future = executor.submit(callable);
        try {
            Object output = (future.get(60, TimeUnit.SECONDS));
            boolean assertion = oracle.assertPerturbation(manager.get(0), output);
            System.out.println(assertion);
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
        System.exit(0);

    }

}
