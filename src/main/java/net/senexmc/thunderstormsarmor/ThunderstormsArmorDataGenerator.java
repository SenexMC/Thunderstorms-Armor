package net.senexmc.thunderstormsarmor;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.senexmc.thunderstormsarmor.datagen.ModModelProvider;
import net.senexmc.thunderstormsarmor.datagen.ModRecipeGenerator;

public class ThunderstormsArmorDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeGenerator::new);
    }


}
