package com.helmont.arcana.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerEntity.class)
public interface MixinVillagerAccessor {
    @Accessor("foodLevel")
    void setFoodLevel(int foodLevel);
}
