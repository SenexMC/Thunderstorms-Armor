package net.senexmc.thunderstormsarmor.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.senexmc.thunderstormsarmor.item.ModItems;
import net.senexmc.thunderstormsarmor.item.custom.ModArmorMaterials;

public class ThunderEvent {
    private static final Random RANDOM = Random.create();
    private static int tickCounter = 0;
    private static final int MIN_INTERVAL = 200; // Minimum ticks between strikes
    private static final int MAX_INTERVAL = 600; // Maximum ticks between strikes
    private static int nextStrikeTime = getRandomInterval();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerManager().getPlayerList().forEach(player -> {
            ServerWorld world = player.getServerWorld();
            boolean isThundering = world.isThundering();
            tickCounter++;
            if (tickCounter >= nextStrikeTime && hasCopper(player) && isThundering) {
                strikePlayerWithLightning(world, player);
                nextStrikeTime = tickCounter + getRandomInterval(); // Reset the counter with a new random interval
            }
        }));
    }

    private static void strikePlayerWithLightning(ServerWorld world, PlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        EntityType.LIGHTNING_BOLT.spawn(world, null, null, pos, SpawnReason.TRIGGERED, true, true);
    }

    private static boolean hasCopper(PlayerEntity player) {
        return hasCopperPartOn(player) || isCopperToolInHand(player);
    }

    private static boolean isCopperToolInHand(PlayerEntity player) {
        ItemStack heldItem = player.getMainHandStack();
        Item item = heldItem.getItem();

        // Check if the held item is a copper tool
        return item.equals(ModItems.COPPER_PICKAXE) || item.equals(ModItems.COPPER_AXE) ||
                item.equals(ModItems.COPPER_SHOVEL) || item.equals(ModItems.COPPER_HOE) || item.equals(ModItems.COPPER_SWORD);
    }

    private static boolean hasCopperPartOn(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        return isCopperArmor(helmet) || isCopperArmor(chestplate)
                || isCopperArmor(leggings) || isCopperArmor(boots);
    }

    private static boolean isCopperArmor(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            ArmorMaterial material = armorItem.getMaterial();
            return material == ModArmorMaterials.COPPER;
        }
        return false;
    }

    private static int getRandomInterval() {
        return MathHelper.nextInt(RANDOM, MIN_INTERVAL, MAX_INTERVAL);
    }
}