package com.helmont.arcana.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WheelOfFortune extends Item {
    public WheelOfFortune(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("item.arcproj.wheel_of_fortune.tooltip").formatted(Formatting.GOLD));
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

            Random rand = new Random();
            int num = rand.nextInt(20);

            switch (num) {
                case 0:
                    System.out.println(num);
                    int count = rand.nextInt(1, 4);
                    user.getStackInHand(hand).decrement(1);
                    user.giveItemStack(new ItemStack(Items.EMERALD, count));
                    break;
                case 1:
                    System.out.println(num);
                    user.getStackInHand(hand).decrement(1);
                    user.sendSystemMessage(new LiteralText("Better luck next time :)").formatted(Formatting.RED), Util.NIL_UUID);
                    player.kill();
                    break;
                case 2:
                    System.out.println(num);
                    break;
                case 3:
                    System.out.println(num);
                    break;
                case 4:
                    System.out.println(num);
                    break;
                case 5:
                    System.out.println(num);
                    break;
                case 6:
                    System.out.println(num);
                    break;
                case 7:
                    System.out.println(num);
                    break;
                case 8:
                    System.out.println(num);
                    break;
                case 9:
                    System.out.println(num);
                    break;
                case 10:
                    System.out.println(num);
                    break;
                case 11:
                    System.out.println(num);
                    break;
                case 12:
                    System.out.println(num);
                    break;
                case 13:
                    System.out.println(num);
                    break;
                case 14:
                    System.out.println(num);
                    break;
                case 15:
                    System.out.println(num);
                    break;
                case 16:
                    System.out.println(num);
                    break;
                case 17:
                    System.out.println(num);
                    break;
                case 18:
                    System.out.println(num);
                    break;
                case 19:
                    System.out.println(num);
                    break;
            }
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
