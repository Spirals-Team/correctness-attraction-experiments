package shadow.manager;

import experiment.Util;
import perturbation.enactor.Enactor;
import perturbation.location.PerturbationLocation;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by bdanglot on 10/08/16.
 */
public class RandomManager extends ManagerImpl {

	private Random random;

	public RandomManager(String adrPerturbation) {
		super(adrPerturbation);
		this.random = new Random();
	}

	public RandomManager(String adrPerturbation, int seed) {
		super(adrPerturbation);
		this.random = new Random(seed);
	}

	@Override
	public PerturbationLocation enableLocation() throws RemoteException {
		PerturbationLocation locationEnabled = super.locations.get(random.nextInt(this.locations.size()));
		locationEnabled = super.skeleton.enableLocation(locationEnabled);
		return locationEnabled;
	}

	@Override
	public PerturbationLocation enableLocation(Enactor enactor) throws RemoteException {
		PerturbationLocation locationEnabled = super.locations.get(random.nextInt(this.locations.size()));
		locationEnabled = super.skeleton.enableLocation(locationEnabled, enactor);
		return locationEnabled;
	}

	@Override
	public PerturbationLocation enableLocation(final List<Integer> list) throws RemoteException {
		System.out.println("[MNGR] nb active location : " + Util.getStringPerc(list.size(), super.locations.size()));
		List<PerturbationLocation> filteredLocations = super.locations.stream().filter(location ->
				list.contains(super.locations.indexOf(location))
		).collect(Collectors.toList());
		PerturbationLocation locationEnabled = filteredLocations.get(random.nextInt(filteredLocations.size()));
		System.out.println("[MNGR] " + locationEnabled);
		locationEnabled = super.skeleton.enableLocation(locationEnabled);
		return locationEnabled;
	}

	@Override
	public PerturbationLocation enableLocation(List<Integer> list, Enactor enactor) throws RemoteException {
		System.out.println("[MNGR] nb active location : " + Util.getStringPerc(list.size(), super.locations.size()));
		List<PerturbationLocation> filteredLocations = super.locations.stream().filter(location ->
				list.contains(super.locations.indexOf(location))
		).collect(Collectors.toList());
		PerturbationLocation locationEnabled = filteredLocations.get(random.nextInt(filteredLocations.size()));
		System.out.println("[MNGR] " + locationEnabled);
		locationEnabled = super.skeleton.enableLocation(locationEnabled, enactor);
		return locationEnabled;
	}
}
