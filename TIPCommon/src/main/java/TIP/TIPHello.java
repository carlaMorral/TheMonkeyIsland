package TIP;

import utils.TIPMessageUtils;

public class TIPHello extends TIPMessage {

    private final String name;
    private final int id;

    public TIPHello(String name, int id) {
        super(Opcode.HELLO);
        this.name = name;
        this.id = id;
    }

    @Override
    public byte[] encoded() {
        int lenBody = this.name.length();
        byte[] messBytes = new byte[lenBody + 4 + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] idBytes = TIPMessageUtils.
                int32ToBytes(this.id, TIPMessageUtils.Endianness.BIG_ENDIAN);
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(this.name);
        System.arraycopy(idBytes, 0, messBytes, 1, idBytes.length);
        System.arraycopy(bodyBytes, 0, messBytes, 5, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "HELLO " + this.id + " " + this.name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
