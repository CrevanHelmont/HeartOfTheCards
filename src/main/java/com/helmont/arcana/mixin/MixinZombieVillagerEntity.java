package com.helmont.arcana.mixin;

import com.helmont.arcana.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ZombieVillagerEntity.class)
public abstract class MixinZombieVillagerEntity {


    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).getItem() == ModItems.THE_HIGH_PRIESTESS) {
            if (!player.getWorld().isClient && !((MixinConvertCheckInvoker) this).invokeIsConverting()) {
                ((MixinZombieVillagerInvoker) this).invokeSetConverting(player.getUuid(), 60);
                cir.setReturnValue(ActionResult.SUCCESS);
                player.getStackInHand(hand).decrement(1);
            }
        }
    }
}
