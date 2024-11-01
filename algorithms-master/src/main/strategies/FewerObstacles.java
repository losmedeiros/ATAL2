package main.strategies;

import java.util.List;

import main.game.map.Map;
import main.game.map.Point;

public class FewerObstacles implements Strategy {

	/**
	 * N is the next location p1 p2 p3 p4 N p5 p6 p7 p8
	 */

	@Override
	public Point evaluatePossbileNextStep(List<Point> possibleNextSteps, Map map) {
		Point bestStep = null;
		int fewestMonsters = Integer.MAX_VALUE;

		for (Point step : possibleNextSteps) {
			int monsterCount = countNearbyMonsters(step, map);
			if (monsterCount < fewestMonsters) {
				fewestMonsters = monsterCount;
				bestStep = step;
			}
		}

		return bestStep;
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
