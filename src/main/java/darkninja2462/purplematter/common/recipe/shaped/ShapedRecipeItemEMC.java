package darkninja2462.purplematter.common.recipe.shaped;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.RecipeItemEMC;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapedRecipeItemEMC extends ShapedRecipe {

    public ShapedRecipeItemEMC(ShapedRecipe recipe, BooleanSupplier enabledSupplier) {
        super(recipe, enabledSupplier);
    }

    public ShapedRecipeItemEMC(ShapedRecipe recipe) {
        super(recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        return RecipeItemEMC.getCraftingResult(inv, result);
    }

    public static class Factory extends ShapedRecipe.Factory {
        @Override
        public ShapedRecipeItemEMC parse(JsonContext context, JsonObject json) {
            return new ShapedRecipeItemEMC(super.parse(context, json));
        }
    }

}
