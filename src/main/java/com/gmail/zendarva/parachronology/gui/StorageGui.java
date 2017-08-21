package com.gmail.zendarva.parachronology.gui;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.container.StorageContainer;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by James on 7/30/2017.
 */
public class StorageGui extends GuiContainer {

    private final StorageEntity entity;
    private static final ResourceLocation background = new ResourceLocation(Parachronology.MODID, "textures/gui/storage.png");

    public StorageGui(StorageEntity entity, StorageContainer container){
        super(container);

        this.entity = entity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }


}
