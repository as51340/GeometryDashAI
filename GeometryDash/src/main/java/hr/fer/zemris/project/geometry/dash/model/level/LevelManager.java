package hr.fer.zemris.project.geometry.dash.model.level;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.io.ZipUtil;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.music.LevelMusicPlayer;

/**
 * @author Andi Škrgat
 *
 */
public class LevelManager {

	/**
	 * List of all available levels in game
	 */
	private Set<Level> allLevels;

	/**
	 * Reference to the level music player
	 */
	private LevelMusicPlayer levelMusicPlayer;

	/**
	 * Currently selected level
	 */
	private Level currentLevel;
	
	/**
	 * Constructor that loads all initial levels created from the start
	 */
	public LevelManager() {
		allLevels = new HashSet<Level>();
		
		try (Stream<Path> paths = Files.list(Paths.get(GameConstants.pathToLevelsFolder))) {
		    paths
		    .filter(Files::isRegularFile)
		    .filter(path -> path.toString().toLowerCase().endsWith(".zip"))
		    .forEach(path -> {
		    	String levelName = path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf("."));
		        Set<GameObject> levelObjects = new SerializationOfObjects(
		        	GsonFactory.createGameObjectGson(50)).deserializeGameObjects(ZipUtil.openZipFile(GameConstants.pathToLevelsFolder, levelName)
		        );
		        addLevel(levelName, levelObjects);
		    });
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Adds level to the list of all levels
	 * 
	 * @param levelName name of the level
	 * @param levelData objects belonging to the level
	 */
	public Level addLevel(String levelName, Set<GameObject> levelData) {
		Level level = new Level(levelName, levelData);
		allLevels.add(level);
		return level;
	}

	/**
	 * Returns appropriate level by its name
	 * @param levelName name of the level
	 * @return {@linkplain Level} with level name
	 */
	public Level getLevelByName(String levelName) {
		for (Level level : allLevels) {
			if (level.getLevelName().equals(levelName)) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Remove level with given name
	 * 
	 * @param levelName name of the level
	 */
	public void removeLevelByName(String levelName) {
		Iterator<Level> iterator = allLevels.iterator();
		while (iterator.hasNext()) {
			Level lev = iterator.next();
			if (lev.getLevelName().equals(levelName)) {
				iterator.remove();
				return;
			}
		}
	}

	/**
	 * Level name
	 * 
	 * @param levelName level name
	 * @return level with level name
	 */
	public Set<GameObject> startLevelWithName(String levelName) {
		Iterator<Level> iterator = allLevels.iterator();
		while (iterator.hasNext()) {
			Level lev = iterator.next();
			if (lev.getLevelName().equals(levelName)) {
//				levelMusicPlayer.startPlayingSongForLevel(levelName);
				this.currentLevel = lev;
//				for(GameObject obj: lev.getLevelData()) {
//					System.out.println(obj.getCurrentPosition().getX());
//				}
				return lev.getLevelData();
			}
		}
		// we should throw because it's not normal situation when user can choose to load level with some level name
		// but it actually doesn't exist
		throw new RuntimeException("No such level");
	}

	/**
	 * @return all levels
	 */
	public Set<Level> getAllLevels() {
		return this.allLevels;
	}

	/**
	 * @return the currentLevel
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * @return the levelMusicPlayer
	 */
	public LevelMusicPlayer getLevelMusicPlayer() {
		return levelMusicPlayer;
	}

}
