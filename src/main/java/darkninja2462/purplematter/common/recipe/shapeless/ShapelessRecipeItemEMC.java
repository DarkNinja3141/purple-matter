package darkninja2462.purplematter.common.recipe.shapeless;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.RecipeItemEMC;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapelessRecipeItemEMC extends ShapelessRecipe {

    public ShapelessRecipeItemEMC(ShapelessRecipe recipe, BooleanSupplier enabledSupplier) {
        super(recipe, enabledSupplier);
    }

    public ShapelessRecipeItemEMC(ShapelessRecipe recipe) {
        super(recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        return RecipeItemEMC.getCraftingResult(inv, result);
    }

    public static class Factory extends ShapelessRecipe.Factory {
        @Override
        public ShapelessRecipeItemEMC parse(JsonContext context, JsonObject json) {
            return new ShapelessRecipeItemEMC(super.parse(context, json));
        }
    }

}
