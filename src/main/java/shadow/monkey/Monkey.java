package shadow.monkey;

/**
 * Created by bdanglot on 22/08/16.
 */
public interface Monkey {

	void doRequest() throws Exception;

	void init() throws InterruptedException;

	void quit();

}
