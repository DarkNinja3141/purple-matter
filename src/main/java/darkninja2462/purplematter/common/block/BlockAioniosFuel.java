package darkninja2462.purplematter.common.block;

import darkninja2462.purplematter.common.item.itemblock.ItemBlockAioniosFuel;
import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.mod.SimpleModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SimpleModBlock(value = "aionios_fuel_block", itemBlock = ItemBlockAioniosFuel.class)
public class BlockAioniosFuel extends Block {

    public BlockAioniosFuel() {
        super(Material.IRON);
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
    }
}
