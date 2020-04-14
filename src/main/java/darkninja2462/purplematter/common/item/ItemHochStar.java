package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.config.PurpleMatterConfig;
import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.mod.SimpleModItem;
import darkninja2462.purplematter.util.ItemNBTUtils;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.utils.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@SimpleModItem("hoch_star")
public class ItemHochStar extends Item implements IItemEmc {

    public static final String nbtExtraCapacity = ItemNBTUtils.makeKey(PurpleMatter.MODID, "extra_capacity");

    public ItemHochStar() {
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation(PurpleMatter.MODID, "tier"), this::getPropertyTier);
    }

    public enum EnumHochTier {
        EIN("ein", 1, Constants.MAX_KLEIN_EMC[0]),
        ZWEI("zwei", 2, Constants.MAX_KLEIN_EMC[1]),
        DREI("drei", 3, Constants.MAX_KLEIN_EMC[2]),
        VIER("vier", 4, Constants.MAX_KLEIN_EMC[3]),
        SPHERE("sphere", 5, Constants.MAX_KLEIN_EMC[4]),
        OMEGA("omega", 6, Constants.MAX_KLEIN_EMC[5]);

        public final String name;
        public final int tier;
        public final long minCapacity;
        EnumHochTier(String name, int tier, long capacity) {
            this.name = name;
            this.tier = tier;
            this.minCapacity = capacity;
        }
    }

    public static void setExtraCapacity(ItemStack stack, long extraCapacity) {
        NBTTagCompound nbt = ItemNBTUtils.getOrCreateCompound(stack);
        nbt.setLong(nbtExtraCapacity, extraCapacity);
    }

    public static long getExtraCapacity(ItemStack stack) {
        if(ItemNBTUtils.hasKey(stack, nbtExtraCapacity))
            return Objects.requireNonNull(stack.getTagCompound()).getLong(nbtExtraCapacity);
        return 0L;
    }

    public static long getFullCapacity(ItemStack stack) {
        return ((long)PurpleMatterConfig.item.hochStarCapacity) + getExtraCapacity(stack);
    }

    public EnumHochTier getTier(@Nonnull ItemStack stack) {
        long capacity = getMaximumEmc(stack);
        if(capacity >= EnumHochTier.OMEGA.minCapacity)
            return EnumHochTier.OMEGA;
        if(capacity >= EnumHochTier.SPHERE.minCapacity)
            return EnumHochTier.SPHERE;
        if(capacity >= EnumHochTier.VIER.minCapacity)
            return EnumHochTier.VIER;
        if(capacity >= EnumHochTier.DREI.minCapacity)
            return EnumHochTier.DREI;
        if(capacity >= EnumHochTier.ZWEI.minCapacity)
            return EnumHochTier.ZWEI;
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

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTagCompound();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        long starEmc = ItemPE.getEmc(stack);

        if (starEmc == 0)
            return 1.0D;
        if (starEmc > getMaximumEmc(stack))
            return 0.0D;

        return 1.0D - starEmc / (double) getMaximumEmc(stack);
    }

    @Override
    public long addEmc(@Nonnull ItemStack stack, long toAdd) {
        long add = Math.min(getMaximumEmc(stack) - getStoredEmc(stack), toAdd);
        ItemPE.addEmcToStack(stack, add);
        return add;
    }

    @Override
    public long extractEmc(@Nonnull ItemStack stack, long toRemove) {
        long sub = Math.min(getStoredEmc(stack), toRemove);
        ItemPE.removeEmc(stack, sub);
        return sub;
    }

    @Override
    public long getStoredEmc(@Nonnull ItemStack stack) {
        return ItemPE.getEmc(stack);
    }

    @Override
    public long getMaximumEmc(@Nonnull ItemStack stack) {
        return getFullCapacity(stack);
    }

}
