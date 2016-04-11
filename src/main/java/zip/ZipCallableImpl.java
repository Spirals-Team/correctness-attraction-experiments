package zip;

import experiment.AddNExplorerImpl;
import experiment.AddOneExplorerImpl;
import experiment.CallableImpl;
import experiment.RndExplorerImpl;
import experiment.Runner;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipCallableImpl extends CallableImpl<String> {

    public ZipCallableImpl(String originalValue) {
        super(originalValue);
    }

    @Override
    public String call() throws Exception {
        return LZW.decompress(LZW.compress(super.originalValue));
    }

    public static void run() {
        System.out.println("Run LZW...");
        Runner.setup(LZW.class, ZipCallableImpl.class, "run", new ZipOracleImpl(), String.class);
        Runner.run(new AddOneExplorerImpl());
        Runner.readAntifragileFile();
        Runner.run(new AddNExplorerImpl(2,5,10,20,50,100,1000,10000));
        Runner.run(new RndExplorerImpl());
    }

}
