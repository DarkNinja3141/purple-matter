package darkninja2462.purplematter.mod;

import net.minecraft.item.ItemBlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the class to be registered as a block
 * Annotation must be applied to a subclass of Block
 * Class must have a zero-argument constructor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SimpleModBlock {
    /** The item's registry name */
    String value();
    /** The ItemBlock class for this block */
    Class<? extends ItemBlock> itemBlock() default ItemBlock.class;
}
