package zip;

import experiment.Runner;

/**
 * Created by spirals on 05/04/16.
 */
public class Main {

    public static String run(String stringToCompressUnCompress) {
        return LZW.decompress(LZW.compress(stringToCompressUnCompress));
    }

    public static void main(String[] args) {
        Runner.setup(LZW.class, Main.class, "run", new ZipOracleImpl(), String.class);
        Runner.runAllCampaign();
    }
}
