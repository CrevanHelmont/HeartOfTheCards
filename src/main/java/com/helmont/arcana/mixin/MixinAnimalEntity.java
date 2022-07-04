package com.helmont.arcana.mixin;

import com.helmont.arcana.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(AnimalEntity.class)
public abstract class MixinAnimalEntity extends PassiveEntity {


    protected MixinAnimalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void onIsBreedingItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(itemStack.isOf(ModItems.THE_LOVERS));
        //if (itemStack == ModItems.THE_LOVERS.getDefaultStack()) {
            //System.out.println("Can Breed!");
            //cir.setReturnValue(true);
            //cir.cancel();
        //}
    }

    /*@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Item lovers = ModItems.THE_LOVERS;

        MixinAnimalEntity animal = (MixinAnimalEntity) (PassiveEntity) this;
        if (this.isOwner(player) && this.isTamed() && player.getStackInHand(hand).isOf(lovers)) {
            if (!player.getWorld().isClient()) {
                if (!(this.getLovingPlayer() == player) && !this.isInLove() &&
                        this.getLoveTicks() == 0 && this.breedingAge == 0) {

                    ServerWorld serverWorld = (ServerWorld) player.getWorld();

                    this.setLoveTicks(600);
                    this.lovePlayer(player);

                    ItemStack card = player.getStackInHand(hand);
                    card.setDamage(card.getDamage() + 1);

                    if (card.getDamage() >= card.getMaxDamage()) {
                        card.decrement(1);
                        serverWorld.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_EYE_DEATH,
                                SoundCategory.PLAYERS, 1f, 1f);
                    }
                    cir.setReturnValue(ActionResult.CONSUME_PARTIAL);
                    cir.cancel();
                }
            }
        }
    }*/
}
