package md5;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.InvPerturbatorImpl;

/**
 * Created by spirals on 19/04/16.
 */
public class MD5Manager extends ManagerImpl<String, byte[]> {

    public MD5Manager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);

    }

    public MD5Manager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = MD5Instr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0 ; i < super.sizeOfTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
		return string;
    }

    @Override
    public CallableImpl<String, byte[]> getCallable(String input) {
        return new MD5CallableImpl(input);
    }

    @Override
    public String getName() {
        return "md5";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of " + super.sizeOfTask + " char\nRandom char generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public String getTask(int index) {
        if (index >= super.tasks.size())
            return super.getTask(index);
        return new String(super.tasks.get(index));
    }

    @Override
    public Oracle<String, byte[]> getOracle() {
        return new MD5Oracle();
    }

    public static void main(String[] args) {
		int nbTask = 1;
		System.out.println(1184 >>> 8);
		System.exit(1);
		MD5Manager manager = new MD5Manager(nbTask, 100000);
		try {
			for (int t = 0; t < nbTask; t++) {
				MD5Instr.__L22.setPerturbator(new InvPerturbatorImpl());
				PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
				PerturbationEngine.loggers.get("ExploreExample").logOn(MD5Instr.__L22);
				manager.getCallable(manager.getTask(t)).call();
				int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(MD5Instr.__L22);
				System.out.println(nbCall);
				for (int i = 0; i < nbCall; i++) {
					try {
						MD5Instr.__L22.setEnactor(new NCallEnactorImpl(i));
						boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(t), manager.getCallable(manager.getTask(t)).call());
						if (!assertion) {
							System.out.println("FAIL " + i + "  " + t);
						}
					} catch (Exception e) {
						System.out.println("ERROR " + i + "  " + t);
						continue;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
