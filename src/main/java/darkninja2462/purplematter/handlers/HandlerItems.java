package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.item.ItemGrosstenCube;
import darkninja2462.purplematter.common.item.ItemHochStar;
import darkninja2462.purplematter.common.item.ItemPurpleMatter;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
public class HandlerItems {

    public static final class ItemNames {
        public static final String PURPLE_MATTER = "purple_matter";
        public static final String HOCH_STAR = "hoch_star";
        public static final String GROSSTEN_CUBE = "grossten_cube";
    }

    //A list of all Item classes that are annotated with @SimpleModItem
    //Maps a registry name to its item
    private static Map<String, Item> items = new HashMap<>();
    //Individual reference to each item if needed
    public static ItemPurpleMatter purple_matter;
    public static ItemHochStar hoch_star;

    public static void findItems(ASMDataTable data) {
        //Create simple items
        for(ASMDataTable.ASMData target : data.getAll(SimpleModItem.class.getName())) {
            try {
                Class<?> c = Class.forName(target.getClassName());
                String name = c.getAnnotation(SimpleModItem.class).value();
                Item item = ((Item) c.newInstance());
                item.setRegistryName(PurpleMatter.MODID, name).setUnlocalizedName(PurpleMatter.MODID + "." + name);
                items.put(name, item);
            } catch (InstantiationException | IllegalAccessException | ClassCastException | ClassNotFoundException e ) {
                PurpleMatter.LOGGER.error("Error loading item", e);
            }
        }
        purple_matter = (ItemPurpleMatter) items.get(ItemNames.PURPLE_MATTER);
        hoch_star = (ItemHochStar) items.get(ItemNames.HOCH_STAR);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(items.values().toArray(new Item[0]));
    }

    private static void registerSimpleItem(IForgeRegistry<Item> r, Item item) {
        r.register(item);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        items.values().forEach(HandlerItems::registerSimpleItemModel);
    }

    private static void registerSimpleItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}
