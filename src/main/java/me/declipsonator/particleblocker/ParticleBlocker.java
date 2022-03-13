package me.declipsonator.particleblocker;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ParticleBlocker implements ModInitializer {
    public static final Logger LOG = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing Particle Blocker");
        Config.loadConfig();

        Runtime.getRuntime().addShutdownHook(new Thread(Config::saveConfig));
    }

}
