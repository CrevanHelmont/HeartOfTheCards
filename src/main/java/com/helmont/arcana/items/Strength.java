package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Strength extends Item {
    public Strength(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.strength.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!user.getWorld().isClient) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            if (!player.hasStatusEffect(StatusEffects.STRENGTH)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 5, false, true, true));
                ItemStack card = user.getStackInHand(hand);
                card.setDamage(card.getDamage() + 1);
                if (card.getDamage() >= card.getMaxDamage()) {
                    card.decrement(1);
                    world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_EYE_DEATH,
                            SoundCategory.PLAYERS, 1f, 1f);
                }
                return TypedActionResult.success(card, true);
            }
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
