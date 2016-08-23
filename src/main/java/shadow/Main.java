package shadow;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import shadow.budget.Budget;
import shadow.budget.NbRequestBudget;
import shadow.explorer.Explorer;
import shadow.explorer.HistoryExplorer;
import shadow.manager.Manager;
import shadow.manager.RandomManager;
import shadow.monkey.Monkey;
import shadow.monkey.RandomMonkey;
import shadow.oracle.CodeStatusOracle;
import shadow.oracle.Oracle;
import shadow.shadower.HistoryProxy;
import shadow.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bdanglot on 10/08/16.
 */
public class Main {

	public static boolean verbose = false;

	public static Logger logger;

	public static final String ADR_PROD = "172.17.0.2";
	public static final String LOCALHOST = "http://localhost";
	public static final int PORT = 8080;

	private static final List<Monkey> monkeys = new ArrayList<>();

	private static final int[] seeds = new int[20];

	static {
		Random rnd = new Random(PORT);
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = rnd.nextInt();
		}
	}

	public static Explorer explorer;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);
		ServletContextHandler contextHandler = new ServletContextHandler();
		server.setHandler(contextHandler);

		Oracle oracle = new CodeStatusOracle();
		Budget budget = new NbRequestBudget(500);
		Manager managerPerturbation = new RandomManager(ADR_PROD, 23);

		explorer = new HistoryExplorer(budget, oracle, managerPerturbation, ADR_PROD);
		HistoryProxy servlet = new HistoryProxy(ADR_PROD, ADR_PROD, PORT, explorer);

		contextHandler.addServlet(new ServletHolder(servlet), "/*");

		int nbMonkey = 3;
		for (int i = 0; i < nbMonkey; i++) {
			monkeys.add(runMonkey());
		}

		try {
			server.start();
			View.run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			for (Monkey monkey : monkeys) {
				monkey.quit();
			}
		}
	}

	private static Monkey runMonkey() {
		Monkey monkey = new RandomMonkey(LOCALHOST + ":" + PORT, seeds[monkeys.size()]);
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
