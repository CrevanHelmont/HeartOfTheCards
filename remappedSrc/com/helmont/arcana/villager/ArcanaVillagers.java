package com.helmont.arcana.villager;

import com.google.common.collect.ImmutableSet;
import com.helmont.arcana.Arcana;
import com.helmont.arcana.registry.ModBlocks;
import net.fabricmc.fabric.mixin.object.builder.PointOfInterestTypeAccessor;
import net.fabricmc.fabric.mixin.object.builder.VillagerProfessionAccessor;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ArcanaVillagers {

    public static final PointOfInterestType WORKBENCH_POI = registerPOI("workbenchpoi", ModBlocks.ARCANA_WORKBENCH);
    public static final VillagerProfession MYSTIC = registerProfession("mystic", WORKBENCH_POI);

    public static VillagerProfession registerProfession(String name, PointOfInterestType type) {
        return Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(Arcana.MOD_ID, name),
                VillagerProfessionAccessor.create(name, type, ImmutableSet.of(), ImmutableSet.of(),
                        SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    }

    public static PointOfInterestType registerPOI(String name, Block block) {
        return Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(Arcana.MOD_ID, name),
                PointOfInterestTypeAccessor.callCreate(name,
                        ImmutableSet.copyOf(block.getStateManager().getStates()), 1, 10));
    }

    public static void setupPOIs() {
        PointOfInterestTypeAccessor.callSetup(WORKBENCH_POI);
    }

}
