package lcs;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.InvPerturbatorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by bdanglot on 10/06/16.
 */
public class LCSManager extends ManagerImpl<String[], String> {

	private BufferedReader sativa;
	private BufferedReader thaliana;

	private String currentSativa;

	public LCSManager(int numberOfTask) {
		this(numberOfTask, 23);
	}

	public LCSManager(int numberOfTask, int seed) {
		super(seed);
		this.initFile();
		super.CUP = LCSInstr.class;
		super.initialize(numberOfTask, -1);
	}

	private void initFile() {
		try {
			this.sativa = new BufferedReader(new FileReader("resources/lcs/sativa.fa"));
			this.sativa.readLine();//Trash Header
			this.currentSativa = sativa.readLine();
			this.thaliana = new BufferedReader(new FileReader("resources/lcs/thaliana.fa"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String[] generateOneTask() {
		try {
			if (this.thaliana.readLine() == null) {
				this.thaliana.close();
				this.thaliana = new BufferedReader(new FileReader("resources/lcs/thaliana.fa"));
				if (this.sativa.readLine() == null) {
					System.err.println("Too many task has been generated (over 12k)");
					System.exit(-1);
				}
				this.currentSativa = this.sativa.readLine();
			}
			return new String[]{this.currentSativa, this.thaliana.readLine()};
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	@Override
	public CallableImpl<String[], String> getCallable(String[] input) {
		return new CallableImpl<String[], String>(input) {
			@Override
			public String call() throws Exception {
				return LCSInstr.lcs(input[0], input[1]);
			}
		};
	}

	@Override
	public Oracle<String[], String> getOracle() {
		return (input, output) -> LCS.lcs(input[0], input[1]).equals(output);
	}

	@Override
	public String getName() {
		return "lcs";
	}

	@Override
	public String getHeader() {
		return super.indexTasks.size() + " pairs of RNA sequences (16 - 25 nt)\n" +
				"RNA sequence of thaliana and sativa from miRBase (mature.fa)\n" +
				super.locations.size() + " perturbation points\n";
	}

	@Override
	public String[] getTask(int indexOfTask) {
		if (indexOfTask >= super.tasks.size())
			return super.getTask(indexOfTask);
		return new String[]{super.tasks.get(indexOfTask)[0], super.tasks.get(indexOfTask)[1]};
	}

	@Override
	public void stop() {
		super.stop();
		try {
			this.thaliana.close();
			this.sativa.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int nbTask = 1;
		LCSManager manager = new LCSManager(nbTask, 1);
		PerturbationLocation location = LCSInstr.__L14;
		location.setPerturbator(new InvPerturbatorImpl());
		PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
		for (int t = 0; t < nbTask; t++) {
			int k = manager.getTask(t)[0].length();
			int nbErrors = 0;
			int nbSuccess = 0;
			PerturbationEngine.loggers.get("ExploreExample").logOn(location);
			try {
				manager.getCallable(manager.getTask(t)).call();
			} catch (Exception ignored) {
			}
			int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(location);
			for (int i = 0; i < k; i++) {

				location.setEnactor(new NCallEnactorImpl(i));
				try {
					boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(t), manager.getCallable(manager.getTask(t)).call());
					if (assertion)
						nbSuccess++;
					else
						nbErrors++;
				} catch (Exception e) {
					nbErrors++;
				}
			}

			System.out.println("\n" + nbSuccess + " : " + nbErrors + " = " + nbCall);
		}

	}
}
