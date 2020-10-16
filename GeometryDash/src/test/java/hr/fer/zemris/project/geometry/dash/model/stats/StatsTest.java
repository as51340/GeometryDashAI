package hr.fer.zemris.project.geometry.dash.model.stats;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StatsTest {

	@Test
	public void testInitializationToZero() {
		Stats stats = new Stats();
		assertEquals(0, stats.getCollectedDiamonds());
		assertEquals(0, stats.getCollectedStars());
		assertEquals(0, stats.getCompletedLevels());
		assertEquals(0, stats.getDislikedLevels());
		assertEquals(0, stats.getLikedLevels());
		assertEquals(0, stats.getRatedLevels());
		assertEquals(0, stats.getSecretCoins());
		assertEquals(0, stats.getTotalAttempts());
		assertEquals(0, stats.getTotalJumps());
		assertEquals(0, stats.getTotalOrbsCollected());
		assertEquals(0, stats.getUserCoins());
	}
	
	@Test
	public void testAddingOfAllProperties() {
		Stats stats = new Stats();
		stats.setCollectedDiamonds();
		stats.setCollectedStars();
		stats.setCompletedLevels();
		stats.setDislikedLevels();
		stats.setLikedLevels();
		stats.setRatedLevels();
		stats.setSecretCoins();
		stats.setTotalAttempts();
		stats.setTotalJumps();
		stats.setTotalOrbsCollected();
		stats.setUserCoins();
		assertEquals(1, stats.getCollectedDiamonds());
		assertEquals(1, stats.getCollectedStars());
		assertEquals(1, stats.getCompletedLevels());
		assertEquals(1, stats.getDislikedLevels());
		assertEquals(1, stats.getLikedLevels());
		assertEquals(1, stats.getRatedLevels());
		assertEquals(1, stats.getSecretCoins());
		assertEquals(1, stats.getTotalAttempts());
		assertEquals(1, stats.getTotalJumps());
		assertEquals(1, stats.getTotalOrbsCollected());
		assertEquals(1, stats.getUserCoins());
	}

}
