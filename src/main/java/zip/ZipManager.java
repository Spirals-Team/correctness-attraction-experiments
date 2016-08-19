package zip;

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
public class ZipManager extends ManagerImpl<String, String> {

	public ZipManager(int numberOfTask, int size) {
		this(numberOfTask, size, 23);
	}

	public ZipManager(int numberOfTask, int size, int seed) {
		super(seed);
		super.CUP = LZWInstr.class;
		super.initialize(numberOfTask, size);
	}


	@Override
	protected String generateOneTask() {
		String string = "";
		for (int i = 0; i < super.sizeOfTask; i++) {
			string += ((char) randomForGenTask.nextInt(256));
		}
		return string;
	}

	@Override
	public CallableImpl<String, String> getCallable(String input) {
		return new ZipCallableImpl(input);
	}

	@Override
	public Oracle<String, String> getOracle() {
		return new ZipOracle();
	}

	@Override
	public String getName() {
		return "zip";
	}

	@Override
	public String getHeader() {
		return super.indexTasks.size() + " string of " + super.sizeOfTask + " char\n" +
				"Random char generated with " + seedForGenTask + " as seed\n" +
				super.locations.size() + " perturbation points\n";
	}

	@Override
	public String getTask(int indexOfTask) {
		if (indexOfTask >= super.tasks.size())
			return super.getTask(indexOfTask);
		return new String(super.tasks.get(indexOfTask));
	}

	public static void main(String[] args) {

		ZipManager manager = new ZipManager(20, 100);
		LZWInstr.__L4.setPerturbator(new InvPerturbatorImpl());

		PerturbationEngine.loggers.put("ZipMain", new LoggerImpl());

		int cpt = 0;

		boolean all = true;

		try {
			for (int i = 0 ; i < 20 ; i++) {
				PerturbationEngine.loggers.get("ZipMain").logOn(LZWInstr.__L4);
				manager.getCallable(manager.getTask(i)).call();
				int nbCall = PerturbationEngine.loggers.get("ZipMain").getCalls(LZWInstr.__L4);
				PerturbationEngine.loggers.get("ZipMain").reset();
				boolean location = false;
				for (int call = 0 ; call < nbCall ; call++) {
					LZWInstr.__L4.setEnactor(new NCallEnactorImpl(call));
					try {
						String output = manager.getCallable(manager.getTask(i)).call();
						boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(i), output);
						if (assertion) {
							location = true;
							cpt++;
						}
					} catch (Exception e) {
						continue;
					}
				}
				all &= location;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(cpt + " " + all);
	}

}
