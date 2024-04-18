package net.senexmc.thunderstormsarmor.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.senexmc.thunderstormsarmor.item.custom.ModArmorMaterials;
import net.senexmc.thunderstormsarmor.item.ModItems;

public class ThunderEvent {
    private static final int TICKS_PER_STRIKE = 100; // 5 seconds in ticks (20 ticks per second)
    private static int ticksSinceLastStrike = 0;

    public static void init() {

        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerManager().getPlayerList().forEach(player -> {

            ServerWorld world = server.getOverworld();
            boolean isThundering = world.isThundering();
            boolean isRaining = world.isRaining();

            if (player instanceof ServerPlayerEntity && !player.isSpectator() && isThundering && isRaining) {

                BlockPos pos = player.getBlockPos().up();

                if (isOutsideOrUnderTree(player, world, pos) && (isCopperToolInHand(player) || hasCopperPartOn(player)) && Random.create().nextBoolean()) {
                    if (ticksSinceLastStrike >= TICKS_PER_STRIKE) {


                        strikeWithLightning(player);

                        ticksSinceLastStrike = 0;

                    } else {

                        ticksSinceLastStrike++;

                    }
                }
            }
        }));
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

    private static void strikeWithLightning(PlayerEntity player) {
        int xPosition = (int) player.getX();
        int yPosition = (int) player.getY();
        int zPosition = (int) player.getZ();
        ServerWorld world = (ServerWorld) player.getWorld();

        EntityType.LIGHTNING_BOLT.spawn(world, new BlockPos(xPosition, yPosition, zPosition), SpawnReason.TRIGGERED);
    }

    private static boolean isOutsideOrUnderTree(PlayerEntity player, ServerWorld world, BlockPos pos) {
        BlockPos playerPos = player.getBlockPos();
        return player.getWorld().isSkyVisible(playerPos) || world.getBlockState(pos).isIn(BlockTags.LEAVES);
    }
}