package shadow.shadower;

import org.eclipse.jetty.client.api.Request;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bdanglot on 19/08/16.
 */
public class ShadowHelper {

	public static boolean isUsed(String requestURI) {
		return !requestURI.endsWith(".ico") && !requestURI.endsWith(".png") && !requestURI.endsWith(".csv") && !requestURI.endsWith(".jpg") && !requestURI.endsWith(".gif");
	}

	public static byte[] readContent(HttpServletRequest request) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		ServletInputStream inputStream = request.getInputStream();
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}



	public static void replaceCookie(Request proxyRequest, String cookie, Enumeration<String> names) {
		Map<String, String> headers = new HashMap<>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (!name.startsWith("Cookie"))
				headers.put(name, proxyRequest.getHeaders().get(name));
			else
				headers.put("Cookie", cookie);
		}
		proxyRequest.getHeaders().clear();
		headers.keySet().forEach(k -> proxyRequest.header(k, headers.get(k)));
	}




}
