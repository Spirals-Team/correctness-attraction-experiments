package rc4;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;


/**
 * Created by bdanglot on 24/05/16.
 */
public class RC4Manager extends ManagerImpl<String, String> {

    private final String key = "0123456789ABCDEF";

    private String [] output;

    public RC4Manager(int number, int size) {
        this(number, size, 23);
    }

    public RC4Manager(int number, int size, int seed) {
        super(seed);
        super.CUP = RC4Engine.class;
        this.output = new String[number];
        super.initialize(number, size > 16 ? 16 : size );
    }

    @Override
    protected String generateOneTask() {
        String task =  "";
        for (int i = 0 ; i < super.sizeOfTask ; i++)
            task += this.key.charAt(super.randomForGenTask.nextInt(16));
        RC4Engine cipher = new RC4Engine();
        cipher.init(true, new KeyParameter(Hex.decode(this.key)));
        byte [] input = Hex.decode(task.getBytes());
        byte [] out = new byte[input.length];
        cipher.processBytes(input, 0, input.length, out, 0);
        this.output[super.tasks.size()] = new String(Hex.encode(out));
        return task;
    }

    @Override
    public CallableImpl<String, String> getCallable(String input) {
        return new CallableImpl<String, String>(input) {
            @Override
            public String call() throws Exception {
                RC4Engine cipher = new RC4Engine();
                cipher.init(true, new KeyParameter(Hex.decode(key)));
                byte [] in = Hex.decode(input.getBytes());
                byte [] out = new byte[in.length];
                cipher.processBytes(in, 0, in.length, out, 0);
                return new String(Hex.encode(out));
            }
        };
    }

    @Override
    public Oracle<String, String> getOracle() {
        return ((input, output) -> (this.output[super.tasks.indexOf(input)]).equals(output));
    }

    @Override
    public String getName() {
        return "rc4";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " hex string of " + super.sizeOfTask + " byte\nRandom byte generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public String getTask(int indexOfTask) {
        return new String(super.tasks.get(indexOfTask));
    }
}
