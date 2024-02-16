package main.com.majornick.compression;

import main.com.majornick.compression.huffman.HuffmanInternalNode;
import main.com.majornick.compression.huffman.HuffmanLeafNode;
import main.com.majornick.compression.huffman.HuffmanNode;
import main.com.majornick.compression.huffman.HuffmanTree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Compressor {
    private static final String COMPRESSED_FILE_EXTENSION = "huff";
    private final String filename;
    private FileReader fileReader;
    private Map<Character, Integer> frequencies;

    public Compressor(String filename) {
        this.filename = filename;
        try {
            fileReader = new FileReader(filename);
            process();

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    private static String changeExtension(String filename) {
        return String.format("%s.%s",
                filename.substring(0, filename.lastIndexOf('.')),
                Compressor.COMPRESSED_FILE_EXTENSION);
    }

    private static ArrayList<CharacterRoad> getBitRep(HuffmanTree tree) {
        ArrayList<CharacterRoad> roads = new ArrayList<>();
        if (tree.getRoot() != null) {
            recursiveGetBitWay(roads, tree.getRoot(), new StringBuilder());
        }
        roads.sort((a, b) -> {
            if (a.roadLength == b.roadLength) {
                return Character.compare(a.c, b.c);
            }
            return Integer.compare(a.roadLength, b.roadLength);
        });
        return roads;
    }

    private static void recursiveGetBitWay(ArrayList<CharacterRoad> storage, HuffmanNode node, StringBuilder road) {
        if (node.isLeaf()) {
            if (road.isEmpty()) {
                road.append('0');
            }
            storage.add(new CharacterRoad(((HuffmanLeafNode) node).getCharacter(), road.length()));
        } else {
            HuffmanInternalNode internalNode = (HuffmanInternalNode) node;
            if (internalNode.getLeftChild() != null) {
                recursiveGetBitWay(storage, internalNode.getLeftChild(), new StringBuilder(road).append('0'));
            }
            if (internalNode.getRightChild() != null) {
                recursiveGetBitWay(storage, internalNode.getRightChild(), new StringBuilder(road).append('1'));
            }
        }
    }

    private void process() {
        frequencies = readOccurrences();
        HuffmanTree huffmanTree = new HuffmanTree(frequencies);
        ArrayList<CharacterRoad> bitRep = getBitRep(huffmanTree);

        LinkedHashMap<Character, String> codes = getCanonicalCodes(bitRep);
        System.out.println(bitRep);
        String compressedFilePath = changeExtension(filename);
        writeInFile(compressedFilePath, codes);
    }


    private void writeInFile(String path, LinkedHashMap<Character, String> codes) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            writeCanonicalCodesMap(fileWriter, codes);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    private void writeCanonicalCodesMap(FileWriter fileWriter, LinkedHashMap<Character, String> codes) {
        codes.forEach((key, value) -> {
            try {
                fileWriter.write(
                        String.format("%s : %s\n", key, value)
                );
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        });
    }

    private Map<Character, Integer> readOccurrences() {
        Map<Character, Integer> frequencies = new HashMap<>();
        try {
            int b = fileReader.read();
            while (b != -1) {
                char c = (char) b;
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
                b = fileReader.read();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return frequencies;
    }

    private LinkedHashMap<Character, String> getCanonicalCodes(ArrayList<CharacterRoad> bitRep) {
        LinkedHashMap<Character, String> codes = new LinkedHashMap<>();
        if (bitRep.isEmpty()) {
            return codes;
        }
        int size = bitRep.get(0).roadLength;
        codes.put(bitRep.get(0).c, "0".repeat(size));
        int current = 0;
        for (int i = 1; i < bitRep.size(); i++) {
            current++;
            int shift = bitRep.get(i).roadLength - bitRep.get(i - 1).roadLength;
            current <<= shift;
            codes.put(bitRep.get(i).c, Integer.toBinaryString(current));
        }
        return codes;
    }


    public int getCharacterFrequency(char a) {
        return frequencies.getOrDefault(a, 0);
    }


    private static class CharacterRoad {
        public char c;
        public int roadLength;

        public CharacterRoad(Character c, int roadLength) {
            this.c = c;
            this.roadLength = roadLength;
        }
    }
}
