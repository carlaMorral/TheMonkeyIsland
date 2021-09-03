package TIP;

import data.TIPInsultContainer;
import utils.TIPException;
import utils.TIPMessageUtils;

public class TIPInsult extends TIPMessage {

    private final String insult;

    public TIPInsult(String insult) throws TIPException {
        super(Opcode.INSULT);
        if (TIPInsultContainer.getInstance().isInsult(insult)) {
            this.insult = insult;
        } else {
            throw new TIPException("Not a valid insult");
        }
    }

    @Override
    public byte[] encoded() {
        int lenBody = this.insult.length();
        byte[] messBytes = new byte[lenBody + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(this.insult);
        System.arraycopy(bodyBytes, 0, messBytes, 1, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "INSULT " + this.insult;
    }

    public String getInsult() {
        return this.insult;
    }

}
