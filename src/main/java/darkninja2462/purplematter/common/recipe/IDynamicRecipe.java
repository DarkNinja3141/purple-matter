package darkninja2462.purplematter.common.recipe;

import com.google.gson.JsonObject;
import darkninja2462.purplematter.util.Suppliers;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public interface IDynamicRecipe extends IRecipe {

    default boolean isEnabled() {
        return getEnabledSupplier().getAsBoolean();
    }

    /**
     * Implementations should override this method
     */
    default BooleanSupplier getEnabledSupplier() {
        return Suppliers.constant(true);
    }

    interface Factory {
        /**
         * Returns a BooleanSupplier that can be called on to evaluate the state of the recipe's dynamic conditions
         */
        default BooleanSupplier parseConditions(JsonContext context, JsonObject json) {
            return CraftingUtils.processDynamicConditions(context, json);
        }
    }

}
