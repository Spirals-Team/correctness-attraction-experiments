package rsa;

import experiment.*;

import java.util.Arrays;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSAManager extends ManagerImpl<byte[], byte[]> {

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
    protected byte[] generateOneTask() {
        byte [] task = new byte[super.sizeOfTask];
        while ( (task[0] = (byte)(randomForGenTask.nextInt())) == 0 );
        for (int i = 1 ; i < super.sizeOfTask - 1 ; i++)
            task[i] = ((byte)(randomForGenTask.nextInt()));
        while ( (task[super.sizeOfTask - 1] = (byte)(randomForGenTask.nextInt())) == 0 );
        return task;
    }

    @Override
    public byte[] getTask(int index) {
        if (index >= super.tasks.size())
            return super.getTask(index);
        byte [] clone = new byte[super.sizeOfTask];
        System.arraycopy(super.tasks.get(index), 0 , clone, 0, clone.length);
        return clone;
    }

    @Override
    public Oracle<byte[], byte[]> getOracle() {
        return Arrays::equals;
    }


    @Override
    public CallableImpl<byte[], byte[]> getCallable(byte[] input) {
        return new RSACallable(input);
    }

    @Override
    public String getName() {
        return "rsa";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of "+ super.sizeOfTask  +" bytes\nGenerated with " + super.seedForGenTask + " as seed\n"+
                super.locations.size() + " perturbations points\n";
    }
}
