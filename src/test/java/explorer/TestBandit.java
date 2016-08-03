package explorer;

import experiment.Main;
import experiment.Manager;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.bandit.BanditExplorer;
import experiment.explorer.bandit.budget.Budget;
import experiment.explorer.bandit.budget.TimeBudget;
import experiment.explorer.bandit.policy.Policy;
import experiment.explorer.bandit.policy.RandomPolicy;
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
//		Policy policy = new UCBPolicy(manager.getLocations().size());
		Policy policy = new RandomPolicy(manager.getLocations().size());
		Budget budget = new TimeBudget(1000);
		BanditExplorer explorer1 = new BanditExplorer(exploration, manager, policy, budget);

		assertEquals(explorer1.outStateBandit(), BanditExplorer.buildBanditFromString(0, explorer1.outStateBandit().split(" "), policy).outStateBandit());

		String initState = explorer1.outStateBandit();

		explorer1.run();

		assertEquals(explorer1.outStateBandit(), BanditExplorer.buildBanditFromString(0, explorer1.outStateBandit().split(" "), policy).outStateBandit());
		assertNotEquals(initState, explorer1.outStateBandit());
	}

}
