package TIP;

import utils.TIPException;
import java.util.HashMap;
import java.util.Map;

public enum Opcode {
    HELLO(1),
    HASH(2),
    SECRET(3),
    INSULT(4),
    COMEBACK(5),
    SHOUT(6),
    ERROR(7);

    private final int code;
    private static final Map<Integer, Opcode> map = new HashMap<>();

    Opcode(int code) {
        this.code = code;
    }

    static {
        for (Opcode op : Opcode.values()) {
            map.put(op.code, op);
        }
    }

    /**
     * Returns an opcode matching the given integer.
     * @param code integer of the enum opcode.
     * @return Opcode mathing the given integer.
     * @throws TIPException if the given integer is not between the supported range.
     */
    public static Opcode codeOf(int code) throws TIPException {
        if (code > 7 || code < 1){
            throw new TIPException("Invalid opcode.");
        }
        return map.get(code);
    }

    public int getCode() {
        return code;
    }
}
