package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class TheTower extends Item {
    public TheTower(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_tower.tooltip").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item.arcproj.the_tower.tooltip2").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            if (user.isSneaking()) {
                if (user.experienceLevel >= 5) {
                    user.sendMessage(Text.literal("Your respawn point is now set").formatted(Formatting.WHITE));
                } else {
                    user.sendMessage(Text.literal("You do not have enough levels to do that!").formatted(Formatting.WHITE));
                }
            }
        }

        if (!world.isClient) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
            ServerWorld serverWorld = serverPlayer.server.getWorld(serverPlayer.getSpawnPointDimension());
            if (!user.isSneaking()) {
                System.out.println(serverPlayer.getSpawnPointPosition());
                BlockPos spawnpoint = serverPlayer.getSpawnPointPosition();
                if (spawnpoint != null) {
                    BlockPos finalSpawnpoint = spawnpoint;
                    Optional<Vec3d> optionalSpawnVec = PlayerEntity.findRespawnPosition(serverWorld, spawnpoint, serverPlayer.getSpawnAngle(), true, false);
                    if (optionalSpawnVec.equals(Optional.empty())){
                        user.sendMessage(Text.literal("Your spawnpoint has been obstructed!").formatted(Formatting.RED));
                    }
                    optionalSpawnVec.ifPresent(spawnVec -> {
                        System.out.println(optionalSpawnVec);
                        serverPlayer.teleport(serverWorld, spawnVec.getX(), spawnVec.getY(), spawnVec.getZ(), serverPlayer.getSpawnAngle(), 0.5F);
                        serverWorld.playSound(null, finalSpawnpoint, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.4f, 1f);
                        user.sendMessage(Text.literal("Teleporting to your respawn point...").formatted(Formatting.WHITE));
                        user.getStackInHand(hand).decrement(1);
                    });
                } else {
                    assert serverWorld != null;
                    spawnpoint = serverWorld.getSpawnPos();
                    serverPlayer.teleport(serverWorld, spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ(), serverPlayer.getSpawnAngle(), 0.5F);
                    while (!serverWorld.isSpaceEmpty(serverPlayer)) {
                        serverPlayer.teleport(serverPlayer.getX(), serverPlayer.getY() + 1.0D, serverPlayer.getZ());
                    }
                    serverWorld.playSound(null, spawnpoint, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.4f, 1f);
                    user.sendMessage(Text.literal("Teleporting to your respawn point...").formatted(Formatting.WHITE));
                    user.getStackInHand(hand).decrement(1);
                }
            }
            else {
                if (user.experienceLevel >= 5) {
                    RegistryKey<World> registryKey = user.world.getRegistryKey();
                    if (World.OVERWORLD.equals(registryKey)) {
                        RegistryKey<World> currentWorld = World.OVERWORLD;
                        serverPlayer.setSpawnPoint(currentWorld, serverPlayer.getBlockPos(), serverPlayer.headYaw, true, false);
                    } else if (World.NETHER.equals(registryKey)) {
                        RegistryKey<World> currentWorld = World.NETHER;
                        serverPlayer.setSpawnPoint(currentWorld, serverPlayer.getBlockPos(), serverPlayer.headYaw, true, false);
                    } else if (World.END.equals(registryKey)) {
                        user.sendMessage(Text.literal("You cannot set your spawnpoint in the End!").formatted(Formatting.WHITE));
                    }
                    serverPlayer.setExperienceLevel(user.experienceLevel - 5);
                }
            }
        }

        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
