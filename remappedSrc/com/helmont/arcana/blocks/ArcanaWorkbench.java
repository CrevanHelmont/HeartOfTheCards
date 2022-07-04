package com.helmont.arcana.blocks;

import com.helmont.arcana.screenhandlers.ArcanaWorkbenchScreenHandler;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

public class ArcanaWorkbench extends Block {
    public static final Text TITLE = new TranslatableText("container.arcana_workbench");
    public ArcanaWorkbench(Settings settings) {
        super(settings);
    }

    public static final Block[] blocks = new Block[]{Blocks.CANDLE, Blocks.WHITE_CANDLE, Blocks.ORANGE_CANDLE, Blocks.MAGENTA_CANDLE,
            Blocks.LIGHT_BLUE_CANDLE, Blocks.YELLOW_CANDLE, Blocks.LIME_CANDLE, Blocks.PINK_CANDLE, Blocks.GRAY_CANDLE,
            Blocks.LIGHT_GRAY_CANDLE, Blocks.CYAN_CANDLE, Blocks.PURPLE_CANDLE, Blocks.BLUE_CANDLE, Blocks.BROWN_CANDLE,
            Blocks.GREEN_CANDLE, Blocks.RED_CANDLE, Blocks.BLACK_CANDLE};

    public Boolean checkForCandles(World world, BlockPos pos) {

        int candleCount = 0;

        for (Block c : blocks) {
            if (world.getBlockState(pos.east(1)).isOf(c) && world.getBlockState(pos.east(1)).get(CandleBlock.LIT)) {
                candleCount += 1;
            }
            if (world.getBlockState(pos.west(1)).isOf(c) && world.getBlockState(pos.west(1)).get(CandleBlock.LIT)) {
                candleCount += 1;
            }
            if (world.getBlockState(pos.north(1)).isOf(c) && world.getBlockState(pos.north(1)).get(CandleBlock.LIT)) {
                candleCount += 1;
            }
            if (world.getBlockState(pos.south(1)).isOf(c) && world.getBlockState(pos.south(1)).get(CandleBlock.LIT)) {
                candleCount += 1;
            }
        }

        return candleCount == 4;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (checkForCandles(world, pos)) {
            if(world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
                player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 3));
                return ActionResult.CONSUME;
            }
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
            return new ArcanaWorkbenchScreenHandler(i, playerInventory);
        }, TITLE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (checkForCandles(world, pos)) {
            int npx = random.nextInt(0, 2);
            int npy = random.nextInt(0, 2);
            int npz = random.nextInt(0, 2);
            if (npx == 0) {
                npx = 1;
            } else {
                npx = -1;
            }
            if (npy == 0) {
                npy = 1;
            } else {
                npy = -1;
            }
            if (npz == 0) {
                npz = 1;
            } else {
                npz = -1;
            }
            world.addParticle(ParticleTypes.DRAGON_BREATH, (double) pos.getX() + 0.5, (double) pos.getY() + 1.0,
                    (double) pos.getZ() + 0.5, npx * 0.1, npy * 0.1, npz * 0.1);
        }
    }
}
