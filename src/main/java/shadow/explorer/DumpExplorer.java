package shadow.explorer;

import org.eclipse.jetty.client.api.Response;
import shadow.budget.Budget;
import shadow.manager.Manager;
import shadow.oracle.Oracle;
import shadow.shadower.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 16/08/16.
 */
public class DumpExplorer extends ExplorerImpl {

	private List<Integer> indicesActiveLocations;

	public DumpExplorer(Budget budget, Oracle oracle, Manager managerPerturbation, String adrProduction) {
		super(budget, oracle, managerPerturbation.getNumberLocations(), managerPerturbation, adrProduction);
		this.indicesActiveLocations = new ArrayList<>();
	}

	public void run(HttpServletRequest request) throws RemoteException {
		super.run(request);
		try {
			if (this.indicesActiveLocations == null || this.indicesActiveLocations.isEmpty())
				this.indicesActiveLocations = this.buildActiveLocationIndices();
			this.currentLocation = this.managerPerturbation.enableLocation(indicesActiveLocations);
			System.out.println("[EXPL] " + this.currentLocation);
			this.managerPerturbation.logOn(this.currentLocation);
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	public void endRun(RequestContent referenceOutput, Response perturbationOutput) throws RemoteException {
		super.endRun(referenceOutput, perturbationOutput);
		if (this.currentLocation != null && perturbationHappens()) {
			this.indicesActiveLocations.clear();
		}
	}


}
