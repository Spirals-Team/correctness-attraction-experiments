package zip;

import experiment.AddNExplorerImpl;
import experiment.AddOneExplorerImpl;
import experiment.RndExplorerImpl;
import experiment.Runner;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run LZW...");
        Runner.setup(LZW.class, ZipCallableImpl.class, new ZipOracleImpl(), String.class);
        Runner.run(new AddOneExplorerImpl());
        Runner.run(new AddNExplorerImpl());
        Runner.run(new RndExplorerImpl());
    }

    public static void main(String[] args) {
        run();
    }
}
