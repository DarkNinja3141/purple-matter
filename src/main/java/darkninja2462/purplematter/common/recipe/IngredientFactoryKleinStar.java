package darkninja2462.purplematter.common.recipe;

import com.google.common.collect.Lists;
import com.google.gson.*;
import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.util.EnumKleinTier;
import darkninja2462.purplematter.util.PredicatesComparable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static moze_intel.projecte.gameObjs.ObjHandler.kleinStars;

public class IngredientFactoryKleinStar implements IIngredientFactory {

    private static final String JSONKEY_MATCHES = "match";
    private static final String JSONKEY_TIER = "tier";

    private static final Map<String, Function<EnumKleinTier, Predicate<EnumKleinTier>>> matchTypes = new HashMap<>();
    static {
        matchTypes.put("=", PredicatesComparable::isEqual);
        matchTypes.put("!=", PredicatesComparable::isNotEqual);
        matchTypes.put(">", PredicatesComparable::isGreaterThan);
        matchTypes.put(">=", PredicatesComparable::isGreaterThanOrEqualTo);
        matchTypes.put("<", PredicatesComparable::isLessThan);
        matchTypes.put("<=", PredicatesComparable::isLessThanOrEqualTo);
    }

    /**
     * Converts a JSON int or string to an EnumKleinTier, using the tier's damage (for ints) or name (for strings)
     */
    private static EnumKleinTier toKleinTier(JsonElement element) {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if(primitive.isNumber()) return EnumKleinTier.of(primitive.getAsInt());
        if(primitive.isString()) return EnumKleinTier.valueOf(primitive.getAsString());
        return null;
    }

    @Nonnull
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {
        //Match type (ex. klein stars equal to a certain tier or perhaps less than a certain tier)
        String match = JsonUtils.getString(json, JSONKEY_MATCHES);
        Function<EnumKleinTier, Predicate<EnumKleinTier>> predicateSupplier = matchTypes.getOrDefault(match, null);
        if(predicateSupplier == null) throw new JsonParseException("Invalid match type");

        //List of tiers (or single tier)
        List<EnumKleinTier> tiers;
        if(JsonUtils.isJsonPrimitive(json, JSONKEY_TIER)) {
            //Single tier
            tiers = Lists.newArrayList(IngredientFactoryKleinStar.toKleinTier(json.get(JSONKEY_TIER)));
        } else {
            //Array of tiers
            JsonArray damages = JsonUtils.getJsonArray(json, JSONKEY_TIER);
            tiers = StreamSupport.stream(damages.spliterator(), false)
                    .map(IngredientFactoryKleinStar::toKleinTier).collect(Collectors.toList());
        }
        //null check!
        if(tiers.stream().anyMatch(Objects::isNull)) throw new JsonParseException("Invalid klein star tier");
        if(tiers.isEmpty())
            throw new JsonParseException("Klein star tiers list cannot be empty");

        //Create a predicate that matches at least one tier in the ingredient
        Predicate<EnumKleinTier> predicate = tiers.stream().map(predicateSupplier).reduce((x -> false), Predicate::or);

        //Create a list of item stacks using the valid klein star tiers
        Stream<EnumKleinTier> validStars = Arrays.stream(EnumKleinTier.values()).filter(predicate);
        ItemStack[] stacks = validStars.map((t) -> new ItemStack(kleinStars, 1, t.damage)).toArray(ItemStack[]::new);

        return Ingredient.fromStacks(stacks);
    }

}
