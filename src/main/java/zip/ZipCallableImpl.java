package zip;

import experiment.AddNExplorerImpl;
import experiment.AddOneExplorerImpl;
import experiment.CallableImpl;
import experiment.RndExplorerImpl;
import experiment.Runner;
import experiment.Util;

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
        Runner.setup(LZW.class, ZipCallableImpl.class, new ZipOracleImpl(), String.class);
        Runner.run(new AddOneExplorerImpl());
        Util.buildNewListOfPerturbationPoint();
        Runner.run(new AddNExplorerImpl(2,5,10,20,33,50,80,100,200));
        Runner.run(new RndExplorerImpl());
    }

    public static void main(String[] args) {
        run();
    }

}
