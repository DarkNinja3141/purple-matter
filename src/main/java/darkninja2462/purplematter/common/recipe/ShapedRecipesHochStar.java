package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.item.ItemHochStar;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.gameObjs.items.KleinStar.EnumKleinTier;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static darkninja2462.purplematter.handlers.HandlerItems.hoch_star;
import static moze_intel.projecte.gameObjs.ObjHandler.kleinStars;

public class ShapedRecipesHochStar extends ShapedRecipes {
    public ShapedRecipesHochStar(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(group, width, height, ingredients, result);
    }

    public ShapedRecipesHochStar(ShapedRecipes recipe) {
        this(recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getRecipeOutput());
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        long storedEMC = 0L;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if(!s.isEmpty() && s.getItem() == kleinStars) {
                storedEMC += KleinStar.getEmc(s);
            }
        }
        //noinspection ConstantConditions
        if (storedEMC != 0 && result.getItem() == hoch_star) {
            ItemPE.setEmc(result, storedEMC);
        }
        return result;
    }

    public static class Factory implements IRecipeFactory {

        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            return new ShapedRecipesHochStar(ShapedRecipes.deserialize(json));
        }
    }
}
