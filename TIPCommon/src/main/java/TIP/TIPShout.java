package TIP;

import utils.TIPException;
import utils.TIPMessageUtils;

public class TIPShout extends TIPMessage {

    private final String message;
    private final Shout shout;

    /**
     * Constructs a TIPShout object from a given message.
     * @param message String containing the message.
     * @throws TIPException If the message is not one of the valid shout messages (see Shout class).
     */
    TIPShout(String message) throws TIPException {
        super(Opcode.SHOUT);
        this.shout = Shout.getShout(message);
        this.message = message;
    }

    /**
     * Constructs a TIPShout object from a given name and shout object.
     * @param shout Shout object corresponding to the state of shout message.
     * @param name String with the name of the player.
     */
    public TIPShout(Shout shout, String name) {
        super(Opcode.SHOUT);
        this.shout = shout;
        this.message = this.shout.getShoutMessage(name);
    }

    @Override
    public byte[] encoded() {
        int lenBody = this.message.length();
        byte[] messBytes = new byte[lenBody + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(this.message);
        System.arraycopy(bodyBytes, 0, messBytes, 1, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "SHOUT " + this.message;
    }

    public Shout getShout() {
        return this.shout;
    }

    public String getMessage() {
        return this.message;
    }

}
