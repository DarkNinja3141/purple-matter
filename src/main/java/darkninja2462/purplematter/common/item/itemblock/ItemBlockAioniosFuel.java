package darkninja2462.purplematter.common.item.itemblock;

import darkninja2462.purplematter.common.recipes.BurnTimes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockAioniosFuel extends ItemBlock {

    public ItemBlockAioniosFuel(Block block) {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return BurnTimes.AIONIOS_FUEL_BLOCK;
    }
}
