package net.senexmc.thunderstormsarmor;

import net.fabricmc.api.ModInitializer;

import net.senexmc.thunderstormsarmor.event.ThunderEvent;
import net.senexmc.thunderstormsarmor.item.ModItemGroup;
import net.senexmc.thunderstormsarmor.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThunderstormsArmor implements ModInitializer {
	public static final String MOD_ID = "thunderstormsarmor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroup.init();
		ModItems.init();
		ThunderEvent.init();
	}
}