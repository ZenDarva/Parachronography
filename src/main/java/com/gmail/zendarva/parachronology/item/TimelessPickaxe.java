package com.gmail.zendarva.parachronology.item;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
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

/**
 * Created by James on 7/30/2017.
 */
public class TimelessPickaxe extends ItemPickaxe{
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null || !tag.getBoolean("linked"))
            tooltip.add("Unlinked");
        else if (tag.getBoolean("linked"))
            tooltip.add("Linked");


    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (getCurrentEnergy(stack) > 0)
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

        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (timeless.getTarget() == null) {
            return true;
        }
        if (!stack.canHarvestBlock(worldIn.getBlockState(pos))){
            return true;
        }
        if (entityLiving.isSneaking()){
            return true;
        }
        if (worldIn.isRemote) {
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

    private int getCurrentEnergy(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null || !tag.hasKey("durability")) {
            ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
            return timeless.getCurrentEnergy();
        }
        return tag.getInteger("durability");
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        int curEnergy = getCurrentEnergy(stack);
        return (timeless.getMaxEnergy() - (double) curEnergy) / (double) timeless.getMaxEnergy();
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

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity entity = worldIn.getTileEntity(pos);
        if (entity == null)
            return EnumActionResult.FAIL;
        ITimeless timeless = player.getHeldItemMainhand().getCapability(TimelessProvider.timeless,null);
        ItemStack targ = player.getHeldItemMainhand();
        if (timeless == null) //Wha?
            return EnumActionResult.FAIL;
        if (player.isSneaking()) {
            timeless.setTarget(null);
            if (targ.getTagCompound() != null) //Should always be the case, but... NPE's suck.
                targ.getTagCompound().setBoolean("linked", false);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag== null){
            tag = new NBTTagCompound();
        }
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (timeless != null && tag != null)
            tag.setInteger("durability",timeless.getCurrentEnergy());
        return tag;
    }

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return super.updateItemStackNBT(nbt);
    }
}
