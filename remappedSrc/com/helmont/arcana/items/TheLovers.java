package com.helmont.arcana.items;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.mixin.client.rendering.LivingEntityRendererAccessor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import org.checkerframework.common.reflection.qual.Invoke;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

public class TheLovers extends Item {
    public TheLovers(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("item.arcproj.the_lovers.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static final EntityType<?>[] entityTypes = new EntityType[]{EntityType.AXOLOTL, EntityType.BEE, EntityType.CAT,
            EntityType.CHICKEN, EntityType.COW, EntityType.DONKEY, EntityType.FOX, EntityType.GOAT,
            EntityType.HOGLIN, EntityType.HORSE, EntityType.LLAMA, EntityType.MOOSHROOM, EntityType.OCELOT,
            EntityType.PANDA, EntityType.PIG, EntityType.RABBIT, EntityType.SHEEP, EntityType.STRIDER,
            EntityType.TRADER_LLAMA, EntityType.TURTLE, EntityType.WOLF};
    public static final EntityType<?>[] tameableEntities = new EntityType[]{EntityType.DONKEY, EntityType.HORSE,
            EntityType.LLAMA, EntityType.TRADER_LLAMA, EntityType.WOLF};

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        ActionResult result = ActionResult.FAIL;
        
        for (EntityType<?> e : entityTypes) {
            if(entity.getType() == e) {
                for (EntityType<?> t : tameableEntities) {
                    if (entity.getType() == t) {
                        TameableEntity animal = (TameableEntity) entity;
                        if (animal.isTamed()) {
                            if (!animal.isInLove()) {
                                if (!user.getWorld().isClient()) {
                                    ServerPlayerEntity player = (ServerPlayerEntity) user;
                                    animal.setSitting(!animal.isSitting());
                                    animal.setLoveTicks(600);
                                    user.getStackInHand(hand).setDamage(user.getStackInHand(hand).getDamage() + 1);
                                    return ActionResult.CONSUME_PARTIAL;
                                }
                            } else {
                                return ActionResult.FAIL;
                            }
                        } else {
                            return ActionResult.FAIL;
                        }
                    }
                }
                AnimalEntity animal = (AnimalEntity) entity;
                if (!animal.isInLove()) {
                    animal.setLoveTicks(600);
                    user.getStackInHand(hand).setDamage(user.getStackInHand(hand).getDamage() + 1);
                    result = ActionResult.CONSUME_PARTIAL;
                } else {
                    result = ActionResult.FAIL;
                }
            } else {
                result = ActionResult.FAIL;
            }
        }
        return result;
    }

}
