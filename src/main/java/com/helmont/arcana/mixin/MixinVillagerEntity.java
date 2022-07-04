package com.helmont.arcana.mixin;

import com.helmont.arcana.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends MerchantEntity implements MixinVillagerAccessor, InteractionObserver, VillagerDataContainer {



    public MixinVillagerEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Item lovers = ModItems.THE_LOVERS;
        if (player.getStackInHand(hand).isOf(lovers)) {
            if (!player.getWorld().isClient()) {
                if (this.breedingAge == 0 && !this.isReadyToBreed()) {

                    this.setFoodLevel(12);
                    ServerWorld serverWorld = (ServerWorld) player.getWorld();

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

        //Chariot
        Item chariot = ModItems.THE_CHARIOT;
        if (player.getStackInHand(hand).isOf(chariot)) {
            if (!player.getWorld().isClient()) {
                NbtCompound nbtCompound = new NbtCompound();
                this.saveSelfNbt(nbtCompound);
                ItemStack stack = player.getStackInHand(hand);
                stack.setNbt(nbtCompound);
                Text name = Text.of("§r§d" + Text.translatable(this.getType().getTranslationKey()).getString());
                if (this.hasCustomName()) {
                    name = Text.of("§r§d" + Objects.requireNonNull(this.getCustomName()).asTruncatedString(100));
                }
                player.getStackInHand(hand).setNbt(nbtCompound);
                player.getStackInHand(hand).setCustomName(name);
                this.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            }
            cir.setReturnValue(ActionResult.CONSUME_PARTIAL);
            cir.cancel();
        }
    }
}
