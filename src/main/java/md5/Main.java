package md5;

import experiment.IntegerAdd1RndEnactorExplorerImpl;
import experiment.Runner;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run md5...");
        Runner.setup(MD5.class, MD5CallableImpl.class, new MD5OracleImpl(), String.class);
        Runner.run(new IntegerAdd1RndEnactorExplorerImpl());
//        Runner.runAllCampaign();
    }

    public static void main(String[] args) {
        run();
    }
}
