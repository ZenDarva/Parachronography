package com.gmail.zendarva.parachronology.item;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by James on 7/30/2017.
 */
public class TimelessPickaxe extends ItemPickaxe{
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null)
            tooltip.add("Unlinked");
        else if (tag.getBoolean("linked"))
            tooltip.add("Linked");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (timeless.getCurrentEnergy() > 0)
            return super.getDestroySpeed(stack,state);
        return -1;
    }

    public TimelessPickaxe() {
        super(ToolMaterial.IRON);
        String name = "timelesspickaxe";
        this.setMaxStackSize(1);
        this.setRegistryName(name);
        this.setCreativeTab(Parachronology.TAB);
        this.setUnlocalizedName(Parachronology.MODID + "." + name);

    }
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(this.getRegistryName(), "inventory"));

    }

    @Override
    public boolean isDamageable() {
        return false;
    }


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (worldIn.isRemote) {
            return true;
        }
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);


        if (timeless.getTarget() == null) {
            return true;
        }
        if (!stack.canHarvestBlock(worldIn.getBlockState(pos))){
            return true;
        }


        if (worldIn.isBlockLoaded(timeless.getTarget())) {
            TileEntity entity = TimelessUtility.getTargetStorage(stack);
            if (entity instanceof StorageEntity) {
                if (stack.canHarvestBlock(worldIn.getBlockState(pos)) &&
                        worldIn.getBlockState(pos).getBlock().canSilkHarvest(worldIn,pos,state, (EntityPlayer) entityLiving) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0){
                    List<ItemStack> drops = new LinkedList<ItemStack>();
                    drops.add(getSilkTouchDrop(worldIn.getBlockState(pos)));
                    worldIn.destroyBlock(pos, false);
                    handleDrops(drops, (StorageEntity) entity,worldIn,pos);
                }
                else {
                    int value = EnchantmentHelper.getLootingModifier(entityLiving);
                    List<ItemStack> drops = worldIn.getBlockState(pos).getBlock().getDrops(worldIn, pos,state, value);
                    worldIn.destroyBlock(pos, false);
                    handleDrops(drops, (StorageEntity) entity, worldIn, pos);
                }
            }
        }
        timeless.addEnergy(-1);
        return false;
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

    private void handleDrops(List<ItemStack> drops, StorageEntity entity, World world, BlockPos pos){
        IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        for (ItemStack drop : drops) {
            drop = ItemHandlerHelper.insertItem(handler,drop,false);
            if (!drop.isEmpty())
                world.spawnEntity(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),drop));
        }
    }

    private ItemStack getSilkTouchDrop(IBlockState blockState){
        Item item = Item.getItemFromBlock(blockState.getBlock());
        int i = 0;

        if (item.getHasSubtypes())
        {
            i = blockState.getBlock().getMetaFromState(blockState);
        }

        return new ItemStack(item, 1, i);
    }


}
