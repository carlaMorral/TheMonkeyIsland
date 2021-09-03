package utils;

import TIP.Opcode;
import java.io.*;
import java.util.Arrays;

public class TIPConnUtils {
    private final int STRSIZE = 10000;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public TIPConnUtils(InputStream inputStream, OutputStream outputStream) throws IOException {
        dataInputStream = new DataInputStream(inputStream);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    /**
     * Reads a given number of bytes from dataInputStream.
     * @param numBytes int Number of bytes to read from stream.
     * @return Array of bytes containing the information read.
     * @throws IOException If no byte is available because the stream is at the end of the file.
     */
    byte[] readBytes(int numBytes) throws IOException {
        int len = 0;
        byte[] bStr = new byte[numBytes];
        int bytesRead;
        do {
            bytesRead = dataInputStream.read(bStr, len, numBytes-len);
            if (bytesRead == -1) {
                throw new IOException("Broken Pipe " + numBytes + " bytes");
            }
            len += bytesRead;
        } while (len < numBytes);
        return bStr;
    }

    /**
     * Reads information from dataInputStream until a zero byte is reached or STRSIZE bytes
     * have been read.
     * @return Array of bytes containing the information read.
     * @throws IOException If no byte is available because the stream is at the end of the file.
     */
    byte[] readBytes() throws IOException {
        int len = 0;
        byte[] bStr = new byte[STRSIZE];
        int bytesRead;
        byte lastElement = (byte) 0xFF;
        do {
            bytesRead = dataInputStream.read(bStr, len, Math.min(1, STRSIZE - len));
            if (bytesRead == -1) {
                throw new IOException("Broken Pipe multiple bytes");
            }
            len += bytesRead;
            if (len > 0) {
                lastElement = bStr[len-1];
            }
        } while (lastElement != 0 && len < STRSIZE);

        return Arrays.copyOfRange(bStr, 0, len);
    }

    /**
     * Writes an array of bytes to the dataOutputStream.
     * @param bytes Array of bytes to write.
     * @throws IOException If it is not possible to write.
     */
    public void writeBytes(byte[] bytes) throws IOException {
        dataOutputStream.write(bytes, 0, bytes.length);
    }

    /**
     * Reads a stream of bytes from the dataInputStream. It changes behaviour depending on
     * the first byte read (Opcode), following the protocol.
     * @return byte[] with the read information.
     * @throws IOException If no byte is available because the stream is at the end of the file.
     */
    public byte[] readStream() throws IOException {
        int len = 0;
        byte[] bStr = new byte[1];
        int bytesRead;
        // Read first byte (OPCODE)
        do {
            bytesRead = dataInputStream.read(bStr, 0, 1);
            if (bytesRead == -1) {
                throw new IOException("Broken Pipe Stream");
            }
            len += bytesRead;
        } while (len < 1);
        // Read message
        byte[] byteMess;
        if (bStr[0] == Opcode.HELLO.getCode()) {
            byte[] byteMessId = this.readBytes(4);
            byte[] byteMessName = this.readBytes();
            byteMess = new byte[4 + byteMessName.length];
            System.arraycopy(byteMessId, 0, byteMess, 0, byteMessId.length);
            System.arraycopy(byteMessName, 0, byteMess, 4, byteMessName.length);
        } else if (bStr[0] == Opcode.HASH.getCode()) {
            byteMess = this.readBytes(32);
        } else {
            byteMess = this.readBytes();
        }

        byte[] byteOut = new byte[byteMess.length + 1];
        byteOut[0] = bStr[0];
        System.arraycopy(byteMess, 0, byteOut, 1, byteMess.length);

        return byteOut;
    }
}
