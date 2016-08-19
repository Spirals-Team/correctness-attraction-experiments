package shadow.shadower;

/**
 * Created by tdurieux
 */
public interface ProxyListener {
	void endRequest(ProxyServlet proxyServlet, org.eclipse.jetty.client.api.Request output);

	void newRequest(ProxyServlet proxyServlet, RequestContent output);
}