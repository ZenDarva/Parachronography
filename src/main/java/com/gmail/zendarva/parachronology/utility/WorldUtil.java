package com.gmail.zendarva.parachronology.utility;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

/**
 * Created by James on 8/11/2017.
 */
public class WorldUtil {
    public static ItemStack UseItemStack(WorldServer world, BlockPos pos, ItemStack stack, EnumFacing facing){
      FakePlayer player = FakePlayerFactory.getMinecraft(world);
      ItemStack old = player.getHeldItem(EnumHand.MAIN_HAND);
      player.setHeldItem(EnumHand.MAIN_HAND,stack);
      player.getHeldItemMainhand().onItemUse(player,world,pos,EnumHand.MAIN_HAND,facing,.5f,.5f,.5f);
      ItemStack result = player.getHeldItem(EnumHand.MAIN_HAND);
      player.setHeldItem(EnumHand.MAIN_HAND, old);
      return result;
    }
}
