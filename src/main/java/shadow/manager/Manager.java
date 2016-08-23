package shadow.manager;

import perturbation.enactor.Enactor;
import perturbation.location.PerturbationLocation;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by bdanglot on 16/08/16.
 */
public interface Manager {

	List<PerturbationLocation> getLocations();

	int getNumberLocations();

	PerturbationLocation logOn(PerturbationLocation location) throws RemoteException;

	void logAllLocations() throws RemoteException;

	int indexOfLocations(PerturbationLocation location);

	PerturbationLocation stopLogOn(PerturbationLocation location) throws RemoteException;

	void stopLogAllLocations() throws RemoteException;

	/**
	 * enable location among the whole list of location
	 * @return
	 * @throws RemoteException
	 */
	PerturbationLocation enableLocation() throws RemoteException;

	/**
	 * enable location among the whole list of location with the given Enactor
	 * @return
	 * @throws RemoteException
	 */
	PerturbationLocation enableLocation(Enactor enactor) throws RemoteException;

	/**
	 * enable a location among the given list(indices of location).
	 * @param list
	 * @return
	 * @throws RemoteException
	 */
	PerturbationLocation enableLocation(List<Integer> list) throws RemoteException;

	/**
	 * enable a location among the given list(indices of location) with the given Enactor
	 * @return
	 * @throws RemoteException
	 */
	PerturbationLocation enableLocation(List<Integer> list, Enactor enactor) throws RemoteException;

	void disableLocation(PerturbationLocation location) throws RemoteException;

	int[] getCalls() throws RemoteException;

	/**
	 * Same as getCalls() but will set 0 to the counter of the number of calls after retrieve it
	 * @return
	 * @throws RemoteException
	 */
	int [] getCallsAndResetLogger() throws RemoteException;

	int getCalls(PerturbationLocation location) throws RemoteException;

	int getEnactions(PerturbationLocation location) throws RemoteException;

	/**
	 * Same as getEnactions() but will set 0 to the counter of the number of perturbations after retrieve it
	 * @return
	 * @throws RemoteException
	 */
	int [] getEnactionsAndResetLogger() throws RemoteException;

	/**
	 * Shouldn't be used since we enable one pp at the time.
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	int[] getEnactions() throws RemoteException;

}
