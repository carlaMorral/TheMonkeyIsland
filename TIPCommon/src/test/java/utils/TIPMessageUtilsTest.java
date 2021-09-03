package utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TIPMessageUtilsTest {

    @Test
    public void int_32_to_bytes_test() {
        int number = 19961128;
        byte[] expected = new byte[4];
        expected[0] = (byte)(0x01);
        expected[1] = (byte)(0x30);
        expected[2] = (byte)(0x95);
        expected[3] = (byte)(0x28);
        byte[] bytes  = TIPMessageUtils.int32ToBytes(number, TIPMessageUtils.Endianness.BIG_ENDIAN);
        assertEquals(expected.length, bytes.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], bytes[i]);
        }
    }

    @Test
    public void bytes_to_int_32_test() {
        int expected = 765244800;
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(0x2D);
        bytes[1] = (byte)(0x9C);
        bytes[2] = (byte)(0xB5);
        bytes[3] = (byte)(0x80);
        int number  = TIPMessageUtils.bytesToInt32(bytes, TIPMessageUtils.Endianness.BIG_ENDIAN);
        assertEquals(expected, number);
    }

    @Test
    public void string_to_bytes_test() {
        String text = "The quick brown fox jumps over the lazy dog!";
        byte[] expected = new byte[44];
        expected[0] = (byte)(0x54);
        expected[1] = (byte)(0x68);
        expected[2] = (byte)(0x65);
        expected[3] = (byte)(0x20);
        expected[4] = (byte)(0x71);
        expected[5] = (byte)(0x75);
        expected[6] = (byte)(0x69);
        expected[7] = (byte)(0x63);
        expected[8] = (byte)(0x6b);
        expected[9] = (byte)(0x20);
        expected[10] = (byte)(0x62);
        expected[11] = (byte)(0x72);
        expected[12] = (byte)(0x6f);
        expected[13] = (byte)(0x77);
        expected[14] = (byte)(0x6e);
        expected[15] = (byte)(0x20);
        expected[16] = (byte)(0x66);
        expected[17] = (byte)(0x6f);
        expected[18] = (byte)(0x78);
        expected[19] = (byte)(0x20);
        expected[20] = (byte)(0x6a);
        expected[21] = (byte)(0x75);
        expected[22] = (byte)(0x6d);
        expected[23] = (byte)(0x70);
        expected[24] = (byte)(0x73);
        expected[25] = (byte)(0x20);
        expected[26] = (byte)(0x6f);
        expected[27] = (byte)(0x76);
        expected[28] = (byte)(0x65);
        expected[29] = (byte)(0x72);
        expected[30] = (byte)(0x20);
        expected[31] = (byte)(0x74);
        expected[32] = (byte)(0x68);
        expected[33] = (byte)(0x65);
        expected[34] = (byte)(0x20);
        expected[35] = (byte)(0x6c);
        expected[36] = (byte)(0x61);
        expected[37] = (byte)(0x7a);
        expected[38] = (byte)(0x79);
        expected[39] = (byte)(0x20);
        expected[40] = (byte)(0x64);
        expected[41] = (byte)(0x6f);
        expected[42] = (byte)(0x67);
        expected[43] = (byte)(0x21);
        byte[] bytes = TIPMessageUtils.stringToBytes(text);
        assertEquals(expected.length, bytes.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], bytes[i]);
        }
    }

    @Test
    public void bytes_to_string_test() {
        String expected = "Carla & Martí!?";
        byte[] bytes = new byte[15];
        bytes[0] = (byte)(0x43);
        bytes[1] = (byte)(0x61);
        bytes[2] = (byte)(0x72);
        bytes[3] = (byte)(0x6c);
        bytes[4] = (byte)(0x61);
        bytes[5] = (byte)(0x20);
        bytes[6] = (byte)(0x26);
        bytes[7] = (byte)(0x20);
        bytes[8] = (byte)(0x4d);
        bytes[9] = (byte)(0x61);
        bytes[10] = (byte)(0x72);
        bytes[11] = (byte)(0x74);
        bytes[12] = (byte)(0xed);
        bytes[13] = (byte)(0x21);
        bytes[14] = (byte)(0x3f);
        String text  = TIPMessageUtils.bytesToString(bytes);
        assertEquals(expected, text);
    }

    @Test
    public void string_to_bytes_and_bytes_to_string_test() {
        String expected = "¿¡Bàrçâå Mãrtí ñÑü øßÆæðÞ";
        byte[] bytes = TIPMessageUtils.stringToBytes(expected);
        String result  = TIPMessageUtils.bytesToString(bytes);
        assertEquals(expected, result);
    }

    @Test
    public void bytes_to_hex_test(){
        String expected = "c2be283f2714ed1f502086b11b9ca278";
        byte[] bytes = new byte[16];
        bytes[0] = (byte)(0xc2);
        bytes[1] = (byte)(0xbe);
        bytes[2] = (byte)(0x28);
        bytes[3] = (byte)(0x3f);
        bytes[4] = (byte)(0x27);
        bytes[5] = (byte)(0x14);
        bytes[6] = (byte)(0xed);
        bytes[7] = (byte)(0x1f);
        bytes[8] = (byte)(0x50);
        bytes[9] = (byte)(0x20);
        bytes[10] = (byte)(0x86);
        bytes[11] = (byte)(0xb1);
        bytes[12] = (byte)(0x1b);
        bytes[13] = (byte)(0x9c);
        bytes[14] = (byte)(0xa2);
        bytes[15] = (byte)(0x78);
        String text  = TIPMessageUtils.bytesToHex(bytes);
        assertEquals(expected, text);
    }
}
