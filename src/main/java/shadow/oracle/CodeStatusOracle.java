package shadow.oracle;

import org.eclipse.jetty.client.api.Response;
import shadow.shadower.RequestContent;

/**
 * Created by bdanglot on 12/08/16.
 */
public class CodeStatusOracle implements Oracle {

	public boolean assertPerturbation(RequestContent referenceOutput, Response perturbationOutput) {
		return !(referenceOutput == null || perturbationOutput == null)
				&& referenceOutput.getResponse().getStatus() == perturbationOutput.getStatus();
	}

}
