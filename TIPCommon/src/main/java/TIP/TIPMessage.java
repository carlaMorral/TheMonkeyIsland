package TIP;

public abstract class TIPMessage {
    Opcode opcode;

    public TIPMessage(Opcode opcode) {
        this.opcode = opcode;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    /**
     * Returns the TIPMessage encoded as an array of bytes.
     * @return Array of bytes. The first byte is the opcode, the following are the body, and the
     * last one byte zero.
     */
    public abstract byte[] encoded();

    /**
     * Returns a string with all the information of the TIPMessage.
     * @return String of information of the object.
     */
    public abstract String toString();

}
