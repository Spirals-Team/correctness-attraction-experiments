package shadow.shadower;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import shadow.explorer.Explorer;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static shadow.shadower.ShadowHelper.isUsed;
import static shadow.shadower.ShadowHelper.readContent;

/**
 * Created by bdanglot on 19/08/16.
 */
public class DumpProxy extends ProxyServlet {

	public DumpProxy(String production, String perturbation, int port, Explorer dumpExplorer) {
		super(production, perturbation, port, dumpExplorer);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestContent reference = new RequestContent();

		reference.setRequest(request);
		reference.setResponse(response);

		String rewrittenTarget = this.rewriteTarget(request);
		if (rewrittenTarget == null) {
			this.onProxyRewriteFailed(request, response);
		} else {
			AsyncContext asyncContext = request.startAsync();
			asyncContext.setTimeout(0L);

			try {

				Request proxyRequest1 = copyRequest(request, rewrittenTarget);
				if (this.hasContent(request)) {
					reference.setContent(readContent(request));
					proxyRequest1.content(new BytesContentProvider(reference.getContent()));
				}

				this.sendProxyRequest(request, response, proxyRequest1);

				if (isUsed(request.getRequestURI())) {
					this.explorer.run(request);
				}

				ContentResponse responsePerturbed = proxyRequest1.send();

				if (isUsed(request.getRequestURI())) {
					this.explorer.endRun(reference, responsePerturbed);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
