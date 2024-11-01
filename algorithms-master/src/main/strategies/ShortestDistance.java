package main.strategies;

import java.util.List;

import main.game.map.Map;
import main.game.map.Point;

public class ShortestDistance implements Strategy {

	@Override
	public Point evaluatePossbileNextStep(List<Point> possibleNextSteps, Map map) {
		Point treasureLocation = map.getTreasureLocation();
		Point bestStep = null;
		double shortestDistance = Double.MAX_VALUE;

		for (Point step : possibleNextSteps) {
			double distance = calculateDistance(step, treasureLocation);
			if (distance < shortestDistance) {
				shortestDistance = distance;
				bestStep = step;
			}
		}

		return bestStep;
	}

	private double calculateDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getPositionX() - p2.getPositionX(), 2)
				+ Math.pow(p1.getPositionY() - p2.getPositionY(), 2));
	}
}
