package darkninja2462.purplematter.common.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import darkninja2462.purplematter.util.PredicatesComparable;
import darkninja2462.purplematter.util.Suppliers;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RecipeHochStarUpgrade extends IForgeRegistryEntry.Impl<IRecipe> implements IEnabledRecipe {

    protected ResourceLocation group;
    protected Ingredient hoch;
    protected Ingredient klein;
    protected Predicate<Integer> kleinNumber;
    protected NonNullList<Ingredient> input;
    protected BooleanSupplier enabledSupplier;

    public RecipeHochStarUpgrade(ResourceLocation group, Ingredient hoch, Ingredient klein, Predicate<Integer> kleinNumber, NonNullList<Ingredient> input, BooleanSupplier enabledSupplier) {
        this.group = group;
        this.hoch = hoch;
        this.klein = klein;
        this.kleinNumber = kleinNumber;
        this.input = input;
        this.enabledSupplier = enabledSupplier;
    }

    public RecipeHochStarUpgrade(ResourceLocation group, Ingredient hoch, Ingredient klein, Predicate<Integer> kleinNumber, NonNullList<Ingredient> input) {
        this(group, hoch, klein, kleinNumber, input, Suppliers.constant(true));
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        if(!isEnabled()) return false;
        ItemStack hoch = null;
        List<ItemStack> klein = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if(!s.isEmpty()) {
                if(this.hoch.apply(s))
                    hoch = s;
                else if(this.klein.apply(s))
                    klein.add(s);
                else
                    items.add(s);
            }
        }
        if(hoch == null)
            return false;
        if(!kleinNumber.test(klein.size()))
            return false;
        return RecipeMatcher.findMatches(items, this.input) != null;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        if(!isEnabled()) return ItemStack.EMPTY;
        ItemStack result = null;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (!s.isEmpty()) {
                if (this.hoch.apply(s))
                    result = s.copy();
            }
        }
        if(result == null)
            return ItemStack.EMPTY;
        return RecipeHochStar.getCraftingResult(inv, result);
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public boolean isDynamic() {
        return true;
    }

    @Override
    @Nonnull
    public String getGroup() {
        return this.group == null ? "" : this.group.toString();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canFit(int width, int height) {
        return width * height >= this.input.size();
    }

    @Override
    public BooleanSupplier getEnabledSupplier() {
        return this.enabledSupplier;
    }

    public static class Factory implements IRecipeFactory, IEnabledRecipe.Factory {

        public static final String JSONKEY_GROUP = "group";
        public static final String JSONKEY_HOCH_STAR = "hoch_star";
        public static final String JSONKEY_KLEIN_STAR = "klein_star";
        public static final String JSONKEY_INGREDIENTS = "ingredients";
        public static final String JSONKEY_KLEIN_QUANTITY = "klein_star_quantity";
        public static final String JSONKEY_MATCH = "match";
        public static final String JSONKEY_QUANTITY = "quantity";

        @Override
        public RecipeHochStarUpgrade parse(JsonContext context, JsonObject json) {
            String groupStr = JsonUtils.getString(json, JSONKEY_GROUP, "");
            ResourceLocation group = groupStr.isEmpty() ? null : new ResourceLocation(groupStr);

            Ingredient hoch = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, JSONKEY_HOCH_STAR), context);
            Ingredient klein = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, JSONKEY_KLEIN_STAR), context);

            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (JsonElement element : JsonUtils.getJsonArray(json, JSONKEY_INGREDIENTS))
                ingredients.add(CraftingHelper.getIngredient(element, context));

            JsonObject quantityJson = JsonUtils.getJsonObject(json, JSONKEY_KLEIN_QUANTITY);
            //Match type (ex. equals, greater than, etc)
            String match = JsonUtils.getString(quantityJson, JSONKEY_MATCH);
            Function<Integer, Predicate<Integer>> predicateSupplier;
            try {
                predicateSupplier = PredicatesComparable.of(match);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Unknown match type: " + match, e);
            }

            //List of quantities (or single)
            List<Integer> quantities;
            if(JsonUtils.isJsonPrimitive(quantityJson, JSONKEY_QUANTITY)) {
                //Single
                quantities = Lists.newArrayList(JsonUtils.getInt(quantityJson, JSONKEY_QUANTITY));
            } else {
                //Array
                JsonArray ints = JsonUtils.getJsonArray(quantityJson, JSONKEY_QUANTITY);
                quantities = StreamSupport.stream(ints.spliterator(), false)
                        .map(JsonElement::getAsInt).collect(Collectors.toList());
            }
            if(quantities.isEmpty())
                throw new JsonParseException("Klein star quantity list cannot be empty");
            //Create a predicate that matches at least one tier in the ingredient
            Predicate<Integer> kleinNumber = quantities.stream().map(predicateSupplier).reduce((x -> false), Predicate::or);

            BooleanSupplier enabledSupplier = parseConditions(context, json);
            return (enabledSupplier != null)
                    ? new RecipeHochStarUpgrade(group, hoch, klein, kleinNumber, ingredients, enabledSupplier)
                    : new RecipeHochStarUpgrade(group, hoch, klein, kleinNumber, ingredients);
        }
    }
}
