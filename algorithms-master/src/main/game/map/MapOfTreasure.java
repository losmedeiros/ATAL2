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
        // Obtemos as coordenadas do tesouro
        Point treasureCoordinates = getTreasureCoordinates();
        if (treasureCoordinates != null) {
            // Exibe as coordenadas do tesouro
            System.out.println("Revealing treasure coordinates: " + treasureCoordinates);
            // Marca 'F' no mapa com o caractere do tesouro
            map.updateSymbol(treasureCoordinates, TreasureChest.CHEST_TRESURE_CHARACTER); // Marca 'F' no mapa
        } else {
            System.out.println("No treasure coordinates found!");
        }
    }

    // Getter para as coordenadas do tesouro
    public Point getTreasureCoordinates() {
        return treasureCoordinates;
    }

    // Setter para as coordenadas do tesouro
    public void setTreasureCoordinates(Point treasureCoordinates) {
        this.treasureCoordinates = treasureCoordinates;
    }

    // Método para salvar as coordenadas do tesouro na inicialização
    public void saveTreasureCoordinates() {
        if (treasureCoordinates != null) {
            System.out.println("Treasure coordinates saved: " + treasureCoordinates);
            // Aqui podemos salvar as coordenadas, que poderão ser acessadas pela estratégia ou pelo mapa
        } else {
            System.out.println("Treasure coordinates are not initialized yet!");
        }
    }
}
