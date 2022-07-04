package com.helmont.arcana.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.scoreboard.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {


    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo cI) {
        ServerScoreboard serverScoreboard = Objects.requireNonNull(this.getServer()).getScoreboard();
        ScoreboardObjective bounty = serverScoreboard.getObjective("arcproj_bounty");
        ScoreboardPlayerScore playerScore = serverScoreboard.getPlayerScore(this.getName().toString(), bounty);
        if (playerScore.getScore() >= 1) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2147483647, 1,
                    false, false, true));
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath()V")
    private void onOnDeath(DamageSource source, CallbackInfo cI) {

        ServerScoreboard serverScoreboard = Objects.requireNonNull(this.getServer()).getScoreboard();
        ScoreboardObjective bounty = serverScoreboard.getObjective("arcproj_bounty");
        ScoreboardPlayerScore playerScore = serverScoreboard.getPlayerScore(this.getName().asTruncatedString(100), bounty);
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        int count = playerScore.getScore();

        if (source.getAttacker() != null) {
            if (source.getAttacker().isPlayer() && count > 0) {
                PlayerEntity player = (PlayerEntity) source.getAttacker();
                ItemStack stack = new ItemStack(Items.DIAMOND, count);
                int occupiedInt = player.getInventory().getOccupiedSlotWithRoomForStack(stack);
                int slotStackInt = 0;
                if (occupiedInt != -1) {
                    slotStackInt = player.getInventory().getStack(occupiedInt).getCount();
                }
                if (player.getInventory().getEmptySlot() == -1 && occupiedInt == -1) {
                    ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(),
                            stack);
                    serverWorld.spawnEntity(itemEntity);

                } else if (count > 64 - slotStackInt) {
                    int difference = count - (64 - slotStackInt);
                    ItemStack giveStack = new ItemStack(Items.DIAMOND, 64 - slotStackInt);
                    ItemStack dropStack = new ItemStack(Items.DIAMOND, difference);
                    player.getInventory().insertStack(giveStack);
                    ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(),
                            dropStack);
                    serverWorld.spawnEntity(itemEntity);
                } else {
                    player.getInventory().insertStack(stack);
                }
            }
        }
        playerScore.setScore(0);
        serverScoreboard.updateScore(playerScore);
        //serverScoreboard.setObjectiveSlot(0, bounty);
    }
}
