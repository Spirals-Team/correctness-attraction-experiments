package shadow.oracle;

import org.eclipse.jetty.client.api.Response;
import shadow.shadower.RequestContent;

/**
 * Created by bdanglot on 16/08/16.
 */
public interface Oracle {

	boolean assertPerturbation(RequestContent referenceOutput, Response perturbationOutput);

}
