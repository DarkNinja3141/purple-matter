package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.mod.SimpleModItem;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.items.ItemPE;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@SimpleModItem("grossten_cube")
public class ItemGrosstenCube extends Item implements IItemEmc {

    public ItemGrosstenCube() {
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
        this.setMaxStackSize(1);
    }

    @Override
    public long addEmc(@Nonnull ItemStack stack, long toAdd) {
        ItemPE.addEmcToStack(stack, toAdd);
        return toAdd;
    }

    @Override
    public long extractEmc(@Nonnull ItemStack stack, long toRemove) {
        long sub = Math.min(getStoredEmc(stack), toRemove);
        ItemPE.removeEmc(stack, sub);
        return sub;
    }

    @Override
    public long getStoredEmc(@Nonnull ItemStack stack) {
        return ItemPE.getEmc(stack);
    }

    @Override
    public long getMaximumEmc(@Nonnull ItemStack stack) {
        return Long.MAX_VALUE;
    }
}
