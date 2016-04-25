package bitcoin;

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
//        Oracle oracle = manager.getOracle();

//        BitcoinCallable callable = new BitcoinCallable(manager.get(0));
//
//        Future future = executor.submit(callable);
//        try {
//            Object output = (future.get(Runner.numberOfSecondsToWait, TimeUnit.SECONDS));
//            boolean assertion = oracle.assertPerturbation(manager.get(0), (Integer)output);
//            System.out.println(assertion);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }

}
