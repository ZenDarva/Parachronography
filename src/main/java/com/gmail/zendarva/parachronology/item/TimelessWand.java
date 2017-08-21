package com.gmail.zendarva.parachronology.item;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.network.PacketHandler;
import com.gmail.zendarva.parachronology.network.UseWandPacket;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by James on 8/9/2017.
 */
public class TimelessWand extends Item {

    public TimelessWand() {
        String name = "timelesswand";
        this.setMaxStackSize(1);
        this.setRegistryName(name);
        this.setCreativeTab(Parachronology.TAB);
        this.setUnlocalizedName(Parachronology.MODID + "." + name);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (timeless.getTarget() == null)
            tooltip.add("Unlinked");
        else
            tooltip.add("Linked");
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(this.getRegistryName(), "inventory"));

    }
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        return (timeless.getMaxEnergy() - (double)timeless.getCurrentEnergy()) / (double)timeless.getMaxEnergy();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking())
            return EnumActionResult.FAIL;
        if (!worldIn.isRemote)
            return EnumActionResult.SUCCESS;
        ItemStack wand = player.getHeldItem(EnumHand.MAIN_HAND);
        if (TimelessUtility.getTimeless(wand).getCurrentEnergy() >0) {
            World targWorld = DimensionManager.getWorld(TimelessUtility.getTimeless(wand).getWorldId());
            BlockPos storagePos = TimelessUtility.getTargetStorage(wand).getPos();

            PacketHandler.INSTANCE.sendToServer(new UseWandPacket(storagePos, pos.offset(facing), TimelessUtility.getSelectedStorageSlot(targWorld, wand), facing));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }






}
