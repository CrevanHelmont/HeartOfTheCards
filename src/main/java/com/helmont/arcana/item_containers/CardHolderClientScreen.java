package com.helmont.arcana.item_containers;

import com.helmont.arcana.Arcana;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CardHolderClientScreen extends HandledScreen<CardHolderScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Arcana.MOD_ID, "textures/gui/container/generic_54.png");
    private final int rows;

    public CardHolderClientScreen(CardHolderScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, handler.customTitle.isEmpty() ? title : Text.literal(handler.customTitle));
        this.passEvents = false;
        this.rows = 2;
        this.backgroundHeight = 114 + this.rows * 18;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    protected void drawBackground(MatrixStack matrixStack, float f, int mouseY, int i)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,TEXTURE);

        int j = (this.width - this.backgroundWidth) / 2;
        int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrixStack, j, k, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        this.drawTexture(matrixStack, j, k + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }

    protected void drawForeground(MatrixStack matrixStack, int i, int j)
    {
        this.textRenderer.draw(matrixStack, this.title, 8.0F, 6.0F, 4210752);
        this.textRenderer.draw(matrixStack, this.playerInventoryTitle, 8.0F, (float)(this.backgroundHeight - 96 + 2), 16755200);
    }
}
