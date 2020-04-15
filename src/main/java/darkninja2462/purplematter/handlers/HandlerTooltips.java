package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.item.ItemHochStar;
import darkninja2462.purplematter.util.ItemNBTUtils;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.utils.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.stream.IntStream;

import static darkninja2462.purplematter.handlers.HandlerItems.*;
import static moze_intel.projecte.gameObjs.ObjHandler.kleinStars;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID, value = Side.CLIENT)
public class HandlerTooltips {

    public static String TRANSLATEKEY_STOREDEMC_TOOLTIP = "tooltip.purplematter.emccapacity";

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;
        if(stack.getItem() instanceof IItemEmc) {
            long capacity = ((IItemEmc) stack.getItem()).getMaximumEmc(stack);
            List<String> tooltip = event.getToolTip();
            //Insert before "Stored EMC:" or at end
            int index = IntStream.range(0, tooltip.size()).filter((i) -> tooltip.get(i).contains(I18n.format("pe.emc.storedemc_tooltip"))).findFirst().orElse(tooltip.size());
            tooltip.add(index, TextFormatting.YELLOW + I18n.format(TRANSLATEKEY_STOREDEMC_TOOLTIP) + ": " + TextFormatting.RESET + Constants.EMC_FORMATTER.format(capacity));
        }
    }

}
