package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.item.ItemGrosstenCube;
import darkninja2462.purplematter.common.item.ItemHochStar;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.reflections.Reflections;

import java.util.*;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
@GameRegistry.ObjectHolder(PurpleMatter.MODID)
public class HandlerItems {

    public static final class ItemNames {
        public static final String HOCH_STAR = "hoch_star";
        public static final String GROSSTEN_CUBE = "grossten_cube";
    }

    //A list of all Item classes that are annotated with @SimpleModItem
    //Maps a registry name to its item
    private static Map<String, Item> items = new HashMap<>();
    static {
        //Create simple items
        Reflections reflections = new Reflections("darkninja2462.purplematter");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SimpleModItem.class);
        for(Class<?> c : annotated) {
            String name = c.getAnnotation(SimpleModItem.class).value();
            try {
                Item item = ((Item) c.newInstance());
                item.setRegistryName(PurpleMatter.MODID, name).setUnlocalizedName(PurpleMatter.MODID + "." + name);
                items.put(name, item);
            } catch (InstantiationException | IllegalAccessException | ClassCastException e ) {
                e.printStackTrace();
            }
        }
    }

    //Individual reference to each item if needed
    public static final ItemHochStar hoch_star;
    static {
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
