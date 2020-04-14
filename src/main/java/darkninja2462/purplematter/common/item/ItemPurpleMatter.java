package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.handlers.HandlerItems;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.item.Item;

@SimpleModItem(HandlerItems.ItemNames.PURPLE_MATTER)
public class ItemPurpleMatter extends Item {

    public ItemPurpleMatter() {
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
    }
}
