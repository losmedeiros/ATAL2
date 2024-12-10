package main.strategies;

import java.util.ArrayList;
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
            // Verificar caminho livre até o mapa do tesouro
            List<Point> pathToTreasureMap = findPathToDestination(robotLocation, treasureMapLocation, map);
            if (pathToTreasureMap != null && !pathToTreasureMap.isEmpty()) {
                return pathToTreasureMap.get(0); // Seguir o próximo passo do caminho
            }
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

                // Chama o método de salvar as coordenadas do tesouro
                mapOfTreasure.saveTreasureCoordinates();  // Agora salva as coordenadas
            }
        }

        // Atualizar a localização do tesouro para busca
        if (treasureCoordinates == null) {
            treasureCoordinates = map.findTreasureCoordinates(); // Usar o método findTreasureCoordinates
            System.out.println("Found treasure coordinates: " + treasureCoordinates);
        }

        if (treasureCoordinates != null && !robotLocation.equals(treasureCoordinates)) {
            // Verificar caminho livre até o tesouro
            List<Point> pathToTreasure = findPathToDestination(robotLocation, treasureCoordinates, map);
            if (pathToTreasure != null && !pathToTreasure.isEmpty()) {
                return pathToTreasure.get(0); // Seguir o próximo passo do caminho
            }
        }

        // Escolher qualquer ponto seguro disponível como fallback
        for (Point step : possibleSteps) {
            if (!visitedPoints.contains(step)) {
                return step;
            }
        }

        return null; // Sem movimento viável
    }

    // Encontra o caminho livre até o destino (sem obstáculos)
    private List<Point> findPathToDestination(Point start, Point destination, Map map) {
        Set<Point> visited = new HashSet<>();
        visited.add(start);

        // Usar BFS ou DFS para encontrar o caminho
        List<Point> queue = new ArrayList<>();
        queue.add(start);

        // Lista para armazenar o caminho
        List<Point> path = new ArrayList<>();
        boolean pathFound = false;

        while (!queue.isEmpty()) {
            Point current = queue.remove(0);

            // Se chegou ao destino, caminho encontrado
            if (current.equals(destination)) {
                pathFound = true;
                break;
            }

            // Adicionar vizinhos não visitados
            for (Point neighbor : map.getNeighbors(current)) {
                if (!visited.contains(neighbor) && map.isSafePoint(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    // Guardar o caminho
                    path.add(current);
                }
            }
        }

        // Retornar o caminho, se encontrado
        if (pathFound) {
            return reconstructPath(path, destination, map); // Passando map aqui
        }

        return null; // Caminho não encontrado
    }


    // Reconstruir o caminho a partir dos pontos encontrados
    private List<Point> reconstructPath(List<Point> path, Point destination, Map map) {
        List<Point> fullPath = new ArrayList<>();
        Point current = destination;
        while (current != null) {
            fullPath.add(current);
            current = getPreviousPoint(path, current, map); // Passando map aqui
        }
        java.util.Collections.reverse(fullPath);
        return fullPath;
    }


 // Obter o ponto anterior no caminho
    private Point getPreviousPoint(List<Point> path, Point current, Map map) {
        for (Point point : path) {
            if (map.getNeighbors(point).contains(current)) {
                return point;
            }
        }
        return null;
    }


}