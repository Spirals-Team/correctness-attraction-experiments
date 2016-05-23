package rsa;

import experiment.*;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSAManager extends ManagerImpl<String, String> {

    public RSAManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public RSAManager(int numberOfTask, int size, int seed) {
        super(seed);
        //RSACoreEngine is a private class, we must use reflection to load it
        try {
            super.CUP = Main.class.getClassLoader().loadClass("org.bouncycastle.crypto.engines.RSACoreEngine");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.initialize(numberOfTask, size);
    }

    @Override
    protected String generateOneTask() {
        byte [] task = new byte[64];
        for (int i = 0 ; i < 64 ; i++)
            task[i] = ((byte)(randomForGenTask.nextInt()));
        return new String(org.bouncycastle.util.encoders.Hex.encode(task));
    }

    @Override
    public String getTask(int index) {
        String task = new String(String.valueOf(super.tasks.get(index)).getBytes());
        return task;
    }

    @Override
    public Oracle getOracle() {
        return new RSAOracle();
    }


    @Override
    public CallableImpl<String, String> getCallable(String input) {
        return new RSACallable(input);
    }

    @Override
    public String getName() {
        return "rsa";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of 64 bytes\nGenerated with " + super.seedForGenTask + " as seed\n"+
                super.locations.size() + " perturbations points\n";
    }
}
