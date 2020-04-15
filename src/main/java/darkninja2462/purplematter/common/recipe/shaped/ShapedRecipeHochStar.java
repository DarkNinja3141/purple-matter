package darkninja2462.purplematter.common.recipe.shaped;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.RecipeHochStar;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapedRecipeHochStar extends ShapedRecipe {

    public ShapedRecipeHochStar(ShapedRecipe recipe, BooleanSupplier enabledSupplier) {
        super(recipe, enabledSupplier);
    }

    public ShapedRecipeHochStar(ShapedRecipe recipe) {
        super(recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        return RecipeHochStar.getCraftingResult(inv, result);
    }

    public static class Factory extends ShapedRecipe.Factory {
        @Override
        public ShapedRecipeHochStar parse(JsonContext context, JsonObject json) {
            return new ShapedRecipeHochStar(super.parse(context, json));
        }
    }

}
