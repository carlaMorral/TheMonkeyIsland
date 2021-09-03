package TIP;

import org.junit.Test;
import utils.TIPException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class TIPMessageTest {

    TIPMessageFactory factory = TIPMessageFactory.getInstance();

    /*
     * Tests of constructors of all different TIPMessages
     */

    @Test
    public void TIPHello_constructor_test(){
        String name = "Capitán Pescanova";
        int id = 123456789;
        Opcode opcode = Opcode.HELLO;
        TIPHello tipHello = new TIPHello(name, id);

        assertEquals(name, tipHello.getName());
        assertEquals(id, tipHello.getId());
        assertEquals(opcode, tipHello.getOpcode());
    }

    @Test
    public void TIPHash_constructor_bytes_test(){
        byte[] bytes = new byte[32];
        bytes[0] = (byte)(0x05);
        bytes[1] = (byte)(0x53);
        bytes[2] = (byte)(0xed);
        bytes[3] = (byte)(0x20);
        bytes[4] = (byte)(0x71);
        bytes[5] = (byte)(0x75);
        bytes[6] = (byte)(0x65);
        bytes[7] = (byte)(0x20);
        bytes[8] = (byte)(0x6c);
        bytes[9] = (byte)(0x61);
        bytes[10] = (byte)(0x73);
        bytes[11] = (byte)(0x20);
        bytes[12] = (byte)(0x68);
        bytes[13] = (byte)(0x61);
        bytes[14] = (byte)(0x79);
        bytes[15] = (byte)(0x2c);
        bytes[16] = (byte)(0x20);
        bytes[17] = (byte)(0x73);
        bytes[18] = (byte)(0xf3);
        bytes[19] = (byte)(0x6c);
        bytes[20] = (byte)(0x6f);
        bytes[21] = (byte)(0x20);
        bytes[22] = (byte)(0x71);
        bytes[23] = (byte)(0x75);
        bytes[24] = (byte)(0x65);
        bytes[25] = (byte)(0x20);
        bytes[26] = (byte)(0x6e);
        bytes[27] = (byte)(0x75);
        bytes[28] = (byte)(0x6e);
        bytes[29] = (byte)(0x63);
        bytes[30] = (byte)(0x61);
        bytes[31] = (byte)(0x20);
        Opcode opcode = Opcode.HASH;
        try {
            TIPHash tipHash = new TIPHash(bytes);
            assertEquals(opcode, tipHash.getOpcode());
            byte[] hash = tipHash.getHash();
            assertEquals(bytes.length, hash.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], hash[i]);
            }
        } catch (TIPException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPHash_constructor_bytes_assert_exception_test(){
        byte[] bytes = new byte[20];
        bytes[0] = (byte)(0x05);
        bytes[1] = (byte)(0x53);
        bytes[2] = (byte)(0xed);
        bytes[3] = (byte)(0x20);
        bytes[4] = (byte)(0x71);
        bytes[5] = (byte)(0x75);
        bytes[6] = (byte)(0x65);
        bytes[7] = (byte)(0x20);
        bytes[8] = (byte)(0x6c);
        bytes[9] = (byte)(0x61);
        bytes[10] = (byte)(0x73);
        bytes[11] = (byte)(0x20);
        bytes[12] = (byte)(0x68);
        bytes[13] = (byte)(0x61);
        bytes[14] = (byte)(0x79);
        bytes[15] = (byte)(0x2c);
        bytes[16] = (byte)(0x20);
        bytes[17] = (byte)(0x73);
        bytes[18] = (byte)(0xf3);
        bytes[19] = (byte)(0x6c);
        try {
            TIPHash tipHash = new TIPHash(bytes);
            assertEquals(0, 1);
        } catch (TIPException e) {
            assertEquals("Hash length is not 32 bytes.", e.getMessage());
        }
    }

    @Test
    public void TIPHash_constructor_string_test(){
        String secret = "9847";
        String hexHash = "6dc6da2a4d2f76400701a5c58c07d795098208a2b904f899ec76a024e55f8718";
        Opcode opcode = Opcode.HASH;
        try {
            TIPHash tipHash = new TIPHash(secret);
            assertEquals(opcode, tipHash.getOpcode());
            assertEquals("HASH " + hexHash, tipHash.toString());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPSecret_constructor_test(){
        String secret = "9847";
        Opcode opcode = Opcode.SECRET;
        try {
            TIPSecret tipSecret = new TIPSecret(secret);
            assertEquals(opcode, tipSecret.getOpcode());
            assertEquals("SECRET " + secret, tipSecret.toString());
        } catch (TIPException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPSecret_constructor_assert_exception_test(){
        String secret = "9847a";
        try {
            TIPSecret tipSecret = new TIPSecret(secret);
            assertEquals(0, 1);
        } catch (TIPException e) {
            assertEquals("Secret is not an integer.", e.getMessage());
        }
    }

    @Test
    public void TIPInsult_constructor_test(){
        String body = "¿Has dejado ya de usar pañales?";
        Opcode opcode = Opcode.INSULT;
        try {
            TIPInsult tipinsult = new TIPInsult(body);
            assertEquals("INSULT " + body, tipinsult.toString());
            assertEquals(opcode, tipinsult.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPInsult_constructor_assert_exception_test(){
        String body = "Invalid message";
        try {
            TIPInsult insult = new TIPInsult(body);
            assertEquals(0, 1);
        } catch (TIPException e) {
            assertEquals("Not a valid insult", e.getMessage());
        }
    }

    @Test
    public void TIPComeback_constructor_test(){
        String body = "¿Incluso antes de que huelan tu aliento?";
        Opcode opcode = Opcode.COMEBACK;
        try {
            TIPComeback tipComeback = new TIPComeback(body);
            assertEquals("COMEBACK " + body, tipComeback.toString());
            assertEquals(opcode, tipComeback.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPComeback_constructor_assert_exception_test(){
        String body = "Invalid message";
        try {
            TIPComeback comeback = new TIPComeback(body);
            assertEquals(0, 1);
        } catch (TIPException e) {
            assertEquals("Not a valid comeback", e.getMessage());
        }
    }

    @Test
    public void TIPShout_constructor_message_test(){
        String message = "¡Has ganado, Trump Öso. Eres tan bueno que podrias luchar contra" +
                " la Sword Master de la isla Mêlée!";
        Opcode opcode = Opcode.SHOUT;
        Shout shout = Shout.HGMELEE;
        try {
            TIPShout tipShout = new TIPShout(message);
            assertEquals("SHOUT " + message, tipShout.toString());
            assertEquals(opcode, tipShout.getOpcode());
            assertEquals(shout, tipShout.getShout());
            assertEquals(message, tipShout.getMessage());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void TIPShout_constructor_message_assert_exception_test(){
        String message = "¡Has perdido, Trump Öso. Ets tan bo que podria lluitar amb " +
                "la Sword Master de la illa Mêlée!";
        try {
            TIPShout tipShout = new TIPShout(message);
            assertEquals(0, 1);
        }catch (TIPException e){
            assertEquals("Shout not valid.", e.getMessage());
        }
    }

    @Test
    public void TIPShout_constructor_shout_name_test(){
        String name = "Trump Öso";
        Opcode opcode = Opcode.SHOUT;
        Shout shout = Shout.HEGANADO;
        TIPShout tipShout = new TIPShout(shout, name);
        assertEquals(opcode, tipShout.getOpcode());
        assertEquals("SHOUT ¡He ganado, " + name + "!", tipShout.toString());
    }

    @Test
    public void TIPError_constructor_test(){
        String body = "¡Código de operación inválido, marinero de agua dulce! ¡Hasta la vista!";
        Opcode opcode = Opcode.ERROR;
        TIPError tipError = new TIPError();
        assertEquals(body, tipError.error);
        assertEquals(opcode, tipError.getOpcode());
    }

    @Test
    public void TIPError_constructor_int_positive_test(){
        String body = "¡Código de operación inválido, marinero de agua dulce! ¡Hasta la vista!";
        Opcode opcode = Opcode.ERROR;
        TIPError tipError = new TIPError(4);
        assertEquals(body, tipError.error);
        assertEquals(opcode, tipError.getOpcode());
    }

    @Test
    public void TIPError_constructor_int_negative_test(){
        String body = "¡No eres tú, soy yo! ¡Hasta la vista!";
        Opcode opcode = Opcode.ERROR;
        TIPError tipError = new TIPError(-1);
        assertEquals(body, tipError.error);
        assertEquals(opcode, tipError.getOpcode());
    }

    /*
     * Tests of encoding of all different TIPMessages
     */

    @Test
    public void Encode_TIPHello_test() {
        String name = "Joan Petit Quan Balla";
        int id = 777;

        byte[] expected = new byte[27];
        expected[0] = (byte)(0x01);
        expected[1] = (byte)(0x00);
        expected[2] = (byte)(0x00);
        expected[3] = (byte)(0x03);
        expected[4] = (byte)(0x09);
        expected[5] = (byte)(0x4a);
        expected[6] = (byte)(0x6f);
        expected[7] = (byte)(0x61);
        expected[8] = (byte)(0x6e);
        expected[9] = (byte)(0x20);
        expected[10] = (byte)(0x50);
        expected[11] = (byte)(0x65);
        expected[12] = (byte)(0x74);
        expected[13] = (byte)(0x69);
        expected[14] = (byte)(0x74);
        expected[15] = (byte)(0x20);
        expected[16] = (byte)(0x51);
        expected[17] = (byte)(0x75);
        expected[18] = (byte)(0x61);
        expected[19] = (byte)(0x6e);
        expected[20] = (byte)(0x20);
        expected[21] = (byte)(0x42);
        expected[22] = (byte)(0x61);
        expected[23] = (byte)(0x6c);
        expected[24] = (byte)(0x6c);
        expected[25] = (byte)(0x61);
        expected[26] = (byte)(0x00);

        TIPHello tipHello = new TIPHello(name, id);
        byte[] bytes = tipHello.encoded();
        assertEquals(expected.length, bytes.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], bytes[i]);
        }
    }

    @Test
    public void Encode_TIPHash_test() {
        String secret = "9847";

        byte[] expected = new byte[33];
        expected[0] = (byte)(0x02);
        expected[1] = (byte)(0x6d);
        expected[2] = (byte)(0xc6);
        expected[3] = (byte)(0xda);
        expected[4] = (byte)(0x2a);
        expected[5] = (byte)(0x4d);
        expected[6] = (byte)(0x2f);
        expected[7] = (byte)(0x76);
        expected[8] = (byte)(0x40);
        expected[9] = (byte)(0x07);
        expected[10] = (byte)(0x01);
        expected[11] = (byte)(0xa5);
        expected[12] = (byte)(0xc5);
        expected[13] = (byte)(0x8c);
        expected[14] = (byte)(0x07);
        expected[15] = (byte)(0xd7);
        expected[16] = (byte)(0x95);
        expected[17] = (byte)(0x09);
        expected[18] = (byte)(0x82);
        expected[19] = (byte)(0x08);
        expected[20] = (byte)(0xa2);
        expected[21] = (byte)(0xb9);
        expected[22] = (byte)(0x04);
        expected[23] = (byte)(0xf8);
        expected[24] = (byte)(0x99);
        expected[25] = (byte)(0xec);
        expected[26] = (byte)(0x76);
        expected[27] = (byte)(0xa0);
        expected[28] = (byte)(0x24);
        expected[29] = (byte)(0xe5);
        expected[30] = (byte)(0x5f);
        expected[31] = (byte)(0x87);
        expected[32] = (byte)(0x18);

        try {
            TIPHash tipHash = new TIPHash(secret);
            byte[] bytes = tipHash.encoded();
            assertEquals(expected.length, bytes.length);
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], bytes[i]);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_TIPSecret_test() {
        String secret = "9847";

        byte[] expected = new byte[6];
        expected[0] = (byte)(0x03);
        expected[1] = (byte)(0x39);
        expected[2] = (byte)(0x38);
        expected[3] = (byte)(0x34);
        expected[4] = (byte)(0x37);
        expected[5] = (byte)(0x00);

        try {
            TIPSecret tipSecret = new TIPSecret(secret);
            byte[] bytes = tipSecret.encoded();
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], bytes[i]);
            }
        } catch (TIPException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }

    }

    @Test
    public void Encode_TIPInsult_test() {
        String body = "¿Has dejado ya de usar pañales?";

        byte[] expected = new byte[33];
        expected[0] = (byte)(0x04);
        expected[1] = (byte)(0xbf);
        expected[2] = (byte)(0x48);
        expected[3] = (byte)(0x61);
        expected[4] = (byte)(0x73);
        expected[5] = (byte)(0x20);
        expected[6] = (byte)(0x64);
        expected[7] = (byte)(0x65);
        expected[8] = (byte)(0x6a);
        expected[9] = (byte)(0x61);
        expected[10] = (byte)(0x64);
        expected[11] = (byte)(0x6f);
        expected[12] = (byte)(0x20);
        expected[13] = (byte)(0x79);
        expected[14] = (byte)(0x61);
        expected[15] = (byte)(0x20);
        expected[16] = (byte)(0x64);
        expected[17] = (byte)(0x65);
        expected[18] = (byte)(0x20);
        expected[19] = (byte)(0x75);
        expected[20] = (byte)(0x73);
        expected[21] = (byte)(0x61);
        expected[22] = (byte)(0x72);
        expected[23] = (byte)(0x20);
        expected[24] = (byte)(0x70);
        expected[25] = (byte)(0x61);
        expected[26] = (byte)(0xf1);
        expected[27] = (byte)(0x61);
        expected[28] = (byte)(0x6c);
        expected[29] = (byte)(0x65);
        expected[30] = (byte)(0x73);
        expected[31] = (byte)(0x3f);
        expected[32] = (byte)(0x00);

        try {
            TIPInsult tipInsult = new TIPInsult(body);
            byte[] bytes = tipInsult.encoded();
            assertEquals(expected.length, bytes.length);
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], bytes[i]);
            }
        } catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_TIPComeback_test() {
        String body = "¿TAN rápido corres?";

        byte[] expected = new byte[21];
        expected[0] = (byte)(0x05);
        expected[1] = (byte)(0xbf);
        expected[2] = (byte)(0x54);
        expected[3] = (byte)(0x41);
        expected[4] = (byte)(0x4e);
        expected[5] = (byte)(0x20);
        expected[6] = (byte)(0x72);
        expected[7] = (byte)(0xe1);
        expected[8] = (byte)(0x70);
        expected[9] = (byte)(0x69);
        expected[10] = (byte)(0x64);
        expected[11] = (byte)(0x6f);
        expected[12] = (byte)(0x20);
        expected[13] = (byte)(0x63);
        expected[14] = (byte)(0x6f);
        expected[15] = (byte)(0x72);
        expected[16] = (byte)(0x72);
        expected[17] = (byte)(0x65);
        expected[18] = (byte)(0x73);
        expected[19] = (byte)(0x3f);
        expected[20] = (byte)(0x00);

        try {
            TIPComeback tipComeback = new TIPComeback(body);
            byte[] bytes = tipComeback.encoded();
            assertEquals(expected.length, bytes.length);
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], bytes[i]);
            }
        } catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_TIPShout_test() {
        String name = "Trump Öso";
        Shout shout = Shout.HASGANADO;

        byte[] expected = new byte[25];
        expected[0] = (byte)(0x06);
        expected[1] = (byte)(0xa1);
        expected[2] = (byte)(0x48);
        expected[3] = (byte)(0x61);
        expected[4] = (byte)(0x73);
        expected[5] = (byte)(0x20);
        expected[6] = (byte)(0x67);
        expected[7] = (byte)(0x61);
        expected[8] = (byte)(0x6e);
        expected[9] = (byte)(0x61);
        expected[10] = (byte)(0x64);
        expected[11] = (byte)(0x6f);
        expected[12] = (byte)(0x2c);
        expected[13] = (byte)(0x20);
        expected[14] = (byte)(0x54);
        expected[15] = (byte)(0x72);
        expected[16] = (byte)(0x75);
        expected[17] = (byte)(0x6d);
        expected[18] = (byte)(0x70);
        expected[19] = (byte)(0x20);
        expected[20] = (byte)(0xd6);
        expected[21] = (byte)(0x73);
        expected[22] = (byte)(0x6f);
        expected[23] = (byte)(0x21);
        expected[24] = (byte)(0x00);

        TIPShout tipShout = new TIPShout(shout, name);
        byte[] bytes = tipShout.encoded();
        assertEquals(expected.length, bytes.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], bytes[i]);
        }
    }

    @Test
    public void Encode_TIPError_test() {
        byte[] expected = new byte[73];
        expected[0] = (byte)(0x07);
        expected[1] = (byte)(0xa1);
        expected[2] = (byte)(0x43);
        expected[3] = (byte)(0xf3);
        expected[4] = (byte)(0x64);
        expected[5] = (byte)(0x69);
        expected[6] = (byte)(0x67);
        expected[7] = (byte)(0x6f);
        expected[8] = (byte)(0x20);
        expected[9] = (byte)(0x64);
        expected[10] = (byte)(0x65);
        expected[11] = (byte)(0x20);
        expected[12] = (byte)(0x6f);
        expected[13] = (byte)(0x70);
        expected[14] = (byte)(0x65);
        expected[15] = (byte)(0x72);
        expected[16] = (byte)(0x61);
        expected[17] = (byte)(0x63);
        expected[18] = (byte)(0x69);
        expected[19] = (byte)(0xf3);
        expected[20] = (byte)(0x6e);
        expected[21] = (byte)(0x20);
        expected[22] = (byte)(0x69);
        expected[23] = (byte)(0x6e);
        expected[24] = (byte)(0x76);
        expected[25] = (byte)(0xe1);
        expected[26] = (byte)(0x6c);
        expected[27] = (byte)(0x69);
        expected[28] = (byte)(0x64);
        expected[29] = (byte)(0x6f);
        expected[30] = (byte)(0x2c);
        expected[31] = (byte)(0x20);
        expected[32] = (byte)(0x6d);
        expected[33] = (byte)(0x61);
        expected[34] = (byte)(0x72);
        expected[35] = (byte)(0x69);
        expected[36] = (byte)(0x6e);
        expected[37] = (byte)(0x65);
        expected[38] = (byte)(0x72);
        expected[39] = (byte)(0x6f);
        expected[40] = (byte)(0x20);
        expected[41] = (byte)(0x64);
        expected[42] = (byte)(0x65);
        expected[43] = (byte)(0x20);
        expected[44] = (byte)(0x61);
        expected[45] = (byte)(0x67);
        expected[46] = (byte)(0x75);
        expected[47] = (byte)(0x61);
        expected[48] = (byte)(0x20);
        expected[49] = (byte)(0x64);
        expected[50] = (byte)(0x75);
        expected[51] = (byte)(0x6c);
        expected[52] = (byte)(0x63);
        expected[53] = (byte)(0x65);
        expected[54] = (byte)(0x21);
        expected[55] = (byte)(0x20);
        expected[56] = (byte)(0xa1);
        expected[57] = (byte)(0x48);
        expected[58] = (byte)(0x61);
        expected[59] = (byte)(0x73);
        expected[60] = (byte)(0x74);
        expected[61] = (byte)(0x61);
        expected[62] = (byte)(0x20);
        expected[63] = (byte)(0x6c);
        expected[64] = (byte)(0x61);
        expected[65] = (byte)(0x20);
        expected[66] = (byte)(0x76);
        expected[67] = (byte)(0x69);
        expected[68] = (byte)(0x73);
        expected[69] = (byte)(0x74);
        expected[70] = (byte)(0x61);
        expected[71] = (byte)(0x21);
        expected[72] = (byte)(0x00);

        TIPError tipError = new TIPError();
        byte[] bytes = tipError.encoded();
        assertEquals(expected.length, bytes.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], bytes[i]);
        }
    }

    /*
     * Tests of encoding and decoding of all different TIPMessages
     */

    @Test
    public void Encode_decode_TIPHello_test(){
        String name = "Capitán Pescanova";
        int id = 123456789;
        Opcode opcode = Opcode.HELLO;
        TIPHello tipHello = new TIPHello(name, id);

        byte[] bytes = tipHello.encoded();
        try {
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPHello.class, newTIPMessage.getClass());
            assertEquals(name, ((TIPHello) newTIPMessage).getName());
            assertEquals(id, ((TIPHello) newTIPMessage).getId());
            assertEquals(opcode, newTIPMessage.getOpcode());
        } catch (TIPException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPHash_test(){
        String secret = "1899";
        String hash = "7c72066c66a912cdecd0cc67136a49d31796193a26486d6b494b131c0d24f058";
        Opcode opcode = Opcode.HASH;
        try {
            TIPHash tipHash = new TIPHash(secret);
            byte[] bytes = tipHash.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPHash.class, newTIPMessage.getClass());
            assertEquals(hash, ((TIPHash) newTIPMessage).getHashString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException | NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPSecret_test(){
        String secret = "1899";
        Opcode opcode = Opcode.SECRET;
        try {
            TIPSecret tipSecret = new TIPSecret(secret);
            byte[] bytes = tipSecret.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPSecret.class, newTIPMessage.getClass());
            assertEquals("SECRET " + secret, newTIPMessage.toString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPInsult_test(){
        String insult = "¡He oído que eres un soplón despreciable!";
        Opcode opcode = Opcode.INSULT;
        try {
            TIPInsult tipInsult = new TIPInsult(insult);
            byte[] bytes = tipInsult.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPInsult.class, newTIPMessage.getClass());
            assertEquals("INSULT " + insult, newTIPMessage.toString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPComeback_test(){
        String comeback = "Ah, ¿Ya has obtenido ese trabajo de barrendero?";
        Opcode opcode = Opcode.COMEBACK;
        try {
            TIPComeback tipComeback = new TIPComeback(comeback);
            byte[] bytes = tipComeback.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPComeback.class, newTIPMessage.getClass());
            assertEquals("COMEBACK " + comeback, newTIPMessage.toString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPShout_test(){
        String name = "Trump Öso";
        Shout shout = Shout.HEGANADO;
        Opcode opcode = Opcode.SHOUT;
        TIPShout tipShout = new TIPShout(shout, name);
        try {
            byte[] bytes = tipShout.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPShout.class, newTIPMessage.getClass());
            assertEquals("SHOUT ¡He ganado, " + name + "!", newTIPMessage.toString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Encode_decode_TIPError_test(){
        String body = "¡Código de operación inválido, marinero de agua dulce! ¡Hasta la vista!";
        Opcode opcode = Opcode.ERROR;
        TIPError tipError = new TIPError();
        try {
            byte[] bytes = tipError.encoded();
            TIPMessage newTIPMessage = factory.getTIPMessage(bytes);
            assertEquals(TIPError.class, newTIPMessage.getClass());
            assertEquals("ERROR " + body, newTIPMessage.toString());
            assertEquals(opcode, newTIPMessage.getOpcode());
        }catch (TIPException e){
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void Decode_invalid_opcode_assert_exception_test(){
        byte[] bytes = new byte[8];
        bytes[0] = (byte)(0x56);
        bytes[1] = (byte)(0xbf);
        bytes[2] = (byte)(0x48);
        bytes[3] = (byte)(0x61);
        bytes[4] = (byte)(0x73);
        bytes[5] = (byte)(0x20);
        bytes[6] = (byte)(0x64);
        bytes[7] = (byte)(0x00);
        try {
            TIPMessage tipMessage = factory.getTIPMessage(bytes);
            // If exception is not caught the test must fail
            assertEquals(0, 1);
        }catch (TIPException e){
            assertEquals("Invalid opcode.", e.getMessage());
        }
    }

    @Test
    public void Decode_invalid_non_zero_last_byte_assert_exception_test(){
        byte[] bytes = new byte[8];
        bytes[0] = (byte)(0x01);
        bytes[1] = (byte)(0xbf);
        bytes[2] = (byte)(0x48);
        bytes[3] = (byte)(0x61);
        bytes[4] = (byte)(0x73);
        bytes[5] = (byte)(0x20);
        bytes[6] = (byte)(0x64);
        bytes[7] = (byte)(0x84);
        try {
            TIPMessage tipMessage = factory.getTIPMessage(bytes);
            // If exception is not caught the test must fail
            assertEquals(0, 1);
        }catch (TIPException e){
            assertEquals("The last byte of the sequence is not the zero byte!",
                    e.getMessage());
        }
    }
}
