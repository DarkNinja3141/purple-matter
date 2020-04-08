package darkninja2462.purplematter.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the class to be registered as an item
 * Annotation must be applied to a subclass of Item
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SimpleModItem {
    /** The item's registry name */
    String value();
}
