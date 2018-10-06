package com.gmail.zendarva.parachronology.interop.waila;

import com.gmail.zendarva.parachronology.block.Displacer;
import com.gmail.zendarva.parachronology.tileEntity.DisplacerEntity;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.List;

public class ParachronologyAddon implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return iWailaDataAccessor.getStack();
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        if (iWailaDataAccessor.getTileEntity() instanceof DisplacerEntity)
        {
            DisplacerEntity entity = (DisplacerEntity) iWailaDataAccessor.getTileEntity();
            int tier = entity.getBlockMetadata();
            String tierStr;
            switch (tier)
            {
                case 0:
                    tierStr ="Tier 1";
                    break;
                case 1:
                    tierStr = "Tier 2";
                    break;
                case 2:
                    tierStr ="Tier 3";
                    break;
                default:
                    tierStr= "Error!";
            }
            list.add(tierStr);

        }
        return list;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, BlockPos blockPos) {
        return null;
    }

    public static void registerAddon(IWailaRegistrar register)
    {
        ParachronologyAddon addon = new ParachronologyAddon();
        register.registerBodyProvider(addon,Displacer.class);
    }
    public static void register(IWailaRegistrar register)
    {
        ParachronologyAddon addon = new ParachronologyAddon();
        register.registerBodyProvider(addon,Displacer.class);
    }
}