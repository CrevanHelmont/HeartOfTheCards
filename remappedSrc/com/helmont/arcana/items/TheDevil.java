package com.helmont.arcana.items;

import java.io.PrintStream;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureSet;
import net.minecraft.tag.TagKey;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.StructureLocator;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.*;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class TheDevil extends Item {
    public TheDevil(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("item.arcproj.the_devil.tooltip").formatted(Formatting.GOLD));
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

            //Stream tags of Registry
            Registry<ConfiguredStructureFeature<?, ?>> registry = serverWorld.getRegistryManager().get(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY);
            System.out.println("Configured Structure Features:");
            registry.streamTags().forEach(System.out::println);

            //Fortress TagKey
            TagKey<ConfiguredStructureFeature<?, ?>> tagKey = TagKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, Registry.STRUCTURE_FEATURE.getId(StructureFeature.FORTRESS));

            //Outputs location of Fortress
            System.out.println(serverWorld.locateStructure(tagKey, player.getBlockPos(), 1000, false));

            //Outputs whether TagKey was found in Registry and Registry itself
            System.out.println(serverWorld.getServer().getRegistryManager().get(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY).getEntryList(tagKey));
            System.out.println(serverWorld.getRegistryManager().get(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY));

            //Outputs TagKey of Fortress
            System.out.println(tagKey);

            serverWorld.getRegistryManager().get(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY).forEach(s -> System.out.println(s.feature));
        }
        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
