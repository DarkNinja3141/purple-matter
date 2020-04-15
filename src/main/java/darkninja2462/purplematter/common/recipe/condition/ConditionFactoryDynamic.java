package darkninja2462.purplematter.common.recipe.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import darkninja2462.purplematter.util.Suppliers;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class ConditionFactoryDynamic implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        return Suppliers.constant(true);
    }

    public static String JSONKEY_CONDITION = "condition";

    public BooleanSupplier parseDynamic(JsonContext context, JsonObject json) {
        if(!json.has(JSONKEY_CONDITION))
            throw new JsonParseException("Condition required");
        return CraftingHelper.getCondition(json.getAsJsonObject(JSONKEY_CONDITION), context);
    }
}
