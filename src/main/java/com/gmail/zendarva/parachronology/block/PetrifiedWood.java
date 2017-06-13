package com.gmail.zendarva.parachronology.block;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 9/12/2015.
 */
public class PetrifiedWood extends Block {
    @Override
    public String getUnlocalizedName() {
        return "parachronology:petrifiedwood";
    }

    public PetrifiedWood(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.setHardness(1.2f);
        this.setLightOpacity(1);
        this.setResistance(5f);
        this.blockSoundType= SoundType.STONE;
        this.setHarvestLevel("pickaxe", 0);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName("petrifiedwood");
        GameRegistry.register(this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName("petrifiedwoodItem");
        GameRegistry.register(itemBlock);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel()
    {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Parachronology.MODID + ":" + getRegistryName(), "inventory"));
    }

}
