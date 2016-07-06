package explorer;

import experiment.Main;
import experiment.Manager;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.bandit.BanditExplorer;
import experiment.explorer.bandit.Budget;
import experiment.explorer.bandit.Policy;
import experiment.explorer.bandit.TimeBudget;
import experiment.explorer.bandit.UCBPolicy;
import org.junit.Test;
import quicksort.QuickSortManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by bdanglot on 06/07/16.
 */
public class TestBandit {

	@Test
	public void testLoadAndSaveBandit() throws Exception {

		Manager manager = new QuickSortManager(1, 25);
		Main.manager = manager;
		Exploration exploration = new IntegerExplorationPlusOne();
		Policy policy = new UCBPolicy(manager.getLocations().size());
		Budget budget = new TimeBudget(1000);
		BanditExplorer explorer1 = new BanditExplorer(exploration, manager, policy, budget);

		assertEquals(explorer1.outStateBandit(), BanditExplorer.buildBanditFromString(0, explorer1.outStateBandit().split(" ")).outStateBandit());

		String initState = explorer1.outStateBandit();

		explorer1.run();

		assertEquals(explorer1.outStateBandit(), BanditExplorer.buildBanditFromString(0, explorer1.outStateBandit().split(" ")).outStateBandit());
		assertNotEquals(initState, explorer1.outStateBandit());
	}

}
