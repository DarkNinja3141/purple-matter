package darkninja2462.purplematter.common.recipe;

import darkninja2462.purplematter.common.item.ItemHochStar;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import static darkninja2462.purplematter.handlers.HandlerItems.hoch_star;
import static moze_intel.projecte.gameObjs.ObjHandler.kleinStars;

public class RecipeHochStar {
    /**
     * Crafting recipe for Hoch Star
     * Combine capacities and stored emc from klein and hoch stars into result item
     */
    public static ItemStack getCraftingResult(InventoryCrafting inv, ItemStack result) {
        long storedEMC = 0L;
        long capacity = 0L;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if(!s.isEmpty()) {
                if (s.getItem() == kleinStars) {
                    storedEMC += KleinStar.getEmc(s);
                    capacity += EMCHelper.getKleinStarMaxEmc(s);
                }
                //noinspection ConstantConditions
                if (s.getItem() == hoch_star) {
                    storedEMC += ItemPE.getEmc(s);
                    capacity += ItemHochStar.getExtraCapacity(s);
                }
            }
        }
        //noinspection ConstantConditions
        if(result.getItem() == hoch_star) {
            if(storedEMC != 0)
                ItemPE.setEmc(result, storedEMC);
            if(capacity != 0)
                ItemHochStar.setExtraCapacity(result, capacity);
        }
        return result;
    }
}
