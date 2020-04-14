package darkninja2462.purplematter.config;

import darkninja2462.purplematter.PurpleMatter;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;

@Config(modid = PurpleMatter.MODID)
public final class PurpleMatterConfig {

    @Config.LangKey("config.purplematter.item")
    @Config.Comment("Options for various items")
    public static final Item item = new Item();
    public static final class Item {
        @Config.LangKey("config.purplematter.item.hochStarCapacity")
        @Config.Comment({
                "Hoch Star EMC capacity when first crafted",
                "Defaults to the Klein Star Ein capacity",
                "Default: 50000"
        })
        @Config.RangeInt(min = 0)
        public int hochStarCapacity = 50000;
    }

    @Config.LangKey("config.purplematter.crafting")
    @Config.Comment({
            "Crafting options",
            "Mainly for enabling/disabling recipes"
    })
    public static final Crafting crafting = new Crafting();
    public static final class Crafting {
        @Config.RequiresMcRestart
        @Config.LangKey("config.purplematter.crafting.grosstenCube")
        @Config.Comment({
                "Enable Grossten Cube crafting",
                "Default: true"
        })
        public boolean grosstenCube = true;

        public BooleanSupplier getBooleanSupplier(String key) throws NoSuchFieldException, IllegalArgumentException {
            Field f = this.getClass().getField(key);
            if(!boolean.class.isAssignableFrom(f.getType())) throw new IllegalArgumentException(key + " is not a boolean field");
            return () -> {
                try {
                    return (boolean)f.get(this);
                } catch (IllegalAccessException e) {
                    PurpleMatter.LOGGER.error("Error in crafting condition", e);
                }
                return false;
            };
        }
    }

    @Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
    private static class Handler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(PurpleMatter.MODID)) {
                ConfigManager.sync(PurpleMatter.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
