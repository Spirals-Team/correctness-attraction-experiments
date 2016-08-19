package shadow.explorer;

import org.eclipse.jetty.client.api.Response;
import perturbation.location.PerturbationLocation;
import shadow.Logger;
import shadow.budget.Budget;
import shadow.manager.Manager;
import shadow.manager.ManagerImpl;
import shadow.oracle.Oracle;
import shadow.shadower.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 19/08/16.
 */
public abstract class ExplorerImpl implements Explorer {

	protected Budget budget;
	protected Oracle oracle;
	protected Logger logger;
	protected Manager managerReference;
	protected Manager managerPerturbation;
	protected PerturbationLocation currentLocation;


	public ExplorerImpl(Budget budget, Oracle oracle, int nbLocations, Manager managerPerturbation, String adrProduction) {
		this.budget = budget;
		this.oracle = oracle;
		this.logger = new Logger(nbLocations);
		this.managerReference = ManagerImpl.buildManagerImpl(adrProduction);
		try {
			this.managerReference.logAllLocations();
		} catch (RemoteException ignored) {
		}
		this.managerPerturbation = managerPerturbation;
		this.currentLocation = null;
	}

	@Override
	public void run(HttpServletRequest request) throws RemoteException {
		if (!this.budget.hasToRun()) {
			this.logger.log();
			System.exit(0);
		}
	}

	@Override
	public void endRun(RequestContent referenceOutput, Response perturbationOutput) throws RemoteException {
		if (this.currentLocation != null && perturbationHappens()) {
			boolean assertion = this.oracle.assertPerturbation(referenceOutput, perturbationOutput);
			System.out.println(this.budget + " " + assertion);
			this.logger.count(assertion, this.managerPerturbation.indexOfLocations(this.currentLocation));
			this.budget.update();
			this.managerPerturbation.stopLogOn(this.currentLocation);
			this.managerPerturbation.disableLocation(this.currentLocation);
			this.managerReference.getCallsAndResetLogger();
		}
	}

	protected List<Integer> buildActiveLocationIndices() {
		List<Integer> indicesActiveLocations = new ArrayList<>();
		try {
			int[] calls = this.managerReference.getCallsAndResetLogger();
			for (int i = 0; i < calls.length; i++)
				if (calls[i] > 0)
					indicesActiveLocations.add(i);
			System.out.println("[EXPL] nb active location : " + indicesActiveLocations.size());
			return indicesActiveLocations;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return indicesActiveLocations;
	}

	protected boolean perturbationHappens() {
		try {
			int calls = this.managerReference.getCalls(this.currentLocation);
			int enactions = this.managerReference.getEnactions(this.currentLocation);
			System.out.println("[EXPL] " + calls + ":" + enactions);
			return this.currentLocation != null && calls > 0 && enactions > 0;
		} catch (Exception e) {
			return false;
		}
	}

}
