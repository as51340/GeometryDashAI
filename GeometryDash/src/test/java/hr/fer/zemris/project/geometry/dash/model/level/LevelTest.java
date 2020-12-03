package hr.fer.zemris.project.geometry.dash.model.level;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

/**
 * Class for testing level object
 * @author Andi �krgat
 *
 */
public class LevelTest {

	@Test
	public void initializationCheck() {
		Level level = new Level("LevelName", new HashSet<>());
		assertEquals(0, level.getLevelPercentagePassNormalMode());
		assertEquals(0, level.getLevelPercentagePassPracticeMode());
		assertEquals(0, level.getTotalAttempts());
		assertEquals(0, level.getTotalJumps());
		assertEquals(LEVEL_MODE.NORMAL_MODE, level.getLevelMode());
	}

	@Test
	public void testPropertiesIncrementing() {
		Level level = new Level("LevelName", new HashSet<>());
		level.setLevelPercentagePassNormalMode();
		level.setLevelPercentagePassPracticeMode();
		level.setTotalAttempts();
		level.setTotalJumps();
		level.setLevelMode(LEVEL_MODE.PRACTICE_MODE);
		assertEquals(1, level.getLevelPercentagePassNormalMode());
		assertEquals(1, level.getLevelPercentagePassPracticeMode());
		assertEquals(1, level.getTotalAttempts());
		assertEquals(1, level.getTotalJumps());
		assertEquals(LEVEL_MODE.PRACTICE_MODE, level.getLevelMode());
	}

}
