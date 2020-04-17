package darkninja2462.purplematter.common.recipe.shapeless;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.IEnabledRecipe;
import darkninja2462.purplematter.common.recipe.CraftingUtils;
import darkninja2462.purplematter.util.Suppliers;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapelessRecipe extends ShapelessOreRecipe implements IEnabledRecipe {

    private BooleanSupplier enabledSupplier;

    public ShapelessRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result, BooleanSupplier enabledSupplier) {
        super(group, input, result);
        this.enabledSupplier = enabledSupplier;
    }

    public ShapelessRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result) {
        this(group, input, result, Suppliers.constant(true));
    }

    public ShapelessRecipe(ShapelessOreRecipe recipe, BooleanSupplier enabledSupplier) {
        this(CraftingUtils.getGroup(recipe), recipe.getIngredients(), recipe.getRecipeOutput(), enabledSupplier);
    }

    public ShapelessRecipe(ShapelessOreRecipe recipe) {
        this(CraftingUtils.getGroup(recipe), recipe.getIngredients(), recipe.getRecipeOutput());
    }

    public ShapelessRecipe(ShapelessRecipe recipe) {
        this(recipe, recipe.enabledSupplier);
    }

    @Override
    public BooleanSupplier getEnabledSupplier() {
        return enabledSupplier;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
        return isEnabled() && super.matches(inv, world);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
        return isEnabled() ? super.getCraftingResult(var1) : ItemStack.EMPTY;
    }

    public static class Factory implements IRecipeFactory, IEnabledRecipe.Factory {
        @Override
        public ShapelessRecipe parse(JsonContext context, JsonObject json) {
            ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
            BooleanSupplier enabledSupplier = parseConditions(context, json);
            return (enabledSupplier != null) ? new ShapelessRecipe(recipe, enabledSupplier) : new ShapelessRecipe(recipe);
        }
    }

}
