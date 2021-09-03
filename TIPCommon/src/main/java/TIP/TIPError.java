package TIP;

import utils.TIPMessageUtils;

public class TIPError extends TIPMessage {

    String error;

    public TIPError() {
        super(Opcode.ERROR);
        error = "¡Código de operación inválido, marinero de agua dulce! ¡Hasta la vista!";
    }

    public TIPError(int i) {
        super(Opcode.ERROR);
        if (i >= 0) {
            error = "¡Código de operación inválido, marinero de agua dulce! ¡Hasta la vista!";
        } else {
            error = "¡No eres tú, soy yo! ¡Hasta la vista!";
        }
    }

    @Override
    public byte[] encoded() {
        int lenBody = error.length();
        byte[] messBytes = new byte[lenBody + 2];
        messBytes[0] = (byte) this.opcode.getCode();
        byte [] bodyBytes = TIPMessageUtils.stringToBytes(error);
        System.arraycopy(bodyBytes, 0, messBytes, 1, bodyBytes.length);
        messBytes[messBytes.length-1] = (byte) 0x00;
        return messBytes;
    }

    @Override
    public String toString() {
        return "ERROR " + this.error;
    }

}
