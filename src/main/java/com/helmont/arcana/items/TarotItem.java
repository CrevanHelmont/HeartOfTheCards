package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


import java.util.List;


public class TarotItem extends Item {

    public TarotItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.tarot_card.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            //user.travel(new Vec3d(0.00, 10000.00, 0.00));
            user.sendMessage(Text.literal("Nothing happens. This must be crafted into a different card!").formatted(Formatting.LIGHT_PURPLE)/*, Util.NIL_UUID*/);

        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
