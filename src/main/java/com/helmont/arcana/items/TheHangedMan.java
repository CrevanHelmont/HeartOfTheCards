package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class TheHangedMan extends Item {
    public TheHangedMan(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_hanged_man.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
