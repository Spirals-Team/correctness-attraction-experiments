package zip;

import experiment.Runner;
import experiment.Util;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run LZW...");
        Runner.setup(LZW.class, ZipCallableImpl.class, new ZipOracleImpl(), String.class);
        Runner.runAllCampaign();
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        run();
    }
}
