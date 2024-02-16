package main.com.majornick.compression.huffman;

public interface HuffmanNode extends Comparable<HuffmanNode> {
    boolean isLeaf();

    int getWeight();

    default int compareTo(HuffmanNode otherNode) {
        return Integer.compare(this.getWeight(), otherNode.getWeight());
    }
}
