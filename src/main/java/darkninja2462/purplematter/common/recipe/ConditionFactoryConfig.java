package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import darkninja2462.purplematter.config.PurpleMatterConfig;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class ConditionFactoryConfig implements IConditionFactory {

    private static final String JSONKEY_KEY = "key";

    /**
     * Enable/disable a recipe based on a config option in the "crafting" subgroup
     */
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String key = JsonUtils.getString(json, JSONKEY_KEY);
        BooleanSupplier supplier;
        try {
            supplier = PurpleMatterConfig.crafting.getBooleanSupplier(key);
        } catch (NoSuchFieldException | IllegalArgumentException e) {
            throw new JsonParseException(e);
        }
        return supplier;
    }
}
