package darkninja2462.purplematter.common.recipe;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.item.ItemHochStar;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import static darkninja2462.purplematter.handlers.HandlerItems.hoch_star;
import static moze_intel.projecte.gameObjs.ObjHandler.kleinStars;

public class RecipeHochStar {

    public static String JSONKEY_CONSUME = "consume";

    /**
     * Crafting recipe for Hoch Star
     * Combine capacities and stored emc from klein and hoch stars into result item
     */
    public static ItemStack getCraftingResult(InventoryCrafting inv, ItemStack result) {
        long storedEmc = 0L;
        long capacity = 0L;
        long mass = 0L;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if(!s.isEmpty()) {
                if (s.getItem() == hoch_star) {
                    storedEmc += hoch_star.getStoredEmc(s);
                    capacity += ItemHochStar.getExtraCapacity(s);
                } else {
                    mass += EMCHelper.getEmcValue(s);
                }
                if (s.getItem() == kleinStars) {
                    capacity += EMCHelper.getKleinStarMaxEmc(s);
                }
            }
        }
        if(result.getItem() == hoch_star) {
            if(capacity != 0)
                ItemHochStar.setExtraCapacity(result, capacity);
            ItemHochStar.setStoredEmc(result, storedEmc + mass);
        }
        return result;
    }
}
