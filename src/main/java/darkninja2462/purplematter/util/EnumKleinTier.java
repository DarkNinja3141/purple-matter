package darkninja2462.purplematter.util;

import moze_intel.projecte.utils.Constants;

import java.util.Arrays;
import java.util.function.Predicate;

public enum EnumKleinTier {
    EIN(0, Constants.MAX_KLEIN_EMC[0]),
    ZWEI(1, Constants.MAX_KLEIN_EMC[1]),
    DREI(2, Constants.MAX_KLEIN_EMC[2]),
    VIER(3, Constants.MAX_KLEIN_EMC[3]),
    SPHERE(4, Constants.MAX_KLEIN_EMC[4]),
    OMEGA(5, Constants.MAX_KLEIN_EMC[5]);

    public final int damage;
    public final long capacity;
    EnumKleinTier(int damage, long capacity) {
        this.damage = damage;
        this.capacity = capacity;
    }

    public int getDamage() {
        return damage;
    }

    public long getCapacity() {
        return capacity;
    }

    public static EnumKleinTier of(int damage) {
        return Arrays.stream(EnumKleinTier.values()).filter(t -> t.damage == damage).findAny().orElse(null);
    }

}
