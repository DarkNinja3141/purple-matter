package darkninja2462.purplematter.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SimpleCreativeTab extends CreativeTabs {

    private ItemStack icon;

    public SimpleCreativeTab(String label) {
        super(label);
    }

    public SimpleCreativeTab(String label, ItemStack icon) {
        this(label);
        this.icon = icon;
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return icon;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }
}
