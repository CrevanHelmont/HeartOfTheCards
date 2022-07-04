package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.List;
import java.util.Objects;

public class TheDevil extends Item {
    public TheDevil(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_devil.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {

            ServerPlayerEntity player = (ServerPlayerEntity) user;
            ServerWorld serverWorld = player.getWorld();

            Registry<Structure> registry = serverWorld.getRegistryManager().get(Registry.STRUCTURE_KEY);
            RegistryKey<Structure> registryKey = RegistryKey.of(Registry.STRUCTURE_KEY, Registry.STRUCTURE_TYPE.getId(StructureType.FORTRESS));

            if (registry.getEntry(registryKey).isPresent()) {
                try {
                    RegistryEntryList<Structure> registryEntryList = RegistryEntryList.of(registry.getEntry(registryKey).get());
                    BlockPos structurePos = Objects.requireNonNull(serverWorld.getChunkManager().getChunkGenerator().locateStructure(serverWorld, registryEntryList, user.getBlockPos(), 1000, false)).getFirst();
                    System.out.println(structurePos);
                    user.sendMessage(Text.literal("The location of the nearest Nether Fortress is"
                    + " [x=" + structurePos.getX() + ", ~, z=" + structurePos.getZ() + "]").formatted(Formatting.RED)/*, Util.NIL_UUID*/);
                    user.getStackInHand(hand).decrement(1);
                    return TypedActionResult.consume(user.getStackInHand(hand));
                } catch (NullPointerException e) {
                    return TypedActionResult.fail(user.getStackInHand(hand));
                }
            }
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
