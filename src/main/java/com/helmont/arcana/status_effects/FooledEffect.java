package com.helmont.arcana.status_effects;

import com.helmont.arcana.util.MiscUtil;
import com.helmont.arcana.util.ModRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FooledEffect extends StatusEffect {
        public FooledEffect() {
            super(
                    StatusEffectCategory.BENEFICIAL,
                    0x8e3463);
        }

        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return true;
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (entity instanceof HostileEntity) {
                if (((HostileEntity) entity).getTarget() != null) {
                    if (entity instanceof SkeletonEntity skeleton && skeleton.getTarget().isRemoved()) {
                        //skeleton.setAttacking(false);
                        ((HostileEntity) entity).setAiDisabled(true);
                        ((HostileEntity) entity).setNoGravity(false);
                    }
                    if (((HostileEntity) entity).getTarget().isPlayer()) {
                        ((HostileEntity) entity).setAiDisabled(true);
                        ((HostileEntity) entity).setNoGravity(false);
                    } else if (((HostileEntity) entity).getTarget().hasStatusEffect(ModRegistries.FOOLED)) {
                        //((HostileEntity) entity).setTarget(null);
                        ((HostileEntity) entity).setAiDisabled(true);
                        ((HostileEntity) entity).setNoGravity(false);
                    }
                } else {
                    ((HostileEntity) entity).setAiDisabled(true);
                    ((HostileEntity) entity).setNoGravity(false);
                }
                if (!entity.getWorld().isClient) {

                    ServerWorld world = (ServerWorld) entity.getWorld();

                    List<Entity> entityList = world.getOtherEntities(entity, new Box(entity.getX() + 20,
                            entity.getY() + 20, entity.getZ() + 20, entity.getX() - 20,
                            entity.getY() - 20, entity.getZ() - 20), EntityPredicates.VALID_LIVING_ENTITY);
                    HostileEntity[] hostileEntities = new HostileEntity[]{};
                    double[] distList = new double[]{};

                    for (Entity entity1 : entityList) {
                        if(entity1 instanceof HostileEntity hostileEntity && !entity1.isRemoved()) {
                            if (!hostileEntity.hasStatusEffect(ModRegistries.FOOLED) && entity.canSee(hostileEntity)) {
                                hostileEntities = MiscUtil.addHostileEntity(hostileEntities.length, hostileEntities, hostileEntity);
                                double x = entity.getX() - entity1.getX();
                                double y = entity.getY() - entity1.getY();
                                double z = entity.getZ() - entity1.getZ();
                                double dist = Math.sqrt(x*x + y*y + z*z);
                                distList = MiscUtil.addX(distList.length, distList, dist);
                            }
                        }
                    }
                    if (hostileEntities.length != 0) {
                        double min = Collections.min(Arrays.asList(ArrayUtils.toObject(distList)));
                        System.out.println(Arrays.toString(hostileEntities));
                        System.out.println(Arrays.toString(distList));
                        System.out.println(min);

                        System.out.println(ArrayUtils.indexesOf(distList, min).stream().toArray()[0]);
                        HostileEntity hostileEntity2 = hostileEntities[ArrayUtils.indexesOf(distList, min).stream().toArray()[0]];
                        ((HostileEntity) entity).setTarget(hostileEntity2);
                        System.out.println(entity.canSee(hostileEntity2));
                        if (hostileEntity2 != null && !hostileEntity2.hasStatusEffect(ModRegistries.FOOLED)) {
                            ((HostileEntity) entity).setAiDisabled(false);
                        }
                    }
                }
            }
        }
    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof HostileEntity) {
            ((HostileEntity) entity).setAiDisabled(false);
            super.onRemoved(entity, attributes, amplifier);
        }
    }
}
