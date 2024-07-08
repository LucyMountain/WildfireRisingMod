package net.shade.wfrising.effects;

public enum WildfireRisingEffectFlags {
    BLOOD_DEBT(0),
    BOND_OF_LIFE(1);

    private final int offset;

    WildfireRisingEffectFlags(int bit) {
        this.offset = 1 << bit;
    }

    public int getOffset() {
        return offset;
    }
}
