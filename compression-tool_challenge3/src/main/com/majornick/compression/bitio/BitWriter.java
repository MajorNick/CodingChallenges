package main.com.majornick.compression.bitio;

import java.io.FileOutputStream;
import java.io.IOException;

public class BitWriter {
    private final FileOutputStream byteWriter;
    private byte bufferedByte;
    private int currentShift;

    public BitWriter(FileOutputStream byteWriter) {
        this.byteWriter = byteWriter;
    }

    public void writeBit(int bit) throws IOException, RuntimeException {
        if (bit > 1 || bit < 0) {
            throw new RuntimeException("bit must be between 0 or 1");
        }
        bufferedByte <<= 1;
        bufferedByte += bit;
        currentShift++;
        if (currentShift == 7) {
            byteWriter.write(bufferedByte);
            currentShift = 0;
        }
    }

    public void endWriting() throws IOException {
        bufferedByte <<= (7 - currentShift);
        byteWriter.write(bufferedByte);
    }

}
