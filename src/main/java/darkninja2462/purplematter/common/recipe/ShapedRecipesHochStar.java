package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonObject;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

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
        return RecipeHochStar.getCraftingResult(inv, result);
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            json.addProperty("type", "minecraft:crafting_shaped");
            return new ShapedRecipesHochStar((ShapedRecipes)CraftingHelper.getRecipe(json, context));
        }
    }
}
