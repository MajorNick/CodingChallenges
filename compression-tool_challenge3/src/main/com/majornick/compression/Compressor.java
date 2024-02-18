package main.com.majornick.compression;

import main.com.majornick.compression.bitio.BitWriter;
import main.com.majornick.compression.huffman.HuffmanInternalNode;
import main.com.majornick.compression.huffman.HuffmanLeafNode;
import main.com.majornick.compression.huffman.HuffmanNode;
import main.com.majornick.compression.huffman.HuffmanTree;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Compressor {
    private static final String COMPRESSED_FILE_EXTENSION = "huff";
    private final String filename;
    private RandomAccessFile fileReader;
    private Map<Character, Integer> frequencies;

    public Compressor(String filename) {
        this.filename = filename;
        try {
            fileReader = new RandomAccessFile(filename, "r");
            process();

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
    }

    private static String changeExtension(String filename) {
        return String.format("%s.%s", filename.substring(0, filename.lastIndexOf('.')), Compressor.COMPRESSED_FILE_EXTENSION);
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

    private void process() throws IOException {

        frequencies = readOccurrences();
        HuffmanTree huffmanTree = new HuffmanTree(frequencies);
        ArrayList<CharacterRoad> bitRep = getBitRep(huffmanTree);

        LinkedHashMap<Integer, String> codes = getCanonicalCodes(bitRep);
        String compressedFilePath = changeExtension(filename);
        writeInFile(compressedFilePath, codes);
    }


    private Map<Character, Integer> readOccurrences() throws IOException {
        Map<Character, Integer> frequencies = new HashMap<>();

        int b = fileReader.read();
        while (b != -1) {
            char c = (char) b;
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            b = fileReader.read();
        }
        return frequencies;
    }

    private LinkedHashMap<Integer, String> getCanonicalCodes(ArrayList<CharacterRoad> bitRep) {
        LinkedHashMap<Integer, String> codes = new LinkedHashMap<>();
        if (bitRep.isEmpty()) {
            return codes;
        }
        int size = bitRep.get(0).roadLength;
        codes.put((int) bitRep.get(0).c, "0".repeat(size));
        int current = 0;
        for (int i = 1; i < bitRep.size(); i++) {
            current++;
            int shift = bitRep.get(i).roadLength - bitRep.get(i - 1).roadLength;
            current <<= shift;
            codes.put((int) bitRep.get(i).c, Integer.toBinaryString(current));
        }
        current++; // for ending
        codes.put(-1, Integer.toBinaryString(current));
        return codes;
    }

    private void writeInFile(String path, LinkedHashMap<Integer, String> codes) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            writeCanonicalCodesMap(fileWriter, codes);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(path, true)) {
            BitWriter bitWriter = new BitWriter(fileOutputStream);
            writeCodedContent(bitWriter, codes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeCanonicalCodesMap(FileWriter fileWriter, LinkedHashMap<Integer, String> codes) {
        codes.forEach((key, value) -> {
            try {
                fileWriter.write(String.format("%s : %s\n", key, value));
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        });
    }


    private void writeCodedContent(BitWriter bitWriter, LinkedHashMap<Integer, String> codes) throws IOException {
        fileReader.seek(0);
        int b = fileReader.read();
        while (b != -1) {
            String codedChar = codes.get(b);
            writeBitByBit(bitWriter, codedChar);
            b = fileReader.read();
        }
        bitWriter.endWriting();

    }

    private void writeBitByBit(BitWriter bitWriter, String codedChar) {
        codedChar.chars().forEach(a -> {
            try {
                bitWriter.writeBit(a - (int) ('0'));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
