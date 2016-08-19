package shadow.explorer;

import org.eclipse.jetty.client.api.Response;
import shadow.shadower.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;

/**
 * Created by bdanglot on 19/08/16.
 */
public interface Explorer {

	void run(HttpServletRequest request) throws RemoteException;

	void endRun(RequestContent referenceOutput, Response perturbationOutput) throws RemoteException;

}
