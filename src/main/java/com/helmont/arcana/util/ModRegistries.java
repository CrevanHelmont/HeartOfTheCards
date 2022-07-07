package com.helmont.arcana.util;

import com.helmont.arcana.Arcana;
import com.helmont.arcana.items.TheHierophant;
import com.helmont.arcana.registry.ModItems;
import com.helmont.arcana.status_effects.FooledEffect;
import com.helmont.arcana.villager.ArcanaVillagers;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;

public class ModRegistries {

    public static void registerModStuff() {
        registerCustomTrades();
        registerStatusEffects();
        tooltipadd();
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

    public static void tooltipadd() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() == ModItems.THE_HIEROPHANT) {
                NbtCompound nbtData = stack.getNbt();
                if (nbtData != null) {
                    lines.add(1, Text.literal("Total EXP: " + nbtData.getInt("TotalExp") + "/10000")
                            .formatted(Formatting.GREEN));
                    lines.add(2, Text.literal("Mode: ")
                            .formatted(Formatting.GREEN)
                            .append(Text.literal(TheHierophant.modes[nbtData.getInt("Mode")])
                                    .formatted(Formatting.BLUE)));
                }
            }
        });
    }

    public static final StatusEffect FOOLED = new FooledEffect();

    public static void registerStatusEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Arcana.MOD_ID, "fooled"), FOOLED);
    }
}
