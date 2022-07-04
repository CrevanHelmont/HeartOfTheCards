package com.helmont.arcana.util;

import com.helmont.arcana.registry.ModItems;
import com.helmont.arcana.villager.ArcanaVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModRegistries {

    public static void registerModStuff() {
        registerCustomTrades();
    }

    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ArcanaVillagers.MYSTIC, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 1),
                            new ItemStack(ModItems.TAROT_CARD, 1),
                            10,2,0.02f));
                });
    }
}
