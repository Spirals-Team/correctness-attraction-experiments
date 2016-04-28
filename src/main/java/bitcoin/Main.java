package bitcoin;

import experiment.*;
import org.bitcoinj.core.ECKey;

/**
 * Created by spirals on 25/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run Bitcoin...");
        Runner.setup(ECKey.class, BitcoinCallable.class, new BitcoinManager(), "Numerical", Tuple.class);
        Runner.runExplorers();
        ((BitcoinManager)Runner.manager).stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
//        System.err.close();
        run();
    }

}
