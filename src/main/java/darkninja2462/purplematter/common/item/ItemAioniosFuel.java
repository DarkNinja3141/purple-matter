package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.common.recipe.BurnTimes;
import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SimpleModItem("aionios_fuel")
public class ItemAioniosFuel extends Item {

    public ItemAioniosFuel() {
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return BurnTimes.AIONIOS_FUEL;
    }

}
