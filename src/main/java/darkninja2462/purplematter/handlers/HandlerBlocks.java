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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID)
public class HandlerBlocks {

    //A list of all Block classes that are annotated with @SimpleModBlock
    //Maps a Block to its ItemBlock
    private static Map<Block, ItemBlock> simpleBlocks = new HashMap<>();

    static {
        //Create simple blocks
        Reflections reflections = new Reflections("darkninja2462.purplematter");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SimpleModBlock.class);
        for(Class<?> c : annotated) {
            String name = c.getAnnotation(SimpleModBlock.class).value();
            Class<? extends ItemBlock> itemBlockClass = c.getAnnotation(SimpleModBlock.class).itemBlock();
            try {
                Block block = ((Block) c.newInstance());
                block.setRegistryName(PurpleMatter.MODID, name).setUnlocalizedName(PurpleMatter.MODID + "." + name);
                ItemBlock itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);
                itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
                simpleBlocks.put(block, itemBlock);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassCastException e ) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(simpleBlocks.keySet().toArray(new Block[0]));
    }

    private static void registerSimpleBlock(IForgeRegistry<Block> r, Block block) {
        r.register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(simpleBlocks.values().toArray(new ItemBlock[0]));
    }

    private static void registerItemBlock(IForgeRegistry<Item> r, Block block) {
        r.register(new ItemBlock(block));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        simpleBlocks.values().forEach(HandlerBlocks::registerSimpleItemModel);
    }

    private static void registerSimpleItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}
