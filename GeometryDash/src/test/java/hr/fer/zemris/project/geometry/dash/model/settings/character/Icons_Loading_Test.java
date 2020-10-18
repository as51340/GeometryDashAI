package hr.fer.zemris.project.geometry.dash.model.settings.character;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import javafx.scene.image.Image;

public class Icons_Loading_Test {

	@Test
	public void icon1Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-130-coins-icon.jpg");
		});
	}
	
	@Test
	public void icon2Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-3000-stars-icon.jpg");
		});
	}
	
	@Test
	public void icon3Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-9000-coins-icon.jpg");
		});
	}
	
	@Test
	public void icon4Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-angry-icon.png");
		});
	}
	
	@Test
	public void icon5Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-blue-green-icon.jpg");
		});
	}
	
	@Test
	public void icon6Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-catch-them-all-icon.jpg");
		});
	}
	
	@Test
	public void icon7Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-cod3breaker-icon.jpg");
		});
	}
	
	@Test
	public void icon8Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-crying-icon.jpg");
		});
	}
	
	@Test
	public void icon9Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-cube-icon.jpg");
		});
	}
	
	@Test
	public void icon10Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-demons-icon.jpg");
		});
	}
	
	@Test
	public void icon11Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-graphic-icon.jpg");
		});
	}
	
	@Test
	public void icon12Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-orange-icon.png");
		});
	}
	
	@Test
	public void icon13Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-sad-icon.jpg");
		});
	}
	
	@Test
	public void icon14Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-shuriken-icon.jpg");
		});
	}
	
	@Test
	public void icon15Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-shy-guy-icon.jpg");
		});
	}
	
	@Test
	public void icon16Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-special-icon.jpg");
		});
	}
	
	@Test
	public void icon17Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-very-angry-icon.jpg");
		});
	}
	
	@Test
	public void icon18Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-yellow-icon.png");
		});
	}
	
	@Test
	public void icon19Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("geom-dash-gatekeeper-query-icon.jpg");
		});
	}
	
	@Test
	public void icon20Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("one.png");
		});
	}

	@Test
	public void icon21Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("two.png");
		});
	}
	
	@Test
	public void icon22Test() {
		assertDoesNotThrow(() -> {
			Image icon = Utils.loadIcon("three.png");
		});
	}
}
