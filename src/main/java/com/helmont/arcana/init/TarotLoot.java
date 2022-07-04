package com.helmont.arcana.init;

import com.helmont.arcana.registry.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class TarotLoot {
    private static final Identifier SAND_LOOT_ID = Blocks.SAND.getLootTableId();
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, table, setter) -> {

            if (id.getPath().contains("zombie") || id.getPath().contains("skeleton")
                    || id.getPath().contains("spider") || id.getPath().contains("witch")
                    || id.getPath().contains("enderman") || id.getPath().contains("blaze")
                    || id.getPath().contains("piglin") || id.getPath().contains("hoglin")
                    || id.getPath().contains("creeper") || id.getPath().contains("silverfish")
                    || id.getPath().contains("vex") || id.getPath().contains("evoker")
                    || id.getPath().contains("creeper") || id.getPath().contains("silverfish")
                    || id.getPath().contains("phantom") || id.getPath().contains("husk")
                    || id.getPath().contains("drowned") || id.getPath().contains("stray")
                    || id.getPath().contains("ghast") || id.getPath().contains("guardian")
                    || id.getPath().contains("ravager") || id.getPath().contains("zoglin")
                    || id.getPath().contains("endermite") || id.getPath().contains("pillager")
                    || id.getPath().contains("vindicator")) {

                LootPool.Builder builder = LootPool.builder();
                builder.rolls(ConstantLootNumberProvider.create(1));
                builder.conditionally(RandomChanceWithLootingLootCondition.builder(0.01f, 0.01f));
                builder.with(ItemEntry.builder(ModItems.TAROT_CARD));
                table.pool(builder);

                }

            if (id.getPath().contains("slime") || id.getPath().contains("magma_cube")) {

                LootPool.Builder builder = LootPool.builder();
                builder.rolls(ConstantLootNumberProvider.create(1));
                builder.conditionally(RandomChanceWithLootingLootCondition.builder(0.005f, 0.005f));
                builder.with(ItemEntry.builder(ModItems.TAROT_CARD));
                table.pool(builder);

                }

            if (id.getPath().contains("goat")) {

                LootPool.Builder builder = LootPool.builder();
                    builder.rolls(ConstantLootNumberProvider.create(1));
                    builder.conditionally(RandomChanceWithLootingLootCondition.builder(0.15f, 0.05f));
                    builder.with(ItemEntry.builder(ModItems.GOAT_HORN));
                    table.pool(builder);

                }

            if (SAND_LOOT_ID.equals(id)) {
                LootPool.Builder builder = LootPool.builder();
                builder.rolls(ConstantLootNumberProvider.create(1));
                builder.conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.001f, 0.005f, 0.01f, 0.02f));
                builder.with(ItemEntry.builder(ModItems.STARDUST));
                table.pool(builder);
                }
            }
        );
    }
}
