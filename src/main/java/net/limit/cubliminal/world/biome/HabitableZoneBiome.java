package net.limit.cubliminal.world.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

public class HabitableZoneBiome {
    public static Biome create(RegistryEntryLookup<PlacedFeature> features, RegistryEntryLookup<ConfiguredCarver<?>> carvers) {
        Biome.Builder biome = new Biome.Builder();

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        BiomeEffects.Builder biomeEffects = new BiomeEffects.Builder();

        biomeEffects.loopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP);
        biomeEffects.skyColor(10989753);
        biomeEffects.fogColor(197388);
        biomeEffects.waterColor(6911607);
        biomeEffects.waterFogColor(10206912);
        BiomeEffects effects = biomeEffects.build();

        spawnSettings.spawn(SpawnGroup.MONSTER,
                new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 515, 1, 1));
        spawnSettings.creatureSpawnProbability(0.003f);

        biome.spawnSettings(spawnSettings.build());
        biome.generationSettings(generationSettings.build());
        biome.temperature(0.4f);
        biome.downfall(0.7f);
        biome.precipitation(false);
        biome.effects(effects);

        return biome.build();
    }
}
