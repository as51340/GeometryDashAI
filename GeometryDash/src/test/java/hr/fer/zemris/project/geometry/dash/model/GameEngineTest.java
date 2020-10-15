package hr.fer.zemris.project.geometry.dash.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameEngineTest {

	@Test
	public void ConstructorTestFpsAndTitle() {
		GameEngine engine = new GameEngine(100, "Geometry Dash", 720, 480);
	}
	
	@Test
	public void ConstructorTestTitle() {
		GameEngine engine = new GameEngine("Geometry Dash", 720, 480);
	}
	

}
