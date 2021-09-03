package TIP;

import data.TIPInsultContainer;
import utils.TIPException;
import utils.TIPMessageUtils;

public class TIPComeback extends TIPMessage {

    private final String comeback;

    public TIPComeback(String comeback) throws TIPException {
        super(Opcode.COMEBACK);
        if (TIPInsultContainer.getInstance().isComeback(comeback)){
            this.comeback = comeback;
        } else {
            throw new TIPException("Not a valid comeback");
        }
    }

    @Override
    public byte[] encoded() {
        int lenBody = this.comeback.length();
        byte[] messBytes = new byte[lenBody + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(this.comeback);
        System.arraycopy(bodyBytes, 0, messBytes, 1, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "COMEBACK " + this.comeback;
    }

    public String getComeback() {
        return this.comeback;
    }

}
