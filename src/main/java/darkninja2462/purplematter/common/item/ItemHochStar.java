package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.mod.SimpleModItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SimpleModItem("hoch_star")
public class ItemHochStar extends Item {

    public ItemHochStar() {
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation(PurpleMatter.MODID, "tier"), this::getPropertyTier);
    }

    public enum EnumHochTier {
        EIN("ein", 1),
        ZWEI("zwei", 2),
        DREI("drei", 3),
        VIER("vier", 4),
        SPHERE("sphere", 5),
        OMEGA("omega", 6);

        public final String name;
        public final int tier;
        EnumHochTier(String name, int tier) {
            this.name = name;
            this.tier = tier;
        }
    }

    public EnumHochTier getTier(@Nonnull ItemStack stack) {
        return EnumHochTier.EIN;
    }

    @SideOnly(Side.CLIENT)
    public float getPropertyTier(@Nonnull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
        return getTier(stack).tier;
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + getTier(stack).tier;
    }
}
