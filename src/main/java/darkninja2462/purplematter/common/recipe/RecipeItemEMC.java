package darkninja2462.purplematter.common.recipe;

import moze_intel.projecte.api.item.IItemEmc;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class RecipeItemEMC {
    /**
     * Combine stored emc from ingredients into result item
     */
    public static ItemStack getCraftingResult(InventoryCrafting inv, ItemStack result) {
        long storedEMC = 0L;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if(!s.isEmpty()) {
                if (s.getItem() instanceof IItemEmc) {
                    storedEMC += ((IItemEmc)s.getItem()).getStoredEmc(s);
                }
            }
        }
        if(result.getItem() instanceof IItemEmc) {
            if(storedEMC != 0)
                ((IItemEmc)result.getItem()).addEmc(result, storedEMC);
        }
        return result;
    }
}
