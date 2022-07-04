package com.helmont.arcana;

import com.helmont.arcana.init.TarotLoot;
import com.helmont.arcana.item_containers.CardHolder;
import com.helmont.arcana.item_containers.CardHolderInvInterface;
import com.helmont.arcana.item_containers.CardHolderScreenHandler;
import com.helmont.arcana.registry.ModBlocks;
import com.helmont.arcana.registry.ModItems;
import com.helmont.arcana.screenhandlers.ArcanaWorkbenchScreenHandler;
import com.helmont.arcana.util.ModRegistries;
import com.helmont.arcana.villager.ArcanaVillagers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Arcana implements ModInitializer {

    public static final String MOD_ID = "arcproj";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ScreenHandlerType<ArcanaWorkbenchScreenHandler> ARCANA_WORKBENCH_SCREEN_HANDLER;
    public static final Identifier ARC_ID = new Identifier(MOD_ID, "card_holder");
    public static final String ARC_TRANSLATION_KEY = Util.createTranslationKey("container", ARC_ID);
    static {
        ARCANA_WORKBENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "arcana_workbench"), ArcanaWorkbenchScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        ModBlocks.registerModBlocks();
        ModItems.registerItems();
        TarotLoot.init();
        ArcanaVillagers.setupPOIs();
        ModRegistries.registerModStuff();

        ContainerProviderRegistry.INSTANCE.registerFactory(ARC_ID, ((syncId, identifier, player, buf) -> {
            final ItemStack stack = buf.readItemStack();
            final Hand hand = buf.readInt() == 0 ? Hand.MAIN_HAND : Hand.OFF_HAND;
            final CardHolderInvInterface inventory = CardHolder.getInventory(stack, hand, player);
            final String customTitle = buf.readString();

            return new CardHolderScreenHandler(syncId, player.getInventory(), inventory.getInventory(), inventory.getInventoryWidth(), inventory.getInventoryHeight(), hand, customTitle);
        }));

    }
}
