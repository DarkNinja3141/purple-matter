package darkninja2462.purplematter;

import darkninja2462.purplematter.handlers.HandlerItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = PurpleMatter.MODID, name = PurpleMatter.NAME, version = PurpleMatter.VERSION, useMetadata = true)
public class PurpleMatter
{
    public static final String MODID = "purplematter";
    public static final String NAME = "Purple Matter";
    public static final String VERSION = "0.0.0";

    private static Logger logger;

    public PurpleMatter() {
        MinecraftForge.EVENT_BUS.register(HandlerItems.class);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

}
