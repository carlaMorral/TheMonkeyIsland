package utils;

public class TIPMessageUtils {

    /**
     * Returns an array of 4 bytes containing the conversion of a 32-bit integer.
     * @param number 32-bit integer to convert to bytes.
     * @param endianness Endianness (order of the bytes) used.
     * @return The array of 4 bytes that represent the 32-bit integer.
     */
    public static byte[] int32ToBytes(int number, TIPMessageUtils.Endianness endianness) {
        byte[] bytes = new byte[4];

        if(TIPMessageUtils.Endianness.BIG_ENDIAN == endianness) {
            bytes[0] = (byte)((number >> 24) & 0xFF);
            bytes[1] = (byte)((number >> 16) & 0xFF);
            bytes[2] = (byte)((number >> 8) & 0xFF);
            bytes[3] = (byte)(number & 0xFF);
        }
        else {
            bytes[0] = (byte)(number & 0xFF);
            bytes[1] = (byte)((number >> 8) & 0xFF);
            bytes[2] = (byte)((number >> 16) & 0xFF);
            bytes[3] = (byte)((number >> 24) & 0xFF);
        }
        return bytes;
    }

    /**
     * Returns the equivalent 32-bit integer from an array of 4 bytes.
     * @param bytes Array of 4 bytes to convert.
     * @param endianness Endianness (order of the bytes) used.
     * @return The integer equivalent to the array of 4 bytes.
     */
    public static int bytesToInt32(byte[] bytes, TIPMessageUtils.Endianness endianness) {
        int number;

        if(TIPMessageUtils.Endianness.BIG_ENDIAN == endianness) {
            number = ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) |
                    ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        }
        else {
            number = (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) |
                    ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
        }
        return number;
    }

    /**
     * Returns the array of bytes equivalent to the string inputted. Each character must be using
     * extended ASCII (SO/IEC 8859-1), and it is converted to a single byte.
     * @param str String to convert to array of bytes.
     * @return The array of bytes equivalent to the given string.
     */
    public static byte[] stringToBytes(String str) {
        int lenStr;
        lenStr = str.length();
        byte[] bStr = new byte[lenStr];

        for (int i = 0; i < lenStr; i++) {
            bStr[i] = (byte) str.charAt(i);
        }

        return bStr;
    }

    /**
     * Returns a string with characters within the extended ASCII (SO/IEC 8859-1), making use of
     * characters above code 127, from an array of bytes. Each char is represented by a single byte.
     * @param bytes Array of bytes to convert.
     * @return String of characters.
     */
    public static String bytesToString(byte[] bytes) {
        String result;
        int lenBytes;
        char[] cStr = new char[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            cStr[i] = (char) ((bytes[i] + 256) % 256);
        }
        result = String.valueOf(cStr);

        return result.trim();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().trim();
    }

    public enum Endianness {
        BIG_ENDIAN,
        LITTLE_ENDIAN
    }
}
