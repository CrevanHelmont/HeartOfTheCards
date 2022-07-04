package com.helmont.arcana.mixin;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ZombieVillagerEntity.class)
public interface MixinConvertCheckInvoker {

    @Invoker("isConverting")
    boolean invokeIsConverting();

}
