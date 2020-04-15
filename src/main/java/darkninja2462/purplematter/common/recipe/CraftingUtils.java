package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import darkninja2462.purplematter.common.recipe.condition.ConditionFactoryDynamic;
import darkninja2462.purplematter.util.Suppliers;
import darkninja2462.purplematter.util.reflect.ReflectionUtils;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class CraftingUtils {

    public static ResourceLocation getGroup(ShapedOreRecipe recipe) {
        return (ResourceLocation) ReflectionUtils.wrap(() -> ReflectionUtils.getPrivateField(recipe, "group")).get();
    }

    public static ShapedPrimer getPrimer(ShapedOreRecipe recipe) {
        ShapedPrimer primer = new ShapedPrimer();
        primer.width = recipe.getRecipeWidth();
        primer.height = recipe.getRecipeHeight();
        primer.mirrored = (boolean) ReflectionUtils.wrap(() -> ReflectionUtils.getPrivateField(recipe, "mirrored")).get();
        primer.input = recipe.getIngredients();
        return primer;
    }

    public static ResourceLocation getGroup(ShapelessOreRecipe recipe) {
        return (ResourceLocation) ReflectionUtils.wrap(() -> ReflectionUtils.getPrivateField(recipe, "group")).get();
    }

    public static String JSONKEY_CONDITIONS = "conditions";

    @Nullable
    public static BooleanSupplier processDynamicConditions(JsonContext context, JsonObject json) {
        return (json.has(CraftingUtils.JSONKEY_CONDITIONS)) ?
                CraftingUtils.processDynamicConditions(JsonUtils.getJsonArray(json, CraftingUtils.JSONKEY_CONDITIONS), context)
                : null;
    }

    @Nullable
    public static BooleanSupplier processDynamicConditions(JsonArray conditions, JsonContext context) {
        if(conditions.size() == 0) return null;
        List<BooleanSupplier> suppliers = new ArrayList<>();
        for (int x = 0; x < conditions.size(); x++)
        {
            if (!conditions.get(x).isJsonObject())
                throw new JsonSyntaxException("Conditions must be an array of JsonObjects");

            JsonObject json = conditions.get(x).getAsJsonObject();
            IConditionFactory conditionFactory = getConditionFactory(json, context);

            if(conditionFactory instanceof ConditionFactoryDynamic)
                suppliers.add(((ConditionFactoryDynamic)conditionFactory).parseDynamic(context, json));
        }
        return Suppliers.or(suppliers);
    }

    @Nonnull
    public static IConditionFactory getConditionFactory(JsonObject json, JsonContext context)
    {
        ResourceLocation type = new ResourceLocation(context.appendModId(JsonUtils.getString(json, "type")));
        @SuppressWarnings("unchecked")
        Map<ResourceLocation, IConditionFactory> conditions = (Map<ResourceLocation, IConditionFactory>) ReflectionUtils.wrap(() -> ReflectionUtils.getPrivateField(CraftingHelper.class, "conditions")).get();
        IConditionFactory factory = conditions.get(type);
        if (factory == null)
            throw new JsonSyntaxException("Unknown condition type: " + type.toString());
        return factory;
    }

}
