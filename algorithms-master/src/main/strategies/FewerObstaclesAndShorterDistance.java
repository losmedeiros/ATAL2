package main.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.game.map.Map;
import main.game.map.Point;

// A classe agora implementa a interface Strategy
public class FewerObstaclesAndShorterDistance implements Strategy {

    private Set<Point> visitedPoints = new HashSet<>(); // Atributo para rastrear pontos visitados

    @Override
    public Point evaluatePossbileNextStep(List<Point> possibleSteps, Map map) {
        if (possibleSteps == null || possibleSteps.isEmpty()) {
            return null; // Sem movimento possível
        }

        Point robotLocation = map.getRobotLocation();
        visitedPoints.add(robotLocation); // Marcar o ponto atual como visitado

        Point treasureMapLocation = map.findPointByChar('B');
        Point treasureLocation = map.findPointByChar('F');

        // Priorizar alcançar o "mapa do tesouro" (B)
        if (treasureMapLocation != null && !visitedPoints.contains(treasureMapLocation)) {
            return findShortestPathAvoidingVisited(possibleSteps, treasureMapLocation);
        }

        // Após alcançar "B", revelar e priorizar "F"
        if (treasureMapLocation != null && robotLocation.equals(treasureMapLocation)) {
            map.revealTreasure(robotLocation); // Método fictício que revela o tesouro
            treasureLocation = map.findPointByChar('F'); // Atualiza posição de "F"
        }

        // Priorizar alcançar "F"
        if (treasureLocation != null && !visitedPoints.contains(treasureLocation)) {
            return findShortestPathAvoidingVisited(possibleSteps, treasureLocation);
        }

        // Escolher qualquer ponto seguro disponível como fallback
        for (Point step : possibleSteps) {
            if (!visitedPoints.contains(step)) {
                return step;
            }
        }

        return null; // Sem movimento viável
    }

    private Point findShortestPathAvoidingVisited(List<Point> possibleSteps, Point target) {
        Point bestStep = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Point step : possibleSteps) {
            if (!visitedPoints.contains(step)) {
                double distance = calculateDistance(step, target);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    bestStep = step;
                }
            }
        }
        return bestStep;
    }

    private double calculateDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getPositionX() - b.getPositionX(), 2)
                + Math.pow(a.getPositionY() - b.getPositionY(), 2));
    }
}
