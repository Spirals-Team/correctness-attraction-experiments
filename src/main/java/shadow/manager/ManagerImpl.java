package shadow.manager;

import perturbation.enactor.Enactor;
import perturbation.location.PerturbationLocation;
import perturbation.rmi.PerturbationServer;
import perturbation.rmi.PerturbationServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Created by bdanglot on 16/08/16.
 */
public abstract class ManagerImpl implements Manager {

	protected PerturbationServer skeleton;
	protected List<PerturbationLocation> locations;

	private PerturbationServer getSkeleton(String adrPerturbation) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(adrPerturbation, PerturbationServerImpl.PORT);
			return (PerturbationServer) registry.lookup(PerturbationServerImpl.NAME_SERVER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	protected ManagerImpl(String adrPerturbation) {
		try {
			this.skeleton = this.getSkeleton(adrPerturbation);
			this.locations = this.skeleton.getLocations();
			System.out.println(this.locations.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getNumberLocations() {
		return this.locations.size();
	}

	@Override
	public int getCalls(PerturbationLocation location) throws RemoteException {
		return this.skeleton.getCalls(location);
	}

	@Override
	public int getEnactions(PerturbationLocation location) throws RemoteException {
		return this.skeleton.getCalls(location);
	}

	@Override
	public int[] getEnactionsAndResetLogger() throws RemoteException {
		return this.skeleton.getEnactionsAndResetLogger();
	}

	@Override
	public int[] getCalls() throws RemoteException {
		return this.skeleton.getCalls();
	}

	@Override
	public int[] getCallsAndResetLogger() throws RemoteException {
		return this.skeleton.getCallsAndResetLogger();
	}

	@Override
	public int[] getEnactions() throws RemoteException {
		return this.skeleton.getEnactions();
	}

	@Override
	public PerturbationLocation stopLogOn(PerturbationLocation location) throws RemoteException {
		location = this.skeleton.stopLogOn(location);
		return location;
	}

	@Override
	public void stopLogAllLocations() throws RemoteException {
		this.skeleton.stopLogOnAllLocation();
	}

	@Override
	public PerturbationLocation logOn(PerturbationLocation location) throws RemoteException {
		location = this.skeleton.logOn(location);
		return location;
	}

	@Override
	public void logAllLocations() throws RemoteException {
		this.skeleton.logAllLocation();
	}

	@Override
	public int indexOfLocations(PerturbationLocation location) {
		return this.locations.indexOf(location);
	}

	public static Manager buildManagerImpl(String adrPerturbation) {
		return new ManagerImpl(adrPerturbation) {
			@Override
			public PerturbationLocation enableLocation() throws RemoteException {
				//empty
				return null;
			}

			@Override
			public PerturbationLocation enableLocation(Enactor enactor) throws RemoteException {
				//empty
				return null;
			}

			@Override
			public PerturbationLocation enableLocation(List<Integer> list) throws RemoteException {
				//empty
				return null;
			}

			@Override
			public PerturbationLocation enableLocation(List<Integer> list, Enactor enactor) throws RemoteException {
				//empty
				return null;
			}

			@Override
			public void disableLocation(PerturbationLocation locations) throws RemoteException {
				//empty
			}
		};
	}

}
