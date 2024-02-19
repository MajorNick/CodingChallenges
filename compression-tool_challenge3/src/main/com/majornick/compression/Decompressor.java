package main.com.majornick.compression;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Decompressor {
    private final String filename;
    private String extension;

    public Decompressor(String filename) {
        this.filename = filename;
        try {
            process(filename);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void process(String filename) throws IOException {
        Map<Integer, String> codes = readExtensionAndCodes(filename);
        System.out.println(codes);
    }

    private Map<Integer, String> readExtensionAndCodes(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        HashMap<Integer, String> codes = new HashMap<>();
        while (true) {
            String line = reader.readLine();
            int dot = line.indexOf(':');
            String left = line.substring(0, dot - 1);
            String right = line.substring(dot + 2);
            if ("extension".equals(left)) {
                this.extension = right;
            } else {
                int c = Integer.parseInt(left);
                codes.put(c, right);
                if (c == -1) {
                    break;
                }
            }
        }
        reader.close();
        return codes;
    }


}
