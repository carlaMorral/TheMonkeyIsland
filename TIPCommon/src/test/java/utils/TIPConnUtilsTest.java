package utils;

import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class TIPConnUtilsTest {

    @Test
    public void write_read_n_bytes_test() {
        File file = new File("testFile");
        byte[] bytes = new byte[8];
        bytes[0] = (byte)(0x56);
        bytes[1] = (byte)(0xbf);
        bytes[2] = (byte)(0x48);
        bytes[3] = (byte)(0x61);
        bytes[4] = (byte)(0x73);
        bytes[5] = (byte)(0x20);
        bytes[6] = (byte)(0x64);
        bytes[7] = (byte)(0x87);
        try {
            file.createNewFile();
            TIPConnUtils connUtils = new TIPConnUtils(new FileInputStream(file),
                    new FileOutputStream(file));
            connUtils.writeBytes(bytes);
            byte[] recBytes = connUtils.readBytes(8);
            assertEquals(bytes.length, recBytes.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], recBytes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void write_read_bytes_test() {
        File file = new File("testFile");
        byte[] bytes = new byte[7];
        bytes[0] = (byte)(0x56);
        bytes[1] = (byte)(0xbf);
        bytes[2] = (byte)(0x48);
        bytes[3] = (byte)(0x61);
        bytes[4] = (byte)(0x73);
        bytes[5] = (byte)(0x20);
        bytes[6] = (byte)(0x00);
        try {
            file.createNewFile();
            TIPConnUtils connUtils = new TIPConnUtils(new FileInputStream(file),
                    new FileOutputStream(file));
            connUtils.writeBytes(bytes);
            byte[] recBytes = connUtils.readBytes();
            assertEquals(bytes.length, recBytes.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], recBytes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void write_read_stream_variable_length_test() {
        File file = new File("testFile");
        byte[] bytes = new byte[51];
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
        bytes[32] = (byte)(0x6c);
        bytes[33] = (byte)(0x61);
        bytes[34] = (byte)(0x73);
        bytes[35] = (byte)(0x20);
        bytes[36] = (byte)(0x68);
        bytes[37] = (byte)(0x61);
        bytes[38] = (byte)(0x73);
        bytes[39] = (byte)(0x20);
        bytes[40] = (byte)(0x61);
        bytes[41] = (byte)(0x70);
        bytes[42] = (byte)(0x72);
        bytes[43] = (byte)(0x65);
        bytes[44] = (byte)(0x6e);
        bytes[45] = (byte)(0x64);
        bytes[46] = (byte)(0x69);
        bytes[47] = (byte)(0x64);
        bytes[48] = (byte)(0x6f);
        bytes[49] = (byte)(0x2e);
        bytes[50] = (byte)(0x00);
        try {
            file.createNewFile();
            TIPConnUtils connUtils = new TIPConnUtils(new FileInputStream(file),
                    new FileOutputStream(file));
            connUtils.writeBytes(bytes);
            byte[] recBytes = connUtils.readStream();
            assertEquals(bytes.length, recBytes.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], recBytes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void write_read_stream_variable_length_hello_test() {
        File file = new File("testFile");
        byte[] bytes = new byte[51];
        bytes[0] = (byte)(0x01);
        bytes[1] = (byte)(0x00);
        bytes[2] = (byte)(0x00);
        bytes[3] = (byte)(0x00);
        bytes[4] = (byte)(0x17);
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
        bytes[32] = (byte)(0x6c);
        bytes[33] = (byte)(0x61);
        bytes[34] = (byte)(0x73);
        bytes[35] = (byte)(0x20);
        bytes[36] = (byte)(0x68);
        bytes[37] = (byte)(0x61);
        bytes[38] = (byte)(0x73);
        bytes[39] = (byte)(0x20);
        bytes[40] = (byte)(0x61);
        bytes[41] = (byte)(0x70);
        bytes[42] = (byte)(0x72);
        bytes[43] = (byte)(0x65);
        bytes[44] = (byte)(0x6e);
        bytes[45] = (byte)(0x64);
        bytes[46] = (byte)(0x69);
        bytes[47] = (byte)(0x64);
        bytes[48] = (byte)(0x6f);
        bytes[49] = (byte)(0x2e);
        bytes[50] = (byte)(0x00);
        try {
            file.createNewFile();
            TIPConnUtils connUtils = new TIPConnUtils(new FileInputStream(file),
                    new FileOutputStream(file));
            connUtils.writeBytes(bytes);
            byte[] recBytes = connUtils.readStream();
            assertEquals(bytes.length, recBytes.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], recBytes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }

    @Test
    public void write_read_stream_32B_length_test() {
        File file = new File("testFile");
        byte[] bytes = new byte[33];
        bytes[0] = (byte)(0x02); // HASH OPCODE
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
        bytes[32] = (byte)(0x6c); // Does not finish with 0x00
        try {
            file.createNewFile();
            TIPConnUtils connUtils = new TIPConnUtils(new FileInputStream(file),
                    new FileOutputStream(file));
            connUtils.writeBytes(bytes);
            byte[] recBytes = connUtils.readStream();
            assertEquals(bytes.length, recBytes.length);
            for (int i = 0; i < bytes.length; i++) {
                assertEquals(bytes[i], recBytes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // If exception is caught the test must fail
            assertEquals(0, 1);
        }
    }
}
