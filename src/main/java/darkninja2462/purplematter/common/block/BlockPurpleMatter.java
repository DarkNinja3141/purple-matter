package darkninja2462.purplematter.common.block;

import darkninja2462.purplematter.handlers.HandlerCreativeTabs;
import darkninja2462.purplematter.mod.SimpleModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SimpleModBlock("purple_matter_block")
public class BlockPurpleMatter extends Block {

    public BlockPurpleMatter() {
        super(Material.IRON);
        this.setCreativeTab(HandlerCreativeTabs.PURPLE_MATTER);
    }

}
