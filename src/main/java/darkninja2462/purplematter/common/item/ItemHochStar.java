package darkninja2462.purplematter.common.item;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.mod.SimpleModItem;
import darkninja2462.purplematter.util.ItemNBTUtils;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar.EnumKleinTier;
import moze_intel.projecte.utils.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@SimpleModItem("hoch_star")
public class ItemHochStar extends Item implements IItemEmc {

    //TODO make config option
    public static final long baseCapacity = EnumHochTier.EIN.capacity;

    public static final String nbtMass = ItemNBTUtils.makeKey(PurpleMatter.MODID, "mass");
    public static final String nbtExtraCapacity = ItemNBTUtils.makeKey(PurpleMatter.MODID, "extra_capacity");

    public ItemHochStar() {
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
        public final long capacity;
        EnumHochTier(String name, int tier, long capacity) {
            this.name = name;
            this.tier = tier;
            this.capacity = capacity;
        }

        public long getCapacity() {
            return this.capacity;
        }
    }

    private static Map<EnumKleinTier, Long> MAX_KLEIN_EMC = new EnumMap<>(EnumKleinTier.class);
    static {
        MAX_KLEIN_EMC.put(EnumKleinTier.EIN, Constants.MAX_KLEIN_EMC[0]);
        MAX_KLEIN_EMC.put(EnumKleinTier.ZWEI, Constants.MAX_KLEIN_EMC[1]);
        MAX_KLEIN_EMC.put(EnumKleinTier.DREI, Constants.MAX_KLEIN_EMC[2]);
        MAX_KLEIN_EMC.put(EnumKleinTier.VIER, Constants.MAX_KLEIN_EMC[3]);
        MAX_KLEIN_EMC.put(EnumKleinTier.SPHERE, Constants.MAX_KLEIN_EMC[4]);
        MAX_KLEIN_EMC.put(EnumKleinTier.OMEGA, Constants.MAX_KLEIN_EMC[5]);
    }

    public static void setMass(ItemStack stack, long mass) {
        NBTTagCompound nbt = ItemNBTUtils.getOrCreateCompound(stack);
        nbt.setLong(nbtMass, mass);
    }

    public static long getMass(ItemStack stack) {
        if(stack.hasTagCompound())
            return Objects.requireNonNull(stack.getTagCompound()).getLong(nbtMass);
        return 0L;
    }

    public static void setExtraCapacity(ItemStack stack, long extraCapacity) {
        NBTTagCompound nbt = ItemNBTUtils.getOrCreateCompound(stack);
        nbt.setLong(nbtExtraCapacity, extraCapacity);
    }

    public static long getExtraCapacity(ItemStack stack) {
        if(stack.hasTagCompound())
            return Objects.requireNonNull(stack.getTagCompound()).getLong(nbtExtraCapacity);
        return 0L;
    }

    public EnumHochTier getTier(@Nonnull ItemStack stack) {
        long capacity = getMaximumEmc(stack);
        if(capacity >= EnumHochTier.OMEGA.capacity)
            return EnumHochTier.OMEGA;
        if(capacity >= EnumHochTier.SPHERE.capacity)
            return EnumHochTier.SPHERE;
        if(capacity >= EnumHochTier.VIER.capacity)
            return EnumHochTier.VIER;
        if(capacity >= EnumHochTier.DREI.capacity)
            return EnumHochTier.DREI;
        if(capacity >= EnumHochTier.ZWEI.capacity)
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
        {
            return 1.0D;
        }

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
        return baseCapacity + getExtraCapacity(stack);
    }

}
