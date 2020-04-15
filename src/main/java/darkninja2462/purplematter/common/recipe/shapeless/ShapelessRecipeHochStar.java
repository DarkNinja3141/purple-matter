package darkninja2462.purplematter.common.recipe.shapeless;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.RecipeHochStar;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapelessRecipeHochStar extends ShapelessRecipeItemEMC {

    public ShapelessRecipeHochStar(ShapelessRecipeItemEMC recipe, BooleanSupplier enabledSupplier) {
        super(recipe, enabledSupplier);
    }

    public ShapelessRecipeHochStar(ShapelessRecipeItemEMC recipe) {
        super(recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        return RecipeHochStar.getCraftingResult(inv, result);
    }

    public static class Factory extends ShapelessRecipeItemEMC.Factory {
        @Override
        public ShapelessRecipeHochStar parse(JsonContext context, JsonObject json) {
            return new ShapelessRecipeHochStar(super.parse(context, json));
        }
    }

}
