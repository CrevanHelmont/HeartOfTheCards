package com.helmont.arcana.items;

import com.helmont.arcana.util.AllItems;
import com.helmont.arcana.util.MiscUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.BlockDataObject;
import net.minecraft.data.dev.NbtProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.scoreboard.*;
import net.minecraft.server.command.SetBlockCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;

public class WheelOfFortune extends Item {
    public WheelOfFortune(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.wheel_of_fortune.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Random rand = new Random();
        int num = rand.nextInt(19, 20);

        if (!world.isClient) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            ServerWorld serverWorld = player.getWorld();

            switch (num) {
                case 0:

                    //Emelds, Dimind, Iern, Glod
                    user.getStackInHand(hand).decrement(1);
                    int count = rand.nextInt(1, 4);
                    Item[] items = new Item[]{Items.EMERALD, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT};

                    MiscUtil.spawnItemAtPlayer(player, MiscUtil.getRandom(rand, items), count);

                    break;

                case 1:

                    //Death

                    user.getStackInHand(hand).decrement(1);
                    player.kill();

                    break;

                case 2:

                    //Rotten Flesh
                    user.getStackInHand(hand).decrement(1);
                    MiscUtil.spawnItemAtPlayer(player, Items.ROTTEN_FLESH, 1);

                    break;

                case 3:

                    //Add XP
                    user.getStackInHand(hand).decrement(1);
                    int levels = rand.nextInt(5, 11);
                    player.addExperienceLevels(levels);

                    break;
                case 4:

                    //Wither Skeleton Skull
                    user.getStackInHand(hand).decrement(1);
                    count = rand.nextInt(1, 3);
                    MiscUtil.spawnItemAtPlayer(player, Items.WITHER_SKELETON_SKULL, count);

                    break;

                case 5:

                    //Give head
                    user.getStackInHand(hand).decrement(1);
                    ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD, 1);
                    NbtCompound skullOwner = new NbtCompound();
                    skullOwner.putString("SkullOwner", /*player.getName().asString()*/ "Gungurt");
                    playerHead.setNbt(skullOwner);
                    MiscUtil.spawnItemAtPlayer(player, playerHead);

                    break;

                case 7:

                    //Give Crystal Ball

                    break;

                case 8:

                    //Bounty

                    user.getStackInHand(hand).decrement(1);
                    ServerScoreboard scoreboard = Objects.requireNonNull(player.getServer()).getScoreboard();
                    Text text = Texts.toText(() -> "Bounty");

                    if (!scoreboard.containsObjective("arcproj_bounty")) {
                        scoreboard.addObjective("arcproj_bounty", ScoreboardCriterion.DUMMY, text,
                                ScoreboardCriterion.RenderType.INTEGER);
                    }

                    ScoreboardObjective bounty = scoreboard.getObjective("arcproj_bounty");
                    ScoreboardPlayerScore playerScore = scoreboard.getPlayerScore(player.getName().asTruncatedString(100), bounty);
                    playerScore.incrementScore();
                    scoreboard.updateScore(playerScore);

                    break;

                case 9:

                    //Falling iron, gold, emerald, diamond blocks
                    user.getStackInHand(hand).decrement(1);

                    BlockPos blockPos = new BlockPos(player.getBlockPos().getX(),
                            player.getBlockPos().getY() + 10,
                            player.getBlockPos().getZ());
                    FallingBlockEntity ironBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
                    FallingBlockEntity goldBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
                    FallingBlockEntity emeraldBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
                    FallingBlockEntity diamondBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
                    Hashtable<FallingBlockEntity, Block> blockHashtable = new Hashtable<>();
                    blockHashtable.put(ironBlock, Blocks.IRON_BLOCK);
                    blockHashtable.put(goldBlock, Blocks.GOLD_BLOCK);
                    blockHashtable.put(emeraldBlock, Blocks.EMERALD_BLOCK);
                    blockHashtable.put(diamondBlock, Blocks.DIAMOND_BLOCK);

                    FallingBlockEntity[] fallingBlockEntities = new FallingBlockEntity[]{ironBlock, goldBlock,
                            emeraldBlock, diamondBlock};

                    int m = 1;

                    for (FallingBlockEntity fallingBlock : fallingBlockEntities) {

                        NbtCompound nbtCompound = new NbtCompound();
                        nbtCompound.put("BlockState",
                                NbtHelper.fromBlockState(blockHashtable.get(fallingBlock).getDefaultState()));
                        nbtCompound.putInt("Time", 1);
                        fallingBlock.readNbt(nbtCompound);
                        fallingBlock.updatePosition(blockPos.getX() + 0.5, blockPos.getY() + 10 * m, blockPos.getZ() + 0.5);
                        serverWorld.spawnEntity(fallingBlock);
                        m += 1;
                    }

                    break;

                case 10:

                    //Diamond Tools
                    user.getStackInHand(hand).decrement(1);

                    Item[] tools = new Item[]{Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL,
                            Items.DIAMOND_SWORD, Items.DIAMOND_HOE};
                    for (Item tool : tools) {
                        MiscUtil.spawnItemAtPlayer(player, tool, 1);
                    }

                    break;

                case 11:

                    //Eyes of Ender & Pearls
                    user.getStackInHand(hand).decrement(1);

                    count = rand.nextInt(1, 4);
                    MiscUtil.spawnItemAtPlayer(player, Items.ENDER_EYE, count);
                    count = rand.nextInt(1, 7);
                    MiscUtil.spawnItemAtPlayer(player, Items.ENDER_PEARL, count);

                    break;

                case 12:

                    //Spawn Zombie Horses
                    user.getStackInHand(hand).decrement(1);
                    for (int i = 0; i <= 3; ++i) {
                        MiscUtil.summonEntityAtPlayer(player, EntityType.ZOMBIE, Items.IRON_SWORD, i);
                    }
                    world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                            SoundCategory.WEATHER, 1.0f, 1.0f);
                    break;

                case 13:

                    //TNT
                    user.getStackInHand(hand).decrement(1);
                    FallingBlockEntity tntBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
                    FallingBlockEntity redstoneBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);

                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.put("BlockState",
                            NbtHelper.fromBlockState(Blocks.REDSTONE_BLOCK.getDefaultState()));
                    nbtCompound.putInt("Time", 1);
                    redstoneBlock.readNbt(nbtCompound);
                    redstoneBlock.updatePosition(player.getX() + 0.5, player.getY() + 10, player.getZ() + 0.5);
                    serverWorld.spawnEntity(redstoneBlock);

                    nbtCompound.put("BlockState",
                            NbtHelper.fromBlockState(Blocks.TNT.getDefaultState()));
                    tntBlock.readNbt(nbtCompound);
                    tntBlock.updatePosition(player.getX() + 0.5, player.getY() + 20, player.getZ() + 0.5);
                    serverWorld.spawnEntity(tntBlock);

                    System.out.println(num);
                    break;

                case 14:

                    //Pet wolf
                    System.out.println(num);
                    WolfEntity wolf = new WolfEntity(EntityType.WOLF, world);
                    wolf.updatePosition(player.getX(), player.getY(), player.getZ());
                    serverWorld.spawnEntity(wolf);
                    for (int i = 0; i <= 8; ++i) {
                        ItemStack bone = new ItemStack(Items.BONE, 1);
                        ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), bone);
                        switch (i) {
                            case 0 -> {
                                item.updatePosition(player.getX(), player.getY(), player.getZ());
                                serverWorld.spawnEntity(item);
                            }
                            case 1 -> {
                                item.updatePosition(player.getX() + 1, player.getY(), player.getZ());
                                serverWorld.spawnEntity(item);
                            }
                            case 2 -> {
                                item.updatePosition(player.getX() + 1, player.getY(), player.getZ() + 1);
                                serverWorld.spawnEntity(item);
                            }
                            case 3 -> {
                                item.updatePosition(player.getX(), player.getY(), player.getZ() + 1);
                                serverWorld.spawnEntity(item);
                            }
                            case 4 -> {
                                item.updatePosition(player.getX() - 1, player.getY(), player.getZ() + 1);
                                serverWorld.spawnEntity(item);
                            }
                            case 5 -> {
                                item.updatePosition(player.getX() - 1, player.getY(), player.getZ());
                                serverWorld.spawnEntity(item);
                            }
                            case 6 -> {
                                item.updatePosition(player.getX() - 1, player.getY(), player.getZ() - 1);
                                serverWorld.spawnEntity(item);
                            }
                            case 7 -> {
                                item.updatePosition(player.getX(), player.getY(), player.getZ() - 1);
                                serverWorld.spawnEntity(item);
                            }
                            case 8 -> {
                                item.updatePosition(player.getX() + 1, player.getY(), player.getZ() - 1);
                                serverWorld.spawnEntity(item);
                            }
                        }
                    }
                    break;

                case 15:

                    //Harry Houdini

                    //Bottom
                    world.setBlockState(player.getBlockPos().north(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().east(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().west(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().east(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().west(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().south(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().east(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().west(), Blocks.OBSIDIAN.getDefaultState());

                    world.setBlockState(player.getBlockPos().north().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().east().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().west().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().east().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().west().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().east().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().west().down(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().down(), Blocks.OBSIDIAN.getDefaultState());

                    //Top
                    world.setBlockState(player.getBlockPos().north().up(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().east().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().west().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().east().up(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().west().up(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().up(), Blocks.GLASS.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().east().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().west().up(), Blocks.OBSIDIAN.getDefaultState());

                    world.setBlockState(player.getBlockPos().north().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().east().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().north().west().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().east().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().west().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().east().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().south().west().up().up(), Blocks.OBSIDIAN.getDefaultState());
                    world.setBlockState(player.getBlockPos().up().up(), Blocks.OBSIDIAN.getDefaultState());

                    world.setBlockState(player.getBlockPos().up(), Blocks.WATER.getDefaultState());

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 1000000, 2));

                    break;

                case 16:

                    //Insta-kill Sword

                    ItemStack instaSword = new ItemStack(Items.GOLDEN_SWORD, 1);

                    NbtCompound swordName = new NbtCompound();

                    NbtCompound name2 = new NbtCompound();
                    name2.putString("Name", "[{\"text\":\"Insta\", \"color\":\"gold\", \"bold\":\"true\", \"italic\":\"false\"}" +
                            ", {\"text\":\"Kill \", \"color\":\"red\", \"bold\":\"true\", \"italic\":\"false\"}, " +
                            "{\"text\":\"Sword\", \"color\":\"gold\", \"bold\":\"true\", \"italic\":\"false\"}]");

                    swordName.put("display", name2);
                    instaSword.setNbt(swordName);
                    instaSword.setRepairCost(2147483647);
                    instaSword.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            new EntityAttributeModifier("attack", 1000.0, EntityAttributeModifier.Operation.ADDITION),
                            EquipmentSlot.MAINHAND);
                    instaSword.addEnchantment(Enchantments.FIRE_ASPECT, 1);
                    instaSword.addHideFlag(ItemStack.TooltipSection.ENCHANTMENTS);
                    instaSword.setDamage(32);
                    serverWorld.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), instaSword));

                    break;

                case 17:

                    //Cake

                    Direction playerDirection = player.getHorizontalFacing();
                    BlockPos playerPos = player.getBlockPos();
                    serverWorld.setBlockState(playerPos.offset(playerDirection, 3),
                            CandleCakeBlock.getCandleCakeFromCandle(Blocks.RED_CANDLE).with(CandleCakeBlock.LIT, true));

                    NbtCompound firework = new NbtCompound();
                    NbtCompound fireworksItem = new NbtCompound();
                    NbtCompound tag = new NbtCompound();
                    NbtCompound fireworks = new NbtCompound();
                    NbtList explosions = new NbtList();
                    NbtCompound explosion1 = new NbtCompound();

                    NbtIntArray colors = new NbtIntArray(new int[]{11743532});
                    NbtIntArray fadeColors = new NbtIntArray(new int[]{15790320});

                    explosion1.putInt("Type", 2);
                    explosion1.putInt("Flicker", 1);
                    explosion1.putInt("Trail", 1);
                    explosion1.put("Colors", colors);
                    explosion1.put("FadeColors", fadeColors);

                    explosions.add(explosion1);

                    fireworks.putInt("Flight", 2);
                    fireworks.put("Explosions", explosions);

                    tag.put("Fireworks", fireworks);

                    fireworksItem.putString("id", "firework_rocket");
                    fireworksItem.putInt("Count", 1);
                    fireworksItem.put("tag", tag);

                    firework.putInt("LifeTime", 15);
                    firework.put("FireworksItem", fireworksItem);

                    FireworkRocketEntity fireworkRocket = new FireworkRocketEntity(EntityType.FIREWORK_ROCKET, world);
                    fireworkRocket.readNbt(firework);
                    BlockPos updatedPos = playerPos.offset(playerDirection, 3);
                    fireworkRocket.updatePosition(updatedPos.getX() + 0.5, updatedPos.up(2).getY(), updatedPos.getZ() + 0.5);
                    serverWorld.spawnEntity(fireworkRocket);

                    break;

                case 18:

                    //Spawn bird and jukebox
                    System.out.println(num);
                    playerDirection = player.getHorizontalFacing();

                    Item[] discs = new Item[]{Items.MUSIC_DISC_11, Items.MUSIC_DISC_13, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CAT,
                            Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI,
                            Items.MUSIC_DISC_OTHERSIDE, Items.MUSIC_DISC_PIGSTEP, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD,
                            Items.MUSIC_DISC_WAIT, Items.MUSIC_DISC_WARD};
                    ItemEntity musicEntity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(),
                            new ItemStack(MiscUtil.getRandom(rand, discs), 1));
                    serverWorld.spawnEntity(musicEntity);
                    serverWorld.setBlockState(player.getBlockPos().offset(playerDirection, 3),
                            Blocks.JUKEBOX.getDefaultState());
                    ParrotEntity parrotEntity1 = new ParrotEntity(EntityType.PARROT, world);
                    ParrotEntity parrotEntity2 = new ParrotEntity(EntityType.PARROT, world);

                    parrotEntity1.setOwner(player);
                    parrotEntity2.setOwner(player);

                    BlockPos looking = player.getBlockPos().offset(playerDirection, 3);

                    Direction p1;
                    Direction p2;
                    float yaw = 0f;

                    switch (playerDirection) {
                        case NORTH -> {
                            p1 = Direction.EAST;
                            p2 = Direction.WEST;
                            yaw = -.0F;
                        }
                        case EAST -> {
                            p1 = Direction.NORTH;
                            p2 = Direction.SOUTH;
                            yaw = 90.0F;
                        }
                        case SOUTH -> {
                            p1 = Direction.EAST;
                            p2 = Direction.WEST;
                            yaw = -180.0F;
                        }
                        case WEST -> {
                            p1 = Direction.NORTH;
                            p2 = Direction.SOUTH;
                            yaw = -90.0F;
                        }
                        default -> p1 = p2 = null;
                    }

                    NbtCompound parrotName = new NbtCompound();
                    parrotName.putString("CustomName",
                            "[{\"text\":\"P\", \"color\":\"red\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"a\", \"color\":\"gold\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"r\", \"color\":\"yellow\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"t\", \"color\":\"green\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"y \", \"color\":\"blue\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"P\", \"color\":\"light_purple\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"a\", \"color\":\"red\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"r\", \"color\":\"gold\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"r\", \"color\":\"yellow\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"o\", \"color\":\"green\", \"bold\":\"true\", \"italic\":\"false\"}," +
                                    "{\"text\":\"t\", \"color\":\"blue\", \"bold\":\"true\", \"italic\":\"false\"}]");

                    parrotEntity1.readNbt(parrotName);
                    parrotEntity2.readNbt(parrotName);

                    BlockPos parrotPos1 = looking.offset(p1, 2);
                    BlockPos parrotPos2 = looking.offset(p2, 2);

                    parrotEntity1.updatePosition(parrotPos1.getX() + 0.5, parrotPos1.getY(), parrotPos1.getZ() + 0.5);
                    parrotEntity2.updatePosition(parrotPos2.getX() + 0.5, parrotPos2.getY(), parrotPos2.getZ() + 0.5);

                    parrotEntity1.setHeadYaw(yaw);
                    parrotEntity2.setHeadYaw(yaw);

                    parrotEntity1.setOnGround(true);
                    parrotEntity2.setOnGround(true);

                    parrotEntity1.setSitting(true);
                    parrotEntity2.setSitting(true);

                    serverWorld.spawnEntity(parrotEntity1);
                    serverWorld.spawnEntity(parrotEntity2);

                    break;

                case 19:

                    Item randomItem = MiscUtil.getRandom(new Random(),AllItems.items);
                    System.out.println(randomItem.toString());
                    int goodInt;
                    int badInt;
                    String option;

                    String option1 = "/execute as @p[scores={arcproj_good=0,arcproj_bad=1}] run tp @s ~ ~3000 ~";
                    String option2 = "/execute as @p[scores={arcproj_good=1,arcproj_bad=0}] run give @s " + randomItem.toString();

                    int randInt = rand.nextInt(0, 2);
                    if (randInt == 0) {
                        option = option1;
                        goodInt = 0;
                        badInt = 1;
                    } else {
                        option = option2;
                        goodInt = 1;
                        badInt = 0;
                    }

                    playerDirection = player.getMovementDirection();
                    playerPos = player.getBlockPos();

                    NbtCompound sign = new NbtCompound();


                    SignBlockEntity signBlockEntity = new SignBlockEntity(player.getBlockPos().offset(playerDirection, 3), Blocks.BIRCH_SIGN.getDefaultState());


                    sign.putString("Text3",
                            "{\"text\":\"for a surprise!\"," +
                                    "\"clickEvent\":{\"action\":\"run_command\"," +
                                    "\"value\":\"/execute as @p[scores={arcproj_good=0,arcproj_bad=1}] run scoreboard players set @s arcproj_bad 0\"}}");
                    sign.putString("Text4",
                            "{\"text\":\"asdfghjkl\"," +
                                    "\"obfuscated\":true," +
                                    "\"clickEvent\":{\"action\":\"run_command\"," +
                                    "\"value\":\"/execute as @p[scores={arcproj_good=1,arcproj_bad=0}] run scoreboard players set @s arcproj_good 0\"}}");
                    sign.putString("Text1",
                            "{\"text\":\"asdfghjkl\"," +
                                    "\"obfuscated\":true," +
                                    "\"clickEvent\":{\"action\":\"run_command\"," +
                                    "\"value\":\"/setblock " + signBlockEntity.getPos().getX() + " " +
                                    signBlockEntity.getPos().getY() + " " +
                                    signBlockEntity.getPos().getZ() + " " + "air replace\"}}");
                    sign.putString("Text2",
                            "{\"text\":\"Click Here\"," +
                                    "\"color\":\"gold\"," +
                                    "\"bold\":true," +
                                    "\"underlined\":true," +
                                    "\"clickEvent\":{\"action\":\"run_command\"," +
                                    "\"value\":\""+ option +"\"}}");

                    signBlockEntity.readNbt(sign);
                    signBlockEntity.setWorld(serverWorld);

                    serverWorld.addBlockEntity(signBlockEntity);

                    BlockRotation rotation = BlockRotation.NONE;

                    switch (playerDirection) {
                        case NORTH -> rotation = BlockRotation.NONE;
                        case EAST -> rotation = BlockRotation.CLOCKWISE_90;
                        case SOUTH -> rotation = BlockRotation.CLOCKWISE_180;
                        case WEST -> rotation = BlockRotation.COUNTERCLOCKWISE_90;
                    }

                    serverWorld.setBlockState(signBlockEntity.getPos(), signBlockEntity.getCachedState().rotate(rotation));
                    BlockEntity blockEntity = serverWorld.getBlockEntity(signBlockEntity.getPos());

                    assert blockEntity != null;
                    blockEntity.readNbt(sign);

                    System.out.println(blockEntity.createNbt());

                    user.getStackInHand(hand).decrement(1);
                    ServerScoreboard scoreboard2 = Objects.requireNonNull(player.getServer()).getScoreboard();
                    Text text2 = Texts.toText(() -> "Good");
                    Text text3 = Texts.toText(() -> "Bad");

                    if (!scoreboard2.containsObjective("arcproj_good")) {
                        scoreboard2.addObjective("arcproj_good", ScoreboardCriterion.DUMMY, text2,
                                ScoreboardCriterion.RenderType.INTEGER);
                    }

                    if (!scoreboard2.containsObjective("arcproj_bad")) {
                        scoreboard2.addObjective("arcproj_bad", ScoreboardCriterion.DUMMY, text3,
                                ScoreboardCriterion.RenderType.INTEGER);
                    }

                    ScoreboardObjective good = scoreboard2.getObjective("arcproj_good");
                    ScoreboardObjective bad = scoreboard2.getObjective("arcproj_bad");
                    ScoreboardPlayerScore goodScore = scoreboard2.getPlayerScore(player.getName().asTruncatedString(50), good);
                    ScoreboardPlayerScore badScore = scoreboard2.getPlayerScore(player.getName().asTruncatedString(50), bad);
                    goodScore.setScore(goodInt);
                    badScore.setScore(badInt);
                    scoreboard2.updateScore(goodScore);
                    scoreboard2.updateScore(badScore);
            }
        } else {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) user;
            switch (num) {
                case 1:
                    user.sendMessage(Text.literal("Better luck next time :)").formatted(Formatting.RED));
                    break;
                case 6:
                    clientPlayerEntity.addVelocity(0.0, 10.0, 0.0);
                    break;
            }
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
