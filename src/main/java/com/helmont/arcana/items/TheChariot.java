package com.helmont.arcana.items;

import com.helmont.arcana.util.MiscUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TheChariot extends Item {
    public TheChariot(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_chariot.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        System.out.println(stack.getItem().toString());
        System.out.println(this.toString());
        if (!user.getWorld().isClient() && !stack.hasNbt()) {
            NbtCompound nbtCompound = new NbtCompound();
            entity.saveSelfNbt(nbtCompound);
            stack.setNbt(nbtCompound);
            Text name = Text.of("§r§d" + Text.translatable(entity.getType().getTranslationKey()).getString());
            if (entity.hasCustomName()) {
                name = Text.of("§r§d" + Objects.requireNonNull(entity.getCustomName()).asTruncatedString(100));
            }
            user.getStackInHand(hand).setNbt(nbtCompound);
            user.getStackInHand(hand).setCustomName(name);
            entity.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        PlayerEntity user = context.getPlayer();
        Hand hand = context.getHand();
        assert user != null;
        World world = user.getWorld();
        BlockPos blockPos = context.getBlockPos().offset(direction);

        if (user.getStackInHand(hand).getName() != this.getName()) {
            if (!world.isClient()) {

                NbtCompound entityNbt = user.getStackInHand(hand).getNbt();
                assert entityNbt != null;

                entityNbt.remove("display");

                Optional<Entity> entity = EntityType.getEntityFromNbt(entityNbt, world);
                if (entity.isPresent()) {
                    Entity entityGotten = entity.get();
                    ServerWorld serverWorld = (ServerWorld) world;
                    entityGotten.setPosition(Vec3d.ofCenter(blockPos));
                    serverWorld.spawnEntity(entityGotten);
                    user.getStackInHand(hand).setNbt(null);
                    user.getStackInHand(hand).decrement(1);
                    return ActionResult.CONSUME;
                }
            }
        }
        return ActionResult.FAIL;
    }
}
