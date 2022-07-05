package com.helmont.arcana.util;
import net.fabricmc.fabric.api.util.NbtType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.data.dev.NbtProvider;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.scanner.NbtScanner;
import net.minecraft.nbt.visitor.NbtElementVisitor;
import net.minecraft.network.Packet;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;

public class MiscUtil {

    public static final EntityType<?>[] entityTypes = new EntityType[]{EntityType.AXOLOTL, EntityType.BEE,
            EntityType.CHICKEN, EntityType.COW, EntityType.FOX, EntityType.GOAT, EntityType.HOGLIN,
            EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.RABBIT,
            EntityType.SHEEP, EntityType.TURTLE};

    public static boolean isAnimal(LivingEntity entity) {
        try {
            AnimalEntity animal = (AnimalEntity) entity;
            System.out.println("AnimalEntity!");
            return true;
        } catch (ClassCastException e) {
            System.out.println("Not an AnimalEntity!");
            return false;
        }
    }

    public static boolean isEligibleToBaby(ServerWorld world, PassiveEntity passive) {
        PassiveEntity passiveEntity = passive.createChild(world, passive);
        if (passiveEntity != null) {
            passiveEntity.setPosition(0.0, -1500.0, 0.0);
            passiveEntity.kill();
            return true;
        }
        return false;
    }

    public static void spawnItemAtPlayer(ServerPlayerEntity player, Item item, int count) {
        ServerWorld serverWorld = player.getWorld();
        ItemStack itemStack = new ItemStack(item, count);
        ItemEntity itemEntity = new ItemEntity(serverWorld, player.getX(), player.getY(), player.getZ(),
                itemStack);
        serverWorld.spawnEntity(itemEntity);
    }

    public static void spawnItemAtPlayer(ServerPlayerEntity player, ItemStack itemStack) {
        ServerWorld serverWorld = player.getWorld();
        ItemEntity itemEntity = new ItemEntity(serverWorld, player.getX(), player.getY(), player.getZ(),
                itemStack);
        serverWorld.spawnEntity(itemEntity);
    }

    public static <T> T getRandom(Random rand, T[] items) {
        return items[rand.nextInt(items.length)];
    }

    public static void summonEntityAtPlayer(ServerPlayerEntity player, @Nullable EntityType<?> passenger,
                                            Item item, int i) {

        ServerWorld serverWorld = player.getWorld();

        NbtCompound nbtCompound = new NbtCompound();
        NbtCompound nbtCompound1 = new NbtCompound();
        NbtCompound helmCompound = new NbtCompound();
        NbtCompound chestCompound = new NbtCompound();
        NbtCompound legsCompound = new NbtCompound();
        NbtCompound bootsCompound = new NbtCompound();
        NbtCompound handCompound = new NbtCompound();
        NbtCompound emptyCompound = new NbtCompound();
        NbtList handList = new NbtList();
        NbtList armorList = new NbtList();

        handCompound.putInt("Count", 1);
        handCompound.putString("id", item.toString());
        handList.add(handCompound);
        handList.add(emptyCompound);

        bootsCompound.putInt("Count", 1);
        bootsCompound.putString("id", "iron_boots");
        armorList.add(bootsCompound);

        legsCompound.putInt("Count", 1);
        legsCompound.putString("id", "iron_leggings");
        armorList.add(legsCompound);

        chestCompound.putInt("Count", 1);
        chestCompound.putString("id", "iron_chestplate");
        armorList.add(chestCompound);

        helmCompound.putInt("Count", 1);
        helmCompound.putString("id", "iron_helmet");
        armorList.add(helmCompound);

        nbtCompound1.putString("id", passenger.getName().getString().toLowerCase().replace(" ", "_"));
        nbtCompound1.put("ArmorItems", armorList);
        nbtCompound1.put("HandItems", handList);
        NbtList nbtList = new NbtList();
        nbtList.add(nbtCompound1);
        ZombieHorseEntity zombieHorseEntity = new ZombieHorseEntity(EntityType.ZOMBIE_HORSE, serverWorld);
        zombieHorseEntity.saveSelfNbt(nbtCompound);
        nbtCompound.put("Passengers", nbtList);
        nbtCompound.putInt("Tame", 1);
        Entity entity = switch (i) {
            case 0 -> EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(player.getBlockPos().north(2), entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            case 1 -> EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(player.getBlockPos().east(2), entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            case 2 -> EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(player.getBlockPos().south(2), entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            case 3 -> EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(player.getBlockPos().west(2), entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            default -> null;
        };



        serverWorld.spawnNewEntityAndPassengers(entity);
    }

    public static HostileEntity[] addHostileEntity(int l, HostileEntity[] arr, HostileEntity entity)
    {
        int i;

        HostileEntity[] newarr = new HostileEntity[l + 1];

        for (i = 0; i < l; i++)
            newarr[i] = arr[i];

        newarr[l] = entity;

        return newarr;
    }

    public static double[] addX(int n, double[] arr, double x)
    {
        int i;

        double[] newarr = new double[n + 1];

        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

    public static int calcExpFromLvl(int lvl) {
        if (0 < lvl && lvl < 17) {
            return (int) (Math.pow(lvl, 2) + 6 * lvl);
        } else if (lvl >= 17 && lvl < 32) {
            return (int) (2.5 * Math.pow(lvl, 2) - 40.5 * lvl + 360);
        } else if (lvl >= 32) {
            return (int) (4.5 * Math.pow(lvl, 2) - 162.5 * lvl + 2220);
        }
        return 0;
    }

    public static int calcExpFromLvl(int lvl, int fromLvl) {
        return calcExpFromLvl(lvl) - calcExpFromLvl(fromLvl);
    }

    public static int calcLvlFromExp(int lvl) {
        if (lvl < 17) {
            return (int) (Math.sqrt(lvl + 9) - 3);
        } else if (lvl < 32) {
            return (int) (8.1 + Math.sqrt(0.4 * (lvl - 195.975)));
        } else {
            return (int) (18.056 + Math.sqrt(0.222 * (lvl - 752.986)));
        }
    }
}
