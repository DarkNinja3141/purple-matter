package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.mod.SimpleModBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
public class HandlerBlocks {

    //A list of all Block classes that are annotated with @SimpleModBlock
    //Maps a block to its registry name
    private static Map<Block, String> simpleBlocks = new HashMap<>();

    static {
        //Create simple blocks
        Reflections reflections = new Reflections("darkninja2462.purplematter");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SimpleModBlock.class);
        for(Class<?> c : annotated) {
            String name = c.getAnnotation(SimpleModBlock.class).value();
            try {
                Block block = ((Block) c.newInstance()).setRegistryName(PurpleMatter.MODID, name).setUnlocalizedName(PurpleMatter.MODID + "." + name);
                simpleBlocks.put(block, name);
            } catch (InstantiationException | IllegalAccessException | ClassCastException e ) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        simpleBlocks.forEach((block, name) -> registerSimpleBlock(event.getRegistry(), block, name));
    }

    private static void registerSimpleBlock(IForgeRegistry<Block> r, Block block, String name) {
        r.register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        simpleBlocks.forEach((block, name) -> registerItemBlock(event.getRegistry(), block, name));
    }

    private static void registerItemBlock(IForgeRegistry<Item> r, Block block, String name) {
        r.register(new ItemBlock(block).setRegistryName(name));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        simpleBlocks.forEach((block, name) -> registerSimpleItemModel(Item.getItemFromBlock(block), name));
    }

    private static void registerSimpleItemModel(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}
