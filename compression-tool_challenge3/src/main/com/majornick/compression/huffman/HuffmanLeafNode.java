package main.com.majornick.compression.huffman;

public class HuffmanLeafNode implements HuffmanNode {
    private final int weight;
    private final char character;


    public HuffmanLeafNode(char character, int weight) {
        this.weight = weight;
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }


    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public int getWeight() {
        return weight;
    }

}
