package main.com.majornick.compression.huffman;

public class HuffmanInternalNode implements HuffmanNode {

    private final int weight;
    private final HuffmanNode leftChild;
    private final HuffmanNode rightChild;

    public HuffmanInternalNode(HuffmanNode leftChild, HuffmanNode rightChild, int weight) {
        this.weight = weight;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }
}
