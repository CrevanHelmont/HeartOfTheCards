package com.helmont.arcana.items;

import com.helmont.arcana.util.MiscUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class TheHierophant extends Item {
    public TheHierophant(Settings settings) {
        super(settings);
    }

    MutableText experience = Text.literal("");
    MutableText levels = Text.literal("");
    MutableText mode = Text.literal("");

    String[] modes = new String[]{"Drain", "Give"};


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(this.experience.formatted(Formatting.GREEN));
        tooltip.add(this.mode.formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.arcproj.the_hierophant.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        NbtCompound hierophantNbt;

        if (!stack.hasNbt()) {
            hierophantNbt = new NbtCompound();
            hierophantNbt.putInt("Mode", 0);
            hierophantNbt.putInt("TotalExp", 0);
            stack.setNbt(hierophantNbt);
        }

        hierophantNbt = stack.getNbt();

        int cardExp = hierophantNbt.getInt("TotalExp");
        int currentMode = hierophantNbt.getInt("Mode");

        if (currentMode == 0 && entity instanceof PlayerEntity player && player.totalExperience >= 1 && cardExp < 10000) {

            if (player.totalExperience >= 5) {
                hierophantNbt.putInt("TotalExp", cardExp + 5);
                player.addExperience(-5);
            } else {
                hierophantNbt.putInt("TotalExp", cardExp + 1);
                player.addExperience(-1);
            }
        } else if (currentMode == 1 && entity instanceof PlayerEntity player && cardExp > 0) {

            if (cardExp >= 5) {
                hierophantNbt.putInt("TotalExp", cardExp - 5);
                player.addExperience(5);
            } else {
                hierophantNbt.putInt("TotalExp", cardExp - 1);
                player.addExperience(1);
            }
        }

        stack.setNbt(hierophantNbt);

        this.experience = Text.literal("Total Experience: " + hierophantNbt.getInt("TotalExp") + "/10000");
        this.mode = Text.literal("Mode: ").append(Text.literal(modes[hierophantNbt.getInt("Mode")]).formatted(Formatting.BLUE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        NbtCompound hierophantNbt = user.getStackInHand(hand).getNbt();

        int currentMode = hierophantNbt.getInt("Mode");

        if (currentMode == 0) {
            hierophantNbt.putInt("Mode", 1);
            if (world.isClient) {
                user.sendMessage(Text.literal("Mode: ").formatted(Formatting.GREEN).append(Text.literal("Give").formatted(Formatting.BLUE)), true);
            }
        } else {
            hierophantNbt.putInt("Mode", 0);
            if (world.isClient) {
                user.sendMessage(Text.literal("Mode: ").formatted(Formatting.GREEN).append(Text.literal("Drain").formatted(Formatting.BLUE)), true);
            }
        }

        user.getStackInHand(hand).setNbt(hierophantNbt);

        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
