package shadow.shadower;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RequestContent {

	private List<Byte> body;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private byte[] content;

	public RequestContent() {

	}

	public List<Byte> getBody() {
		return body;
	}

	public void addBody(int length, byte... body) {
		if (this.body == null) {
			this.body = new ArrayList();
		}
		for (int i = 0; i < Math.min(length, body.length); i++) {
			byte b = body[i];
			this.body.add(b);
		}
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public byte[] getContent() {
		return content;
	}

}