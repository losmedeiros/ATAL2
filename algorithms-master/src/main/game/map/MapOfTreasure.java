package main.game.map;

public class MapOfTreasure extends Obstacle {
    public static final String CHARACTER = "B"; // Caractere que representa o mapa do tesouro
    private Point treasureCoordinates; // Coordenadas do tesouro (F)

    // Construtor que recebe as coordenadas do tesouro associado
    public MapOfTreasure(Point coordinates, Point treasureCoordinates) {
        super(coordinates); // Coordenadas do ponto "B"
        this.treasureCoordinates = treasureCoordinates; // Coordenadas do ponto "F"
    }

    // Método para revelar as coordenadas do tesouro
    public void revealTreasure(Map map) {
        Point treasureCoordinates = getTreasureCoordinates(); // Obtemos as coordenadas de F
        if (treasureCoordinates != null) {
            System.out.println("Revealing treasure coordinates: " + treasureCoordinates);
            map.updateSymbol(treasureCoordinates, TreasureChest.CHEST_TRESURE_CHARACTER); // Marca 'F' no mapa
        } else {
            System.out.println("No treasure coordinates found!");
        }
    }


    // Getter para as coordenadas do tesouro
    public Point getTreasureCoordinates() {
        return treasureCoordinates;
    }
}
