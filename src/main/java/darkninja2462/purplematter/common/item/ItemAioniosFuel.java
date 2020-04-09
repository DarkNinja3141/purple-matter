package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.common.recipes.BurnTimes;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SimpleModItem("aionios_fuel")
public class ItemAioniosFuel extends Item {

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return BurnTimes.AIONIOS_FUEL;
    }

}
