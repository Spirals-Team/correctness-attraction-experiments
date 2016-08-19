package shadow;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import shadow.budget.Budget;
import shadow.budget.TimeBudget;
import shadow.explorer.HistoryExplorer;
import shadow.manager.Manager;
import shadow.manager.RandomManager;
import shadow.monkey.Monkey;
import shadow.oracle.CodeStatusOracle;
import shadow.oracle.Oracle;
import shadow.shadower.HistoryProxy;

/**
 * Created by bdanglot on 10/08/16.
 */
public class Main {

	public static boolean verbose = false;

	public static final String ADR_PROD = "172.17.0.67";
	public static final String LOCALHOST = "http://localhost";
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);
		ServletContextHandler contextHandler = new ServletContextHandler();
		server.setHandler(contextHandler);

		Oracle oracle = new CodeStatusOracle();
		Budget budget = new TimeBudget(5000 * 60);
		Manager managerPerturbation = new RandomManager(ADR_PROD, 23);

		HistoryExplorer explorer = new HistoryExplorer(budget, oracle, managerPerturbation, ADR_PROD);
		HistoryProxy servlet = new HistoryProxy(ADR_PROD, ADR_PROD, PORT, explorer);

		contextHandler.addServlet(new ServletHolder(servlet), "/*");

		boolean runMonkey = true;

		Monkey monkey = null;
		if (runMonkey)
			monkey = runMonkey();

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (monkey != null)
				monkey.quit();
		}
	}

	private static Monkey runMonkey() {
		Monkey monkey = new Monkey(LOCALHOST + ":" + PORT);
		// Start the server
		try {
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
						monkey.init();
						while (true)
							monkey.doRequest();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			return monkey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
