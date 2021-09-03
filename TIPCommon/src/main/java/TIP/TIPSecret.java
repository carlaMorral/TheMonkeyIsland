package TIP;

import utils.TIPException;
import utils.TIPMessageUtils;

public class TIPSecret extends TIPMessage{

    private final String secret;

    public TIPSecret(String secret) throws TIPException {
        super(Opcode.SECRET);
        if (this.isNumber(secret)) {
            this.secret = secret;
        } else {
            throw new TIPException("Secret is not an integer.");
        }
    }

    @Override
   public byte[] encoded() {
        int lenBody = this.secret.length();
        byte[] messBytes = new byte[lenBody + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(this.secret);
        System.arraycopy(bodyBytes, 0, messBytes, 1, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "SECRET " + this.secret;
    }

    /**
     * Returns true if the given string is an integer, false otherwise.
     * @param secret String to check if it is an integer.
     * @return boolean: true if input string can be casted to an integer, false otherwise.
     */
    private boolean isNumber(String secret) {
        if (secret == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(secret);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public String getSecret() {
        return this.secret;
    }

    public int getSecretNumber() throws TIPException {
        if (this.isNumber(secret)) {
            return Integer.parseInt(this.secret);
        } else {
            throw new TIPException("Secret is not an integer.");
        }
    }
}
