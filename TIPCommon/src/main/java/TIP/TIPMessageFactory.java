package TIP;

import utils.TIPException;
import utils.TIPMessageUtils;
import java.util.Arrays;

public class TIPMessageFactory {

    private static TIPMessageFactory factory;

    private TIPMessageFactory() {}

    public static TIPMessageFactory getInstance() {
        if (factory == null) {
            factory = new TIPMessageFactory();
        }
        return factory;
    }

    public TIPMessage getTIPMessage(byte[] bytes) throws TIPException {
        return decoded(bytes);
    }

    /**
     * Returns a TIPMessage from an array of bytes.
     * @param bytes Array of bytes to convert.
     * @return TIPMessage with the opcode as the value in the first byte, and the body as the
     * following bytes.
     * @throws TIPException if last byte of sequence is not zero, or invalid opcode.
     */
    private TIPMessage decoded(byte[] bytes) throws TIPException {
        Opcode opcode = Opcode.codeOf(bytes[0]);
        if (bytes[bytes.length - 1] != (byte) 0x00 && opcode != Opcode.HASH){
            throw new TIPException("The last byte of the sequence is not the zero byte!");
        }

        byte[] bodyBytes = Arrays.copyOfRange(bytes, 1, bytes.length - 1);

        if (opcode == Opcode.HELLO) {
            int id = TIPMessageUtils.bytesToInt32(Arrays.copyOfRange(bodyBytes, 0,4),
                    TIPMessageUtils.Endianness.BIG_ENDIAN);
            String body = TIPMessageUtils.bytesToString(Arrays.copyOfRange(bodyBytes, 4,
                    bodyBytes.length));
            return new TIPHello(body, id);
        } else if (opcode == Opcode.HASH) {
            return new TIPHash(Arrays.copyOfRange(bytes, 1, bytes.length));
        } else if (opcode == Opcode.SECRET) {
            return new TIPSecret(TIPMessageUtils.bytesToString(bodyBytes));
        } else if (opcode == Opcode.INSULT) {
            return new TIPInsult(TIPMessageUtils.bytesToString(bodyBytes));
        } else if (opcode == Opcode.COMEBACK) {
            return new TIPComeback(TIPMessageUtils.bytesToString(bodyBytes));
        } else if (opcode == Opcode.SHOUT) {
            return new TIPShout(TIPMessageUtils.bytesToString(bodyBytes));
        } else {
            return new TIPError();
        }
    }

}
