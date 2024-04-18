package net.senexmc.thunderstormsarmor.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.senexmc.thunderstormsarmor.ThunderstormsArmor;

public class ModItemGroup {
    public static ItemGroup COPPER_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(ThunderstormsArmor.MOD_ID, "copper"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.copper"))
                    .icon(() -> new ItemStack(ModItems.COPPER_HELMET)).entries((displayContext, entries) -> {
                        entries.add(ModItems.COPPER_HELMET);
                        entries.add(ModItems.COPPER_CHESTPLATE);
                        entries.add(ModItems.COPPER_LEGGINGS);
                        entries.add(ModItems.COPPER_BOOTS);
                        entries.add(ModItems.COPPER_PICKAXE);
                        entries.add(ModItems.COPPER_AXE);
                        entries.add(ModItems.COPPER_SHOVEL);
                        entries.add(ModItems.COPPER_HOE);
                        entries.add(ModItems.COPPER_SWORD);
                    }).build());

    public static void init() {
        ThunderstormsArmor.LOGGER.info("Registering copper item group for " + ThunderstormsArmor.MOD_ID);
    }
}