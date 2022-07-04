package com.helmont.arcana.item_containers;

import com.helmont.arcana.items.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;
import com.google.common.collect.Sets;

import java.util.Set;

public class CardHolderScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PlayerInventory playerInventory;
    public final int inventoryWidth;
    public final int inventoryHeight;
    public final String customTitle;
    public static final Set<Item> SHULKER_BOXES;

    static
    {
        SHULKER_BOXES = Sets.newHashSet(Items.SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BLUE_SHULKER_BOX,
                Items.BROWN_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.GREEN_SHULKER_BOX,
                Items.LIGHT_BLUE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.LIME_SHULKER_BOX,
                Items.MAGENTA_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.RED_SHULKER_BOX,
                Items.WHITE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.PURPLE_SHULKER_BOX);
    }

    public CardHolderScreenHandler(final int syncId, final PlayerInventory playerInventory, final Inventory inventory, final int inventoryWidth, final int inventoryHeight, final Hand hand, String customTitle)
    {
        super(null, syncId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        this.inventoryWidth = inventoryWidth;
        this.inventoryHeight = 2;
        this.customTitle = customTitle;

        checkSize(inventory, inventoryWidth * inventoryHeight);
        inventory.onOpen(playerInventory.player);
        setupSlots(false);
    }

    public class CardHolderLockedSlot extends Slot
    {
        public CardHolderLockedSlot(Inventory inventory, int index, int x, int y)
        {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity)
        {
            return stackMovementIsAllowed(getStack());
        }

        @Override
        public boolean canInsert(ItemStack stack)
        {
            return stackMovementIsAllowed(stack);
        }

        public boolean stackMovementIsAllowed(ItemStack stack)
        {
            Item testItem = stack.getItem();
            if(stack.getItem() instanceof CardHolder) return false;
            if(SHULKER_BOXES.contains(testItem)) return false;
            if(stack.getItem() instanceof Death) return true;
            if(stack.getItem() instanceof Judgement) return true;
            if(stack.getItem() instanceof Justice) return true;
            if(stack.getItem() instanceof Strength) return true;
            if(stack.getItem() instanceof TarotItem) return true;
            if(stack.getItem() instanceof Temperance) return true;
            if(stack.getItem() instanceof TheChariot) return true;
            if(stack.getItem() instanceof TheDevil) return true;
            if(stack.getItem() instanceof TheEmperor) return true;
            if(stack.getItem() instanceof TheEmpress) return true;
            if(stack.getItem() instanceof TheFool) return true;
            if(stack.getItem() instanceof TheHangedMan) return true;
            if(stack.getItem() instanceof TheHermit) return true;
            if(stack.getItem() instanceof TheHierophant) return true;
            if(stack.getItem() instanceof TheHighPriestess) return true;
            if(stack.getItem() instanceof TheLovers) return true;
            if(stack.getItem() instanceof TheMagician) return true;
            if(stack.getItem() instanceof TheMoon) return true;
            if(stack.getItem() instanceof TheStar) return true;
            if(stack.getItem() instanceof TheSun) return true;
            if(stack.getItem() instanceof TheTower) return true;
            if(stack.getItem() instanceof TheWorld) return true;
            if(stack.getItem() instanceof WheelOfFortune) return true;
            return false;
        }
    }

    @Override
    public void close(final PlayerEntity player)
    {
        super.close(player);
        inventory.onClose(player);
    }

    public void setupSlots(final boolean includeChestInventory)
    {
        int i = (this.inventoryHeight - 4) * 18;

        int n;
        int m;
        for(n = 0; n < this.inventoryHeight; ++n)
        {
            for(m = 0; m < 9; ++m)
            {
                this.addSlot(new CardHolderLockedSlot(inventory, m + n * 9, 8 + m * 18, 18 + n * 18));
            }
        }

        for(n = 0; n < 3; ++n)
        {
            for(m = 0; m < 9; ++m)
            {
                this.addSlot(new CardHolderLockedSlot(playerInventory, m + n * 9 + 9, 8 + m * 18, 103 + n * 18 + i));
            }
        }

        for(n = 0; n < 9; ++n)
        {
            this.addSlot(new CardHolderLockedSlot(playerInventory, n, 8 + n * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(final PlayerEntity player)
    {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(final PlayerEntity player, final int invSlot)
    {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        ItemStack originalStack = slot.getStack();
        Item testItem = originalStack.getItem();

        if(testItem instanceof CardHolder) return ItemStack.EMPTY;
        if(SHULKER_BOXES.contains(testItem)) return ItemStack.EMPTY;

        if (slot.hasStack())
        {
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size())
            {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
            {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty())
            {
                slot.setStack(ItemStack.EMPTY);
            }
            else
            {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
