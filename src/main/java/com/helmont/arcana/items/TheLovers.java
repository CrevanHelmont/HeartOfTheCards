package com.helmont.arcana.items;

import com.helmont.arcana.util.MiscUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class TheLovers extends Item {
    public TheLovers(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_lovers.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        ActionResult result = ActionResult.FAIL;
        PassiveEntity passive = (PassiveEntity) entity;
        if (!user.getWorld().isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            ServerWorld world = player.getWorld();
            if (MiscUtil.isEligibleToBaby(world, passive) && MiscUtil.isAnimal(entity)
            && entity.getType() != EntityType.POLAR_BEAR) {

                AnimalEntity animal = (AnimalEntity) entity;

                if (!(animal.getLovingPlayer() == user) && !animal.isInLove() &&
                        animal.getLoveTicks() == 0 && animal.getBreedingAge() == 0) {

                    animal.lovePlayer(user);

                    ItemStack card = user.getStackInHand(hand);
                    card.setDamage(card.getDamage() + 1);
                    if (card.getDamage() >= card.getMaxDamage()) {
                        card.decrement(1);
                        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_EYE_DEATH,
                                SoundCategory.PLAYERS, 1f, 1f);
                    }
                    result = ActionResult.CONSUME_PARTIAL;
                    return result;
                }
            }
        }
        return result;
    }

}
