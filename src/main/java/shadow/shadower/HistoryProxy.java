package shadow.shadower;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.util.BytesContentProvider;
import shadow.explorer.Explorer;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

import static shadow.shadower.ShadowHelper.isUsed;
import static shadow.shadower.ShadowHelper.readContent;

/**
 * Created by bdanglot on 19/08/16.
 */
public class HistoryProxy extends ProxyServlet {

	private RequestContent reference;

	private int nbRequestReceive;

	public HistoryProxy(String production, String perturbation, int port, Explorer explorer) {
		super(production, perturbation, port, explorer);
		this.nbRequestReceive = 0;
	}

	public int getNbRequestReceive() {
		return nbRequestReceive;
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.nbRequestReceive++;
		this.reference = new RequestContent();

		this.reference.setRequest(request);
		this.reference.setResponse(response);

		String rewrittenTarget = this.rewriteTarget(request);
		if (rewrittenTarget == null) {
			this.onProxyRewriteFailed(request, response);
		} else {
			AsyncContext asyncContext = request.startAsync();
			asyncContext.setTimeout(0L);

			try {

				if (isUsed(request.getRequestURI())) {
					this.explorer.run(request);
				}

				Request proxyRequest1 = copyRequest(request, rewrittenTarget);
				if (this.hasContent(request)) {
					reference.setContent(readContent(request));
					proxyRequest1.content(new BytesContentProvider(reference.getContent()));
				}

				this.sendProxyRequest(request, response, proxyRequest1);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onProxyResponseSuccess(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse) {
		if (isUsed(clientRequest.getRequestURI())) {
			System.out.println(clientRequest.getRequestURI());
			try {
				this.explorer.endRun(reference, serverResponse);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		super.onProxyResponseSuccess(clientRequest, proxyResponse, serverResponse);
	}

	@Override
	protected void onProxyResponseFailure(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse, Throwable failure) {
		if (isUsed(clientRequest.getRequestURI())) {
			try {
				this.explorer.endRun(reference, serverResponse);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		super.onProxyResponseSuccess(clientRequest, proxyResponse, serverResponse);
		super.onProxyResponseFailure(clientRequest, proxyResponse, serverResponse, failure);
	}
}
