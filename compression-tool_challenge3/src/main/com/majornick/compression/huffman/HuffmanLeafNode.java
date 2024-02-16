package main.com.majornick.compression.huffman;

public class HuffmanLeafNode implements HuffmanNode {
    private int weight;
    private char character;


    public HuffmanLeafNode(char character, int weight) {
        this.weight = weight;
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
