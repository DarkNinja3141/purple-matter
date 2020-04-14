package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.tab.SimpleCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
public class HandlerCreativeTabs {

    public static SimpleCreativeTab PURPLE_MATTER = new SimpleCreativeTab("purple_matter");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemsRegistered(RegistryEvent.Register<Item> event) {
        PURPLE_MATTER.setIcon(new ItemStack(HandlerItems.purple_matter));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlocksRegistered(RegistryEvent.Register<Block> event) {

    }

}
