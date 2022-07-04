package com.helmont.arcana.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TameableEntity.class)
public abstract class MixinTameableEntity {

    @Shadow public abstract boolean isSitting();

    @Shadow public abstract boolean isTamed();
    @Shadow public abstract boolean isOwner(LivingEntity p_isOwner_1_);

}
