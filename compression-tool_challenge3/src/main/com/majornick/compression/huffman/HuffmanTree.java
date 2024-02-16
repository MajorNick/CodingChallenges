package main.com.majornick.compression.huffman;

import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;

    public HuffmanTree(Map<Character, Integer> frequencyMap) {
        buildTree(frequencyMap);
    }

    public static void main(String[] args) {
        HashMap<Test, Integer> test = new HashMap<>();
        Test l = new Test();
        l.setValue("abc");
        test.put(l, 5);
        System.out.println(test.get(l));
        l.setLength(5);

        System.out.println(test.get(l));


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

    public static class Test {
        String value;
        Integer length;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return Objects.equals(value, test.value) && Objects.equals(length, test.length);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, length);
        }

        public int getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }


    }

}
