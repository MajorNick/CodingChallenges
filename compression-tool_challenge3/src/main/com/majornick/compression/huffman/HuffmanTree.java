package main.com.majornick.compression.huffman;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanNode root;

    public HuffmanTree(Map<Character, Integer> frequencyMap) {
        buildTree(frequencyMap);
    }

    public HuffmanNode getRoot() {
        return root;
    }

    private void buildTree(Map<Character, Integer> frequencyMap) {
        if (frequencyMap.isEmpty()) {
            root = null;
            return;
        }

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Comparator.comparingInt(HuffmanNode::getWeight));
        for (Character c : frequencyMap.keySet()) {
            queue.add(new HuffmanLeafNode(c, frequencyMap.get(c)));
        }

        while (queue.size() != 1) {
            HuffmanNode a = queue.poll();
            HuffmanNode b = queue.poll();
            if (a == null || b == null) {
                return;
            }
            HuffmanInternalNode node = new HuffmanInternalNode(a, b, a.getWeight() + b.getWeight());
            queue.add(node);
        }
        root = queue.poll();
    }



}
