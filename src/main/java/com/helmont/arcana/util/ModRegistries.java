package com.helmont.arcana.util;

import com.helmont.arcana.Arcana;
import com.helmont.arcana.registry.ModItems;
import com.helmont.arcana.status_effects.FooledEffect;
import com.helmont.arcana.villager.ArcanaVillagers;
import com.mojang.brigadier.Message;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModRegistries {

    public static void registerModStuff() {
        registerCustomTrades();
        registerStatusEffects();
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

    public static final StatusEffect FOOLED = new FooledEffect();

    public static void registerStatusEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Arcana.MOD_ID, "fooled"), FOOLED);
    }
}
