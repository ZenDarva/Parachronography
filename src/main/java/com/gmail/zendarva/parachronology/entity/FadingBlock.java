package com.gmail.zendarva.parachronology.entity;

import com.google.common.base.Optional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by James on 7/17/2018.
 */
public class FadingBlock extends Entity {
    private ItemStack possibleDrop;
    protected int opacity = 255;
    protected int delta = -5;
    protected int maxOscillations=3;
    protected int oscillations=0;
    protected int dropChance = 13;
    private static final DataParameter<Optional<IBlockState>> sharedBlockState = EntityDataManager.createKey(FadingBlock.class,DataSerializers.OPTIONAL_BLOCK_STATE);

    public FadingBlock(World worldIn) {
        super(worldIn);
    }

    public FadingBlock(World worldIn, BlockPos pos, IBlockState targBlock, ItemStack possibleDrop){
        super(worldIn);
        this.possibleDrop = possibleDrop;
        this.setPosition(pos.getX(),pos.getY(),pos.getZ());
        this.dataManager.set(sharedBlockState,Optional.of(targBlock));
    }
    public FadingBlock(World worldIn, BlockPos pos, IBlockState targBlock, ItemStack possibleDrop, int dropChance){
        super(worldIn);
        this.possibleDrop = possibleDrop;
        this.setPosition(pos.getX(),pos.getY(),pos.getZ());
        this.dataManager.set(sharedBlockState,Optional.of(targBlock));
        this.dropChance=dropChance;
    }

    @Override
    protected void entityInit() {
        dataManager.register(sharedBlockState,Optional.absent());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("block"))
            this.dataManager.set(sharedBlockState, Optional.of(NBTUtil.readBlockState(nbtTagCompound.getCompoundTag("block"))));
        if (nbtTagCompound.hasKey("drop")){
            this.possibleDrop=new ItemStack(nbtTagCompound.getCompoundTag("drop"));
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound block = new NBTTagCompound();
        Optional<IBlockState> state = dataManager.get(sharedBlockState);
        if (state.isPresent()){
            NBTUtil.writeBlockState(block,state.get());
            nbtTagCompound.setTag("block",block);
        }
        NBTTagCompound drop = possibleDrop.serializeNBT();
        nbtTagCompound.setTag("drop",drop);

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void onEntityUpdate() {
        if (this.isDead)
                return;
        if (oscillations>=maxOscillations)
                checkDie();
            opacity+=delta;
            if (opacity >= 255) {
                opacity = 255;
                delta*=-1;
                oscillations++;
            }
            if (opacity<=30) {
                opacity = 30;
                delta*=-1;
                oscillations++;
                checkDie();
            }
    }

    private void checkDie() {
        if (world.isRemote){
            return;
        }
        if (world.rand.nextInt(100) <40 && !this.isDead){
            this.setDead();
                if(world.rand.nextInt(100) <= dropChance){
                    EntityItem item = new EntityItem(world,posX,posY,posZ,possibleDrop.copy());
                    world.spawnEntity(item);
            }
        }
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    public IBlockState getTargBlock(){
        return dataManager.get(sharedBlockState).get();
    }
}
