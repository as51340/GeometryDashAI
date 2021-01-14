package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

public abstract class Obstacle extends GameObject {

    /**
     * Checks if obstacle hitbox is touching player
     *
     * @return true if it is, otherwise false
     */
    public abstract boolean checkCollisions(Player player);

    public abstract boolean playerIsOn(Player player);
    
    //TODO should probably change how obstacles are decoded

    /**
     * Decodes obstacle type to a Double
     *
     * @param obstacle given obstacle
     * @return Double representation of the obstacle
     */
    public static double decodeObstacleType(Obstacle obstacle) {
        return switch (obstacle.getName()) {
            case "Block" -> 0;
            case "Floor" -> 1;
            case "GrassSpike" -> 2;
            case "LeftSpike" -> 3;
            case "Platform" -> 4;
            case "RightSpike" -> 5;
            case "Spike" -> 6;
            default -> throw new IllegalArgumentException("Not a valid obstacle");
        };
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
    
    

}
