package com.helmont.arcana.item_containers;

import com.helmont.arcana.Arcana;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class CardHolder extends Item {

    public CardHolder(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        user.setCurrentHand(hand);

        if(!world.isClient)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(Arcana.ARC_ID, user, buf -> {
                ItemStack stack = user.getStackInHand(hand);
                buf.writeItemStack(stack);
                buf.writeInt(hand == Hand.MAIN_HAND ? 0 : 1);
                buf.writeString(stack.getName().asString());
            });
        }

        return super.use(world, user, hand);
    }

    public static CardHolderInventory getInventory(ItemStack stack, Hand hand, PlayerEntity player)
    {
        if(!stack.hasNbt())
        {
            stack.setNbt(new NbtCompound());
        }

        if(!stack.getNbt().contains("arcproj"))
        {
            stack.getNbt().put("arcproj", new NbtCompound());
        }

        return new CardHolderInventory(stack.getNbt().getCompound("arcproj"), hand, player);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(new TranslatableText("item.arcproj.card_holder.tooltip").formatted(Formatting.GOLD));
    }
}
