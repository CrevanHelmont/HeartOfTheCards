package com.helmont.arcana.screenhandlers;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class ArcanaWorkbenchScreenHandler extends CraftingScreenHandler {

    public ArcanaWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }
}
