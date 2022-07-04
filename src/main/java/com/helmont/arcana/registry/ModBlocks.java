package com.helmont.arcana.registry;

import com.helmont.arcana.Arcana;
import com.helmont.arcana.blocks.ArcanaWorkbench;
import com.helmont.arcana.creative_tabs.ArcanaTab;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.TorchBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block ARCANA_WORKBENCH = registerBlock("arcana_workbench",
            new ArcanaWorkbench(FabricBlockSettings.of(Material.GLASS).nonOpaque().luminance(5)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).ticksRandomly()), ArcanaTab.ARCANA_TAB);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Arcana.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));

    }

    public static void registerModBlocks() {
        Arcana.LOGGER.info("Registering ModBlocks for " + Arcana.MOD_ID);
    }
}
