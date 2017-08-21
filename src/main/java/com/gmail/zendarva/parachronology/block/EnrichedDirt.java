package com.gmail.zendarva.parachronology.block;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by James on 7/18/2017.
 */
public class EnrichedDirt extends Block {
    public EnrichedDirt() {
        super(Material.GROUND);
        String name = "enricheddirt";
        this.setHardness(1.2f);
        this.setLightOpacity(1);
        this.setResistance(5f);
        this.blockSoundType = SoundType.GROUND;
        this.setHarvestLevel("shovel", 0);
        this.setCreativeTab(Parachronology.TAB);
        this.setRegistryName(name);
        this.setUnlocalizedName(Parachronology.MODID + "." + name);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName("enricheddirtitem");
        this.needsRandomTick = true;
        this.setCreativeTab(Parachronology.TAB);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        Block block = world.getBlockState(pos.add(0,1,0)).getBlock();
        if (block instanceof IGrowable || block instanceof IPlantable) {
            block.updateTick(world,pos.add(0,1,0),world.getBlockState(pos.add(0,1,0)),random);
        }
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return true;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
