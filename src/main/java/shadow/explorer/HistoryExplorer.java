package shadow.explorer;

import org.eclipse.jetty.client.api.Response;
import perturbation.enactor.NCallEnactorImpl;
import shadow.budget.Budget;
import shadow.manager.Manager;
import shadow.oracle.Oracle;
import shadow.shadower.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bdanglot on 19/08/16.
 */
public class HistoryExplorer extends ExplorerImpl {

	private Map<HttpServletRequest, List<Integer>> history;

	private boolean locationEnabled;

	public HistoryExplorer(Budget budget, Oracle oracle, Manager perturbationManager, String adrProduction) {
		super(budget, oracle, perturbationManager.getNumberLocations(), perturbationManager, adrProduction);
		super.oracle = (referenceOutput, perturbationOutput) -> referenceOutput.getResponse().getStatus() != 500 && referenceOutput.getResponse().getStatus() != 404;
		this.history = new HashMap<>();
		this.locationEnabled = false;
	}

	@Override
	public synchronized void run(HttpServletRequest request) throws RemoteException {
		super.run(request);
		try {
			if (!locationEnabled) {
				List<Integer> lst = this.history.get(request);
				if (lst == null || lst.isEmpty())
					super.currentLocation = super.managerPerturbation.enableLocation(new NCallEnactorImpl(0));
				else
					super.currentLocation = super.managerPerturbation.enableLocation(lst, new NCallEnactorImpl(0));
				System.out.println("[EXPL] " + this.currentLocation);
				this.locationEnabled = true;
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	@Override
	public synchronized void endRun(RequestContent referenceOutput, Response perturbationOutput) throws RemoteException {
		if (this.locationEnabled && this.currentLocation != null) {
			if (perturbationHappens()) {
				boolean assertion = super.oracle.assertPerturbation(referenceOutput, perturbationOutput);
				System.out.println(super.budget + " " + assertion);
				this.logger.count(assertion, super.managerPerturbation.indexOfLocations(super.currentLocation));
				this.budget.update();
				this.managerPerturbation.disableLocation(super.currentLocation);
			}
			List<Integer> lst = super.buildActiveLocationIndices();
			if (!lst.isEmpty()) {
				this.history.put(referenceOutput.getRequest(), lst);
				System.out.println(lst);
			}
			this.locationEnabled = false;
			this.currentLocation = null;
		}
	}
}
