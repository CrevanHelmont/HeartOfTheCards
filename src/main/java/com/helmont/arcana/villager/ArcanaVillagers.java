package com.helmont.arcana.villager;

import com.google.common.collect.ImmutableSet;
import com.helmont.arcana.Arcana;
import com.helmont.arcana.registry.ModBlocks;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.mixin.object.builder.VillagerTypeAccessor;
import net.minecraft.block.Block;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.data.server.PointOfInterestTypeTagProvider;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.PointOfInterestTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;

public class ArcanaVillagers {

    public static final PointOfInterestType WORKBENCH_POI = registerPOI("workbenchpoi", ModBlocks.ARCANA_WORKBENCH);
    public static final VillagerProfession MYSTIC = registerProfession("mystic",
            RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY,
                    new Identifier(Arcana.MOD_ID, "workbenchpoi")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(Arcana.MOD_ID, name),
                VillagerProfessionBuilder.create().id(new Identifier(Arcana.MOD_ID, name)).workstation(type)
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN).build());
    }

    public static PointOfInterestType registerPOI(String name, Block block) {
        return Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(Arcana.MOD_ID, name),
                new PointOfInterestType(ImmutableSet.copyOf(block.getStateManager().getStates()), 1, 10));
    }

}
