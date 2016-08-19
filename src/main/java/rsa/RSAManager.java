package rsa;

import experiment.CallableImpl;
import experiment.Main;
import experiment.ManagerImpl;
import experiment.Oracle;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.InvPerturbatorImpl;

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
		byte[] task = new byte[super.sizeOfTask];
		while ((task[0] = (byte) (randomForGenTask.nextInt())) == 0) ;
		for (int i = 1; i < super.sizeOfTask - 1; i++)
			task[i] = ((byte) (randomForGenTask.nextInt()));
		while ((task[super.sizeOfTask - 1] = (byte) (randomForGenTask.nextInt())) == 0) ;
		return task;
	}

	@Override
	public byte[] getTask(int index) {
		if (index >= super.tasks.size())
			return super.getTask(index);
		byte[] clone = new byte[super.sizeOfTask];
		System.arraycopy(super.tasks.get(index), 0, clone, 0, clone.length);
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
		return super.indexTasks.size() + " string of " + super.sizeOfTask + " bytes\nGenerated with " + super.seedForGenTask + " as seed\n" +
				super.locations.size() + " perturbations points\n";
	}

	public static void main(String[] args) {
		RSAManager manager = new RSAManager(20, 100);
		try {
			PerturbationLocation location = manager.getLocations().stream().filter(l -> l.getLocationIndex() == 48).findFirst().get();
			System.out.println(location);
			location.setPerturbator(new InvPerturbatorImpl());
			PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
			for (int i = 0; i < 20; i++) {
				PerturbationEngine.loggers.get("ExploreExample").logOn(location);
				manager.getCallable(manager.getTask(i)).call();
				int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(location);
				PerturbationEngine.loggers.get("ExploreExample").remove(location);
				for (int i1 = 0; i1 < nbCall; i1++) {
					PerturbationEngine.loggers.get("ExploreExample").logOn(location);
					RSACallable callable = (RSACallable) manager.getCallable(manager.getTask(i));
					callable.init();
					byte [] cipher = callable.cipher();
					byte [] output = callable.decipher(cipher);
					boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(i), output);
					if (!assertion)
						System.out.println("ERROR ON " + i);
					location.setEnactor(new NCallEnactorImpl(i1));
					callable = (RSACallable) manager.getCallable(manager.getTask(i));
					callable.init();
					byte [] cipher2 = callable.cipher();
					System.out.println(Arrays.equals(cipher, cipher2));
					output = callable.decipher(cipher2);
					assertion = manager.getOracle().assertPerturbation(manager.getTask(i), output);
					if (!assertion)
						System.out.println("ERROR ON " + i);
					System.out.println(PerturbationEngine.loggers.get("ExploreExample").getEnactions(location) + " : " + PerturbationEngine.loggers.get("ExploreExample").getCalls(location));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
