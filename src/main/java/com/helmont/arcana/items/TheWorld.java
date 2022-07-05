package com.helmont.arcana.items;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class TheWorld extends Item {
    public TheWorld(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(Text.translatable("item.arcproj.the_world.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Direction direction = user.getMovementDirection();

        Direction dir2 = Direction.UP;
        switch (direction) {
            case NORTH -> dir2 = Direction.WEST;
            case EAST -> dir2 = Direction.NORTH;
            case SOUTH -> dir2 = Direction.EAST;
            case WEST -> dir2 = Direction.SOUTH;
        }

        BlockPos blockPos = user.getBlockPos().offset(dir2, 5);

        for (int i = 0; i < 10; i++) {
            blockPos = blockPos.offset(direction, 1);

            if (i != 0) {
                blockPos = blockPos.down(9);
                blockPos = blockPos.offset(dir2, 9);
            }
            for (int j = 0; j < 10; j++) {
                if (j != 0) {
                    blockPos = blockPos.offset(dir2, 9);
                    blockPos = blockPos.up(1);
                }
                for (int k = 0; k < 10; k++) {
                    if (k != 0) {
                        blockPos = blockPos.offset(dir2.getOpposite(), 1);
                    }

                    String blockName = NbtHelper.fromBlockState(world.getBlockState(blockPos)).getString("Name");
                    if (Objects.equals(blockName, "minecraft:stone")
                            || Objects.equals(blockName, "minecraft:granite")
                            || Objects.equals(blockName, "minecraft:andesite")
                            || Objects.equals(blockName, "minecraft:diorite")
                            || Objects.equals(blockName, "minecraft:gravel")
                            || Objects.equals(blockName, "minecraft:sand")
                            || Objects.equals(blockName, "minecraft:dirt")
                            || Objects.equals(blockName, "minecraft:deepslate")
                            || Objects.equals(blockName, "minecraft:tuff")
                            || Objects.equals(blockName, "minecraft:netherrack")) {
                        world.breakBlock(blockPos, true, user);
                    }
                }
            }
        }


        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
