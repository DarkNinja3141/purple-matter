package darkninja2462.purplematter.common.recipe.shaped;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.common.recipe.IDynamicRecipe;
import darkninja2462.purplematter.common.recipe.CraftingUtils;
import darkninja2462.purplematter.util.Suppliers;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class ShapedRecipe extends ShapedOreRecipe implements IDynamicRecipe {

    protected BooleanSupplier enabledSupplier;

    public ShapedRecipe(ResourceLocation group, @Nonnull ItemStack result, ShapedPrimer primer, BooleanSupplier enabledSupplier) {
        super(group, result, primer);
        this.enabledSupplier = enabledSupplier;
    }

    public ShapedRecipe(ResourceLocation group, @Nonnull ItemStack result, ShapedPrimer primer) {
        this(group, result, primer, Suppliers.constant(true));
    }

    public ShapedRecipe(ShapedOreRecipe recipe, BooleanSupplier enabledSupplier) {
        this(CraftingUtils.getGroup(recipe), recipe.getRecipeOutput(), CraftingUtils.getPrimer(recipe), enabledSupplier);
    }

    public ShapedRecipe(ShapedOreRecipe recipe) {
        this(CraftingUtils.getGroup(recipe), recipe.getRecipeOutput(), CraftingUtils.getPrimer(recipe));
    }

    public ShapedRecipe(ShapedRecipe recipe) {
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

    public static class Factory implements IRecipeFactory, IDynamicRecipe.Factory {
        @Override
        public ShapedRecipe parse(JsonContext context, JsonObject json) {
            ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
            BooleanSupplier enabledSupplier = parseConditions(context, json);
            return (enabledSupplier != null) ? new ShapedRecipe(recipe, enabledSupplier) : new ShapedRecipe(recipe);
        }
    }

}
