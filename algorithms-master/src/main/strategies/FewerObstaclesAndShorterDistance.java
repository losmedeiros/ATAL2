package main.strategies;

import java.util.List;

import main.game.map.Map;
import main.game.map.Point;

public class FewerObstaclesAndShorterDistance implements Strategy {

	@Override
	public Point evaluatePossbileNextStep(List<Point> possibleNextSteps, Map map) {
		Point treasureLocation = map.getTreasureLocation();
		Point bestStep = null;
		double bestScore = Double.MAX_VALUE;

		for (Point step : possibleNextSteps) {
			double distance = calculateDistance(step, treasureLocation);
			int monsterCount = countNearbyMonsters(step, map);

			double score = distance + (monsterCount * 2); // Peso maior para monstros
			if (score < bestScore) {
				bestScore = score;
				bestStep = step;
			}
		}

		return bestStep;
	}

	private double calculateDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getPositionX() - p2.getPositionX(), 2)
				+ Math.pow(p1.getPositionY() - p2.getPositionY(), 2));
	}

	private int countNearbyMonsters(Point point, Map map) {
		int count = 0;
		List<Point> neighbors = map.getNeighbors(point);

		for (Point neighbor : neighbors) {
			if (map.isMonster(neighbor)) {
				count++;
			}
		}

		return count;
	}

}
