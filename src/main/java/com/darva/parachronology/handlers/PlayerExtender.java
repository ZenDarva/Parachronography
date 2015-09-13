package com.darva.parachronology.handlers;

import com.darva.parachronology.proxy.commonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by James on 9/12/2015.
 */
public class PlayerExtender implements IExtendedEntityProperties {
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.setBoolean("First", false);
        compound.setTag(EXT_PROP_NAME, newCompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        System.out.println("LoadNBT");
        if (compound.getCompoundTag(EXT_PROP_NAME).hasKey("First")) {
            this.firstConnection = false;
        } else {
            this.firstConnection = true;
        }
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public static PlayerExtender get(EntityPlayer player) {
        return (PlayerExtender) player.getExtendedProperties(EXT_PROP_NAME);
    }

    public PlayerExtender(EntityPlayer player) {
        EntityPlayer player1 = player;
    }

    public final static String EXT_PROP_NAME = "Parachronology";
    public boolean firstConnection = true;

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PlayerExtender.EXT_PROP_NAME, new PlayerExtender(player));
    }


    private static String getSaveKey(EntityPlayer player) {
        return player.getPersistentID() + ":" + EXT_PROP_NAME;
    }

    public static void saveProxyData(EntityPlayer player) {
        PlayerExtender playerData = PlayerExtender.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);
        commonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player) {
        PlayerExtender playerData = PlayerExtender.get(player);
        NBTTagCompound savedData = commonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }
    }

}
