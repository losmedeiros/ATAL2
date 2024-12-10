package main.game.map;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private String id;
    private int positionX;
    private int positionY;
    private double weight;
    private char symbol; // Adiciona o atributo símbolo para representar o conteúdo do ponto
    
    // Construtor padrão
    public Point(int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.symbol = '*'; // Define um símbolo padrão
    }
    
    // Construtor adicional para inicializar com símbolo
    public Point(int x, int y, char symbol) {
        this.positionX = x;
        this.positionY = y;
        this.symbol = symbol;
    }
    
    // Getters e setters
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point p = (Point) obj;
        return this.positionX == p.getPositionX() && this.positionY == p.getPositionY();
    }

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", weight=" + weight +
                ", symbol=" + symbol +
                '}';
    }

    // Adiciona o método getNeighbors para obter vizinhos diretos
    public List<Point> getNeighbors() {
        List<Point> neighbors = new ArrayList<>();
        
        // Adiciona vizinhos (cima, baixo, esquerda, direita)
        neighbors.add(new Point(positionX, positionY - 1)); // Cima
        neighbors.add(new Point(positionX, positionY + 1)); // Baixo
        neighbors.add(new Point(positionX - 1, positionY)); // Esquerda
        neighbors.add(new Point(positionX + 1, positionY)); // Direita
        
        return neighbors;
    }
}
