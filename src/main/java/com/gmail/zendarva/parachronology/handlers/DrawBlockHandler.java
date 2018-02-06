package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by James on 8/9/2017.
 */
public class DrawBlockHandler {

    ItemStack targStack;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void blockHighlightEvent(DrawBlockHighlightEvent event) {
        if (event.getTarget() == null || event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK)
            return;

        EntityPlayer player = event.getPlayer();
        World world = player.world;
        ItemStack wand = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
        if (wand.getItem() instanceof TimelessWand && TimelessUtility.getTimeless(wand).getCurrentEnergy() > 0){

            ITimeless timeless = TimelessUtility.getTimeless(wand);

            IBlockState state = getBlockToRender(world,wand);
            if (state == null)
                return;

            BlockPos pos = event.getTarget().getBlockPos();

            pos = pos.offset(event.getTarget().sideHit);
            double px = -(player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks());
            double py = -(player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks());
            double pz = -(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks());


            drawStateAt(state, pos, px, py, pz);
            EnumFacing facing = player.getHorizontalFacing().rotateAround(EnumFacing.Axis.Y);
            int count = Parachronology.proxy.wandStack.getCount()-1;
            for (int i = 1; i <= timeless.getExtraData();i++ ) {
                BlockPos temp;
                if (count > 0) {
                    temp = pos.offset(facing,i);
                    if (world.isAirBlock(temp)) {
                        drawStateAt(state, temp, px, py, pz);
                        count--;
                    }
                }

                if (count > 0) {
                    temp = pos.offset(facing.getOpposite(), i);
                    if (world.isAirBlock(temp)) {
                        drawStateAt(state, temp, px, py, pz);
                        count--;
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void drawStateAt(IBlockState state, BlockPos pos, double px, double py, double pz) {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

        GlStateManager.translate(px,py,pz);
        GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
        GlStateManager.enableCull();
        IBakedModel model = dispatcher.getModelForState(state);
        for (BlockRenderLayer layer : BlockRenderLayer.values()) {
            if (!state.getBlock().canRenderInLayer(state,layer))
                continue;
            ForgeHooksClient.setRenderLayer(layer);
            for (EnumFacing facing : EnumFacing.VALUES) {
                model.getQuads(state, facing, 0).forEach(f -> {
                    LightUtil.renderQuadColor(bufferBuilder, f, 0x80FFFFFF);});
            }
            model.getQuads(state, null, 0).forEach(f -> LightUtil.renderQuadColor(bufferBuilder, f, 0x80FFFFFF));
        }
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.disableCull();
    }

    private IBlockState getBlockToRender(World world, ItemStack stack) {
        ItemStack result = Parachronology.proxy.wandStack;
        if (result == null)
            return null;
        Block block = Block.getBlockFromItem(result.getItem());

        if (block == Blocks.AIR)
            return null;
        if (result.getHasSubtypes())
            return block.getStateFromMeta(result.getMetadata());
        return block.getDefaultState();


    }



}
