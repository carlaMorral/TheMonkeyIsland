package TIP;

import utils.TIPException;

public enum Shout {
    HEGANADO,
    HASGANADO,
    HGMELEE;

    public String getShoutMessage(String name) {
        if (this == HEGANADO) {
            return "¡He ganado, " + name + "!";
        } else if (this == HASGANADO) {
            return "¡Has ganado, " + name + "!";
        } else {
            return "¡Has ganado, " + name +
                    ". Eres tan bueno que podrias luchar contra la Sword Master de la isla Mêlée!";
        }
    }

    public static Shout getShout(String message) throws TIPException {
        if (message.startsWith("¡He ganado, ") && message.endsWith("!")) {
            return Shout.HEGANADO;
        } else if (message.startsWith("¡Has ganado, ") && message.endsWith(". Eres tan bueno que " +
                "podrias luchar contra la Sword Master de la isla Mêlée!")) {
            return Shout.HGMELEE;
        } else if (message.startsWith("¡Has ganado, ") && message.endsWith("!")) {
            return Shout.HASGANADO;
        } else {
            throw new TIPException("Shout not valid.");
        }
    }
}
