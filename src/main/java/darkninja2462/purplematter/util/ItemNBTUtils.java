package darkninja2462.purplematter.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ItemNBTUtils {

    public static NBTTagCompound getOrCreateCompound(@Nonnull ItemStack stack) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }

    public static String makeKey(String namespace, String key) {
        return namespace + ":" + key;
    }

    /**
     * Method reference that performs a function with return type void on an object of type O with two parameters of types T1 and T2
     * @param <O>
     * @param <T1>
     * @param <T2>
     */
    private interface InstanceBiConsumer<O, T1, T2> {
        void apply(O obj, T1 t1, T2 t2);
    }

    /**
     * Stores the given map object into the give nbt compound with the given string key.
     * Helper function for the other set(*)Map methods
     */
    private static <T> void setMap(NBTTagCompound nbt, String key, Map<String, T> map, InstanceBiConsumer<NBTTagCompound, String, T> setter) {
        NBTTagCompound value = new NBTTagCompound();
        map.forEach((k, v) -> setter.apply(value, k, v));
        nbt.setTag(key, value);
    }

    /**
     * Retrieves a map object using the specified key
     * Helper function for the other get(*)Map methods
     */
    private static <T> Map<String, T> getMap(NBTTagCompound nbt, String key, BiFunction<NBTTagCompound, String, T> getter) {
        NBTTagCompound value = nbt.getCompoundTag(key);
        Map<String, T> map = new HashMap<>();
        value.getKeySet().forEach((k) -> map.put(k, getter.apply(value, k)));
        return map;
    }

    /**
     * Stores the Map&#60;String, NBTTagCompound&#62; object into the give nbt compound with the given string key.
     */
    public static void setCompoundMap(NBTTagCompound nbt, String key, Map<String, NBTTagCompound> value) {
        setMap(nbt, key, value, NBTTagCompound::setTag);
    }

    /**
     * Retrieves a Map&#60;String, NBTTagCompound&#62; object using the specified key
     */
    public static Map<String, NBTTagCompound> getCompoundMap(NBTTagCompound nbt, String key) {
        return getMap(nbt, key, NBTTagCompound::getCompoundTag);
    }

    /**
     * Stores the given Map&#60;String, Integer&#62; object into the give nbt compound with the given string key.
     */
    public static void setIntegerMap(NBTTagCompound nbt, String key, Map<String, Integer> value) {
        setMap(nbt, key, value, NBTTagCompound::setInteger);
    }

    /**
     * Retrieves a Map&#60;String, NBTTagCompound&#62; object using the specified key
     */
    public static Map<String, Integer> getIntegerMap(NBTTagCompound nbt, String key) {
        return getMap(nbt, key, NBTTagCompound::getInteger);
    }

}
