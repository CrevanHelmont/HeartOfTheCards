package com.helmont.arcana;

import com.helmont.arcana.item_containers.CardHolderClientScreen;
import com.helmont.arcana.item_containers.CardHolderScreenHandler;
import com.helmont.arcana.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
//import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
//import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ArcanaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }
    /*@Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<CardHolderScreenHandler>registerFactory(Arcana.ARC_ID, (container -> new CardHolderClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText(Arcana.ARC_TRANSLATION_KEY))));
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ARCANA_WORKBENCH, RenderLayer.getCutout());
    }*/
}
