package darkninja2462.purplematter.handlers;

import darkninja2462.purplematter.PurpleMatter;
import darkninja2462.purplematter.common.item.ItemHochStar;
import darkninja2462.purplematter.util.ItemNBTUtils;
import moze_intel.projecte.utils.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static darkninja2462.purplematter.handlers.HandlerItems.*;

@Mod.EventBusSubscriber(modid = PurpleMatter.MODID, value = Side.CLIENT)
public class HandlerTooltips {

    public static String TRANSLATEKEY_STOREDEMC_TOOLTIP = "tooltip.purplematter.emccapacity";

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;
        if(stack.getItem() == hoch_star) {
            long capacity = ItemHochStar.getFullCapacity(stack);
            event.getToolTip().add(TextFormatting.YELLOW + I18n.format(TRANSLATEKEY_STOREDEMC_TOOLTIP) + ": " + TextFormatting.RESET + Constants.EMC_FORMATTER.format(capacity));
        }
    }

}
