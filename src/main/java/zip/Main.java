package zip;

import experiment.IntegerAdd1RndEnactorExplorerImpl;
import experiment.Runner;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run LZW...");
        Runner.setup(LZW.class, ZipCallableImpl.class, new ZipOracleImpl(), String.class);
        Runner.run(new IntegerAdd1RndEnactorExplorerImpl());
//        Runner.runAllCampaign();
    }

    public static void main(String[] args) {
        run();
    }
}