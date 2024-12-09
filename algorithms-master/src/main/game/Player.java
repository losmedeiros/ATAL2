package main.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.game.map.Map;
import main.game.map.Point;
import main.strategies.Strategy;

public class Player {
    public static final String CHARACTER = "W";
    private Strategy strategy;

    public Player(Strategy strategy) {
        this.strategy = strategy;
    }

    public Point evaluatePossbileNextStep(Map map) {
        Point robotLocation = map.getRobotLocation();
        List<Point> safeSteps = calculateSafeSteps(robotLocation, map);
        return this.strategy.evaluatePossbileNextStep(safeSteps, map);
    }

    private List<Point> calculatePossibleSteps(Point robotLocation) {
        List<Point> possibleNextSteps = new ArrayList<>();
        possibleNextSteps.add(new Point(robotLocation.getPositionX(), robotLocation.getPositionY() + 1)); // direita
        possibleNextSteps.add(new Point(robotLocation.getPositionX() + 1, robotLocation.getPositionY())); // abaixo
        possibleNextSteps.add(new Point(robotLocation.getPositionX() - 1, robotLocation.getPositionY())); // acima
        possibleNextSteps.add(new Point(robotLocation.getPositionX(), robotLocation.getPositionY() - 1)); // esquerda
        return possibleNextSteps;
    }

    private List<Point> calculateSafeSteps(Point robotLocation, Map map) {
        List<Point> safeSteps = new LinkedList<>();
        for (Point step : calculatePossibleSteps(robotLocation)) {
            if (map.isWithinBounds(step) && map.isSafePoint(step)) {
                safeSteps.add(step);
            }
        }
        return safeSteps;
    }
}
