package main.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.game.map.Map;
import main.game.map.MapOfTreasure;
import main.game.map.Point;
import main.game.map.TreasureChest;

// A classe implementa a interface Strategy
public class FewerObstaclesAndShorterDistance implements Strategy {

    private Set<Point> visitedPoints = new HashSet<>(); // Rastreia os pontos visitados
    private Point treasureCoordinates = null; // Coordenadas do tesouro reveladas

    @Override
    public Point evaluatePossbileNextStep(List<Point> possibleSteps, Map map) {
        if (possibleSteps == null || possibleSteps.isEmpty()) {
            return null; // Sem movimento possível
        }

        Point robotLocation = map.getRobotLocation();
        visitedPoints.add(robotLocation); // Marcar o ponto atual como visitado

        // Procurar a localização do mapa do tesouro (B)
        Point treasureMapLocation = map.findPointByChar(MapOfTreasure.CHARACTER);

        System.out.println("Robot location: " + robotLocation);
        System.out.println("Treasure map (B) location: " + treasureMapLocation);
        System.out.println("Revealed Treasure (F) coordinates: " + treasureCoordinates);

        // Priorizar alcançar o "mapa do tesouro" (B)
        if (treasureMapLocation != null && !robotLocation.equals(treasureMapLocation)) {
            return findShortestPathAvoidingVisited(possibleSteps, treasureMapLocation, robotLocation);
        }

        // Revelar o tesouro ao alcançar "B"
        if (treasureMapLocation != null && robotLocation.equals(treasureMapLocation)) {
            System.out.println("Revealing treasure...");
            Object obstacle = map.getObstacleAt(treasureMapLocation); // Obtenha o obstáculo (pode ser um objeto genérico)
            
            if (obstacle instanceof MapOfTreasure) { // Verifique se é do tipo MapOfTreasure
                MapOfTreasure mapOfTreasure = (MapOfTreasure) obstacle;
                treasureCoordinates = mapOfTreasure.getTreasureCoordinates();
                System.out.println("Treasure coordinates revealed: " + treasureCoordinates);
                map.updateSymbol(treasureCoordinates, TreasureChest.CHEST_TRESURE_CHARACTER); // Atualiza o mapa com 'F'
            }
        }

        // Atualizar a localização do tesouro para busca
        if (treasureCoordinates == null) {
            treasureCoordinates = map.findTreasureCoordinates(); // Usar o método findTreasureCoordinates
            System.out.println("Found treasure coordinates: " + treasureCoordinates);
        }

        if (treasureCoordinates != null && !robotLocation.equals(treasureCoordinates)) {
            return findShortestPathAvoidingVisited(possibleSteps, treasureCoordinates, robotLocation);
        }

        // Escolher qualquer ponto seguro disponível como fallback
        for (Point step : possibleSteps) {
            if (!visitedPoints.contains(step)) {
                return step;
            }
        }

        return null; // Sem movimento viável
    }

    // Atualizado para evitar o ponto anterior
    private Point findShortestPathAvoidingVisited(List<Point> possibleSteps, Point target, Point previousLocation) {
        Point bestStep = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Point step : possibleSteps) {
            if (!visitedPoints.contains(step) && !step.equals(previousLocation)) {
                double distance = calculateDistance(step, target);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    bestStep = step;
                }
            }
        }
        return bestStep;
    }


    // Calcula a distância euclidiana entre dois pontos
    private double calculateDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getPositionX() - b.getPositionX(), 2)
                + Math.pow(a.getPositionY() - b.getPositionY(), 2));
    }
}
