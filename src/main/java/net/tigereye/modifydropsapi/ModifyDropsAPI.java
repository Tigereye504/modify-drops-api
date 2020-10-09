package net.tigereye.modifydropsapi;

import net.fabricmc.api.ModInitializer;
import net.tigereye.modifydropsapi.internal.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModifyDropsAPI implements ModInitializer {
	public static final String MODID = "modifydropsapi";
	public static final Logger LOGGER = LogManager.getLogger("ModifyDropsAPI");
	@Override
	public void onInitialize() {
		Listeners.register();
		LOGGER.info("ModifyDropsAPI initialized!");
	}
}
