package rsa;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSAManager extends OracleManagerImpl<String> {

    public RSAManager(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " string of 64 bytes\nGenerated with " + super.seedForGenTask + " as seed\n";
        super.path = "rsa";
    }

    public RSAManager() {
        super();
        super.header = Runner.numberOfTask + " string of 64 bytes\nGenerated with " + super.seedForGenTask + " as seed\n";
        super.path = "rsa";
    }

    @Override
    protected String generateOneTask() {
        byte [] task = new byte[64];
        for (int i = 0 ; i < 64 ; i++)
            task[i] = ((byte)(randomForGenTask.nextInt()));
        return new String(org.bouncycastle.util.encoders.Hex.encode(task));
    }

    @Override
    public String get(int index) {
        String task = new String(String.valueOf(super.scenario.get(index)).getBytes());
        return task;
    }

    @Override
    public Oracle getOracle() {
        return new RSAOracle();
    }
}
