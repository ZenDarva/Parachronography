package com.darva.parachronology.renderer;

import com.darva.parachronology.Parachronology;
import com.darva.parachronology.model.Displacer;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by James on 9/12/2015.
 */
public class DisplaceISBRH implements ISimpleBlockRenderingHandler {

    //Put this code in like was in Renderer and modify accordingly.
    //Use GL11.glBindTexture() instead of bindTextureByName
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        Displacer displacer = new Displacer();
        GL11.glPushMatrix();
        ResourceLocation textures;
        switch (metadata) {

            case 1:
                textures = (new ResourceLocation("parachronology:textures/blocks/displacer2.png"));
                break;
            case 2:
                textures = (new ResourceLocation("parachronology:textures/blocks/displacer3.png"));
                break;
            default:
                textures = (new ResourceLocation("parachronology:textures/blocks/displacer1.png"));
        }


        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures);
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        GL11.glRotatef(0f, 0f, 1f, 0f);
        GL11.glTranslatef((float) 0.5F, (float) -.65F, (float) 0.5F);
        displacer.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, -1.0F, 0.0625F);
        GL11.glPopMatrix();

    }

    //This method only returns false because you have TESR instead
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    //This makes item in inventory appear 3D or 2D, change to your liking
    public boolean shouldRender3DInInventory() {
        return true;
    }

    //Return that ID again
    public int getRenderId() {
        return Parachronology.renderId;
    }

}
