package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

public class ShapelessRecipesItemEMC extends ShapelessRecipes {
    public ShapelessRecipesItemEMC(String group, ItemStack output, NonNullList<Ingredient> ingredients) {
        super(group, output, ingredients);
    }

    public ShapelessRecipesItemEMC(ShapelessRecipes recipe) {
        this(recipe.getGroup(), recipe.getRecipeOutput(), recipe.getIngredients());
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = super.getCraftingResult(inv);
        return RecipeItemEMC.getCraftingResult(inv, result);
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            json.addProperty("type", "minecraft:crafting_shapeless");
            return new ShapelessRecipesItemEMC((ShapelessRecipes)CraftingHelper.getRecipe(json, context));
        }
    }
}
