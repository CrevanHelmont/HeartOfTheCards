package com.helmont.arcana.creative_tabs;

import com.helmont.arcana.registry.ModBlocks;
import com.helmont.arcana.registry.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import static com.helmont.arcana.Arcana.MOD_ID;

public class ArcanaTab {
    public static final ItemGroup ARCANA_TAB = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "general"))
            .icon(() -> new ItemStack(ModItems.TAROT_CARD))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModBlocks.ARCANA_WORKBENCH));
                stacks.add(new ItemStack(ModItems.TAROT_CARD));
                stacks.add(new ItemStack(ModItems.CARD_HOLDER));
                stacks.add(new ItemStack(ModItems.THE_FOOL));
                stacks.add(new ItemStack(ModItems.THE_MAGICIAN));
                stacks.add(new ItemStack(ModItems.THE_HIGH_PRIESTESS));
                stacks.add(new ItemStack(ModItems.THE_EMPRESS));
                stacks.add(new ItemStack(ModItems.THE_EMPEROR));
                stacks.add(new ItemStack(ModItems.THE_HIEROPHANT));
                stacks.add(new ItemStack(ModItems.THE_LOVERS));
                stacks.add(new ItemStack(ModItems.THE_CHARIOT));
                stacks.add(new ItemStack(ModItems.STRENGTH));
                stacks.add(new ItemStack(ModItems.THE_HERMIT));
                stacks.add(new ItemStack(ModItems.WHEEL_OF_FORTUNE));
                stacks.add(new ItemStack(ModItems.JUSTICE));
                stacks.add(new ItemStack(ModItems.THE_HANGED_MAN));
                stacks.add(new ItemStack(ModItems.DEATH));
                stacks.add(new ItemStack(ModItems.TEMPERANCE));
                stacks.add(new ItemStack(ModItems.THE_DEVIL));
                stacks.add(new ItemStack(ModItems.THE_TOWER));
                stacks.add(new ItemStack(ModItems.THE_STAR));
                stacks.add(new ItemStack(ModItems.THE_MOON));
                stacks.add(new ItemStack(ModItems.THE_SUN));
                stacks.add(new ItemStack(ModItems.JUDGEMENT));
                stacks.add(new ItemStack(ModItems.THE_WORLD));
            }).build();
    public static final ItemGroup ARCANA_MATS = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "materials"))
            .icon(() -> new ItemStack(ModItems.GOAT_HORN))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModItems.GOAT_HORN));
                stacks.add(new ItemStack(ModItems.STARDUST));
                stacks.add(new ItemStack(ModItems.ENRICHED_AMETHYST));
            }).build();
}
