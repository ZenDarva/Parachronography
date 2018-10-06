package com.gmail.zendarva.parachronology.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import javax.annotation.Nullable;

/**
 * Created by James on 7/17/2018.
 */

@SideOnly(Side.CLIENT)
public class FadingBlockRender extends Render<FadingBlock> {
    protected FadingBlockRender(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(FadingBlock fadingBlock) {
        return null;
    }

    @Override
    public void doRender(FadingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();

        GlStateManager.translate(x, y, z);

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBlockState state = getBlockState(entity);
        GlStateManager.enableCull();
        IBakedModel model = dispatcher.getModelForState(state);
        for (BlockRenderLayer layer : BlockRenderLayer.values()) {
            if (!state.getBlock().canRenderInLayer(state,layer))
                continue;
            ForgeHooksClient.setRenderLayer(layer);
            for (EnumFacing facing : EnumFacing.VALUES) {
                model.getQuads(state, facing, 0).forEach(f -> {
                    LightUtil.renderQuadColor(bufferBuilder, f, getColor(entity));});
            }
            model.getQuads(state, null, 0).forEach(f -> LightUtil.renderQuadColor(bufferBuilder, f, getColor(entity)));
        }
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.disableCull();
    }

    private IBlockState getBlockState(FadingBlock block){
        IBlockState state = block.getTargBlock();
        if (state == null){
            state = Blocks.COBBLESTONE.getDefaultState();
        }
        return state;
    }

    private int getColor(FadingBlock entity){
        int num = (entity.opacity);
        return (int)Long.parseLong(Integer.toHexString(num) +"FFFFFF",16);
    }


    public static class Factory implements IRenderFactory<FadingBlock> {

        @Override
        public Render<? super FadingBlock> createRenderFor(RenderManager manager) {
            return new FadingBlockRender(manager);
        }

    }

}
