package rsa;

import experiment.Runner;
import experiment.Util;

/**
 * Created by bdanglot on 29/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run RSA...");
//        Runner.setup(RSACoreEngine.class,RSACallable.class,new RSAManager(), "Numerical", String.class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        run();
    }

}
