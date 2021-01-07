package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;

/**
 * Implementation of customable genetic algorithm
 * 
 * @author Andi Škrgat
 *
 */
public class GeneticFunctionality {

	/**
	 * Random reference
	 */
	private Random random;

	/**
	 * Current population
	 */
	private List<Tree> currPopulation;

	/**
	 * By default we use three tournament selection
	 */
	private SelectionProcess selProcess = SelectionProcess.THREE_TOURNAMENT_SELECTION;

	/**
	 * By default we use eliminative genetic algorithm
	 */
	private GenAlg genAlg = GenAlg.ELIMINATIVE_GENETIC_ALGORITHM;

	/**
	 * Current inputs for genetic algorithm
	 */
	private List<Double> inputs = null;

	/**
	 * Sum of all goodness values in population
	 */
	private double population_goodness_value = -1;

	/**
	 * Worst goodness value in population
	 */
	private double worst_goodness_value_in_population = -1;

	/**
	 * Default constructor
	 */
	public GeneticFunctionality() {
		this(null);
	}

	public GeneticFunctionality(List<Double> inputs) {
		this.inputs = inputs;
		random = new Random();
		currPopulation = new ArrayList<Tree>();
		performAlgorithm();
	}

	/**
	 * Genetic algorithm
	 */
	private void performAlgorithm() {
		initializePopulation();
		loop();
	}

	/**
	 * We initialize population of size {@linkplain AIConstants}.treePopulationSize
	 * using RHH method. With this method we create same number of trees for every
	 * tree size between 2 and {@linkplain AIConstants}.initialDepth. Half of these
	 * trees are generated with full method and half with grow method
	 */
	private void initializePopulation() {
		for (int i = 2; i <= AIConstants.initialDepth; i++) { // generate for each level
			for (int j = 1; j <= 2; j++) { // first generate trees by full method, then by grow method
				for (int k = 1; k <= AIConstants.populationSizeFullGrow; k++) { // half goes for grow, half goes for
					TreeNode root = new TreeNode(new Action(TreeUtil.numberToActionType(random.nextInt(25) + 1)));
					if (j == 1) { // generate full tree
						TreeUtil.randomSubtree(root, i - 1, random, inputs, true);
					} else { // if tree is generated with grow method
						TreeUtil.randomSubtree(root, i - 1, random, inputs, false);
					}
					currPopulation.add(new Tree(root));
				}
			}
		}
	}

	/**
	 * Create algorithm
	 */
	private void loop() {
		if (genAlg == GenAlg.ELIMINATIVE_GENETIC_ALGORITHM) {
			for (int i = 0; i < AIConstants.maxGenerations; i++) {
				for (int j = 0; j < AIConstants.treePopulationSize; j++) {
					Player player = new Player(
							new Vector2D(j, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
							new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y),
							PlayingMode.GENETIC_PROGRAMMING);
					currPopulation.get(j).setPlayer(player);
					// dodaj ih u game world nekako preko listenera
					// pokreni
					// zapamti mi 2 najgora igraca
					// removaj 2 najgora igraca
					// zapamti najboljeg igraca
					// suma svih goodness_valuea igraca
				}
				List<Tree> selectedTrees = selection();
				int firstTree = random.nextInt(selectedTrees.get(0).getSize()) + 1;
				int secondTree = random.nextInt(selectedTrees.get(1).getSize()) + 1;
				List<Tree> crossovered = TreeUtil.crossover(selectedTrees.get(0), selectedTrees.get(1), firstTree,
						secondTree);
				int targetNode1 = random.nextInt(crossovered.get(0).getSize()) + 1;
				Tree mutated1 = TreeUtil.mutate(crossovered.get(0), targetNode1, random, inputs);
				int targetNode2 = random.nextInt(crossovered.get(1).getSize()) + 1;
				Tree mutated2 = TreeUtil.mutate(crossovered.get(1), targetNode2, random, inputs);
				currPopulation.add(mutated1);
				currPopulation.add(mutated2);
			}
		} else if (genAlg == GenAlg.GENERATIONAL_GENETIC_ALGORITHM) {
			int genNumber = 0;
			while (genNumber < AIConstants.maxGenerations) {
				for (int j = 0; j < AIConstants.treePopulationSize; j++) {
					Player player = new Player(
							new Vector2D(j, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
							new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y),
							PlayingMode.GENETIC_PROGRAMMING);
					currPopulation.get(j).setPlayer(player);
				}
				List<Tree> newGeneration = new ArrayList<Tree>();
				// dodaj ih u game world
				// pokreni
				// ovo je training mode
				// cekaj da zavrse
				// zapamti 2 najbolja
				// dodaj ih u newGeneration
				while (newGeneration.size() < AIConstants.treePopulationSize) {
					List<Tree> selectedTrees = selection();
					int firstTree = random.nextInt(selectedTrees.get(0).getSize()) + 1;
					//dok su isti stvaraj second tree
					int secondTree = random.nextInt(selectedTrees.get(1).getSize()) + 1;
					List<Tree> crossovered = TreeUtil.crossover(selectedTrees.get(0), selectedTrees.get(1), firstTree,
							secondTree);
					int targetNode1 = random.nextInt(crossovered.get(0).getSize()) + 1;
					Tree mutated1 = TreeUtil.mutate(crossovered.get(0), targetNode1, random, inputs);
					int targetNode2 = random.nextInt(crossovered.get(1).getSize()) + 1;
					Tree mutated2 = TreeUtil.mutate(crossovered.get(1), targetNode2, random, inputs);
					newGeneration.add(mutated1);
					newGeneration.add(mutated2);
				}
				currPopulation = newGeneration;
				genNumber++;
			}
		}
	}

	/**
	 * Selection algorithm
	 * 
	 * @return list of selected trees
	 */
	private List<Tree> selection() {
		List<Tree> list = new ArrayList<Tree>();
		if (selProcess == SelectionProcess.THREE_TOURNAMENT_SELECTION) {
			int[] indexes = random.ints(0, AIConstants.treePopulationSize).distinct().limit(3).toArray();
			list.add(currPopulation.get(indexes[0]));
			list.add(currPopulation.get(indexes[1]));
			list.add(currPopulation.get(indexes[2]));
			if (list.get(0).getPlayer().getGoodness_value() < list.get(1).getPlayer().getGoodness_value()
					&& list.get(0).getPlayer().getGoodness_value() < list.get(2).getPlayer().getGoodness_value()) {
				list.remove(0);
			} else if (list.get(1).getPlayer().getGoodness_value() < list.get(0).getPlayer().getGoodness_value()
					&& list.get(1).getPlayer().getGoodness_value() < list.get(2).getPlayer().getGoodness_value()) {
				list.remove(1);
			} else {
				list.remove(2);
			}
		} else if (selProcess == SelectionProcess.ROULETTE_WHEEL_SELECTION) {
			Tree tree1 = getPlayerByRoulette();
			if (tree1 == null) { // uniformly select
				tree1 = currPopulation.get(random.nextInt(AIConstants.treePopulationSize));
			}
			Tree tree2 = getPlayerByRoulette();
			if (tree2 == null) {
				tree2 = currPopulation.get(random.nextInt(AIConstants.treePopulationSize));
			}
			list.add(tree1);
			list.add(tree2);
		}
		return list;
	}

	/**
	 * @return tree using roulette algorithm in which better individual has more
	 *         chances to be selected
	 * 
	 */
	private Tree getPlayerByRoulette() {
		double d = random.nextDouble() * (1 - population_goodness_value * AIConstants.treePopulationSize);
		if (d < 0) {
			throw new IllegalArgumentException("Something is wrong with roulette_wheel selection");
		}
		double curr = 0;
		for (int i = 0; i < AIConstants.treePopulationSize; i++) {
			if (curr + (currPopulation.get(i).getPlayer().getGoodness_value() - worst_goodness_value_in_population)
					/ population_goodness_value > d) {
				return currPopulation.get(i);
			}
			curr += (currPopulation.get(i).getPlayer().getGoodness_value() - worst_goodness_value_in_population);
		}
		return null;
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * @return the currPopulation
	 */
	public List<Tree> getCurrPopulation() {
		return currPopulation;
	}

	/**
	 * @param currPopulation the currPopulation to set
	 */
	public void setCurrPopulation(List<Tree> currPopulation) {
		this.currPopulation = currPopulation;
	}

	/**
	 * @return the inputs
	 */
	public List<Double> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return the selProcess
	 */
	public SelectionProcess getSelProcess() {
		return selProcess;
	}

	/**
	 * @param selProcess the selProcess to set
	 */
	public void setSelProcess(SelectionProcess selProcess) {
		this.selProcess = selProcess;
	}

	/**
	 * @return the genAlg
	 */
	public GenAlg getGenAlg() {
		return genAlg;
	}

	/**
	 * @param genAlg the genAlg to set
	 */
	public void setGenAlg(GenAlg genAlg) {
		this.genAlg = genAlg;
	}

	public static void main(String[] args) {
		List<Double> inputs = new ArrayList<Double>();
		inputs.add(1.0);
		inputs.add(2.0);
		inputs.add(3.0);
		inputs.add(4.0);
		inputs.add(5.0);
		GeneticFunctionality genFun = new GeneticFunctionality(inputs);
	}

}
