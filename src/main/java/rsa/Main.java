package rsa;

import experiment.explorer.IntegerAdd1RndEnactorExplorerImpl;
import experiment.Runner;
import experiment.Util;
import perturbation.perturbator.AddNPerturbatorImpl;

/**
 * Created by bdanglot on 29/04/16.
 */
public class Main {

    private static final String QUALIFIED_NAME_RSACOREENGINE = "org.bouncycastle.crypto.engines.RSACoreEngine";

    public static void run() {
        System.out.println("Run RSA...");
        //RSACoreEngine is a friendly class
        try {
            Class rsaCoreEngine = ClassLoader.getSystemClassLoader().loadClass(QUALIFIED_NAME_RSACOREENGINE);
            Runner.setup(rsaCoreEngine ,RSACallable.class,new RSAManager(), "Numerical", String.class);
            Runner.verbose = true;
            Runner.run(new IntegerAdd1RndEnactorExplorerImpl(new AddNPerturbatorImpl(1)));
            //Runner.runExplorers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        run();
    }

}
