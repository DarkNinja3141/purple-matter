package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandlerItems {
    public static Item purpleMatter = new Item().setRegistryName(PurpleMatter.MODID, "purple_matter").setUnlocalizedName("purplematter.purple_matter");

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(purpleMatter);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(purpleMatter, 0, new ModelResourceLocation(purpleMatter.getRegistryName(), "inventory"));
    }
}
