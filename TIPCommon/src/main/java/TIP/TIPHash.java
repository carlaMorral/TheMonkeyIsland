package TIP;

import utils.TIPException;
import utils.TIPMessageUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TIPHash extends TIPMessage {

    private final byte[] hash;

    /**
     * Constructs a TIPHash object from an array of 32 bytes.
     * @param hash Array of 32 bytes containing the hash.
     * @throws TIPException If the given array does not has a length of 32.
     */
    TIPHash(byte[] hash) throws TIPException {
        super(Opcode.HASH);
        if (hash.length == 32) {
            this.hash = hash;
        } else{
            throw new TIPException("Hash length is not 32 bytes.");
        }
    }

    /**
     * Constructs a TIPHash object from a secret string, using SHA-256.
     * @param secret String to hash.
     * @throws NoSuchAlgorithmException If hash algorithm is not available.
     */
    public TIPHash(String secret) throws NoSuchAlgorithmException {
        super(Opcode.HASH);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        hash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] encoded() {
        byte[] messBytes = new byte[33];
        messBytes[0] = (byte) this.opcode.getCode();
        System.arraycopy(hash, 0, messBytes, 1, hash.length);
        return messBytes;
    }

    @Override
    public String toString() {
        return "HASH " + TIPMessageUtils.bytesToHex(hash);
    }

    /**
     * Returns the hash as a string of hexadecimal values.
     * @return String of the hash.
     */
    public String getHashString() {
        return TIPMessageUtils.bytesToHex(hash);
    }

    public byte[] getHash(){
        return hash;
    }

    /**
     * Compares the hash of a given string with the hash of the TIPHash object.
     * @param otherSecret String whose hash will be compared.
     * @return true if the hash of the inputted string equals the hash of the TIPHash object,
     * false if they are different.
     * @throws NoSuchAlgorithmException If hash algorithm is not available.
     */
    public boolean compare(String otherSecret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] otherHash = digest.digest(otherSecret.getBytes(StandardCharsets.UTF_8));
        return getHashString().equals(TIPMessageUtils.bytesToHex(otherHash));
    }

}
