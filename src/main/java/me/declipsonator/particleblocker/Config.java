package me.declipsonator.particleblocker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.declipsonator.particleblocker.utils.ConfigJsonTransfer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Config {

    private static final ArrayList<String> activeParticles = new ArrayList<>();

    public static boolean getValue(String particle) {
        return activeParticles.contains(particle);
    }

    public static void changeValue(String particle) {
         if (activeParticles.contains(particle)) activeParticles.remove(particle);
         else activeParticles.add(particle);

    }

    public static void setValue(String particle, boolean value) {
        if (!activeParticles.contains(particle) && value) activeParticles.add(particle);
        else if (activeParticles.contains(particle) && !value) activeParticles.remove(particle);

    }

    public static void loadConfig() {
        try {
            ParticleBlocker.LOG.info("Loading Configuration");
            Path configPath = Path.of(FabricLoader.getInstance().getConfigDir().toString() + File.separator + "particle-blocker.json");
            File configFile = configPath.toFile();
            if(!configFile.exists()) {
                ParticleBlocker.LOG.info("Couldn't find Config file, setting everything to true");
                for(Identifier id: Registry.PARTICLE_TYPE.getIds()) setValue(id.toString(), true);
                return;
            }
            Gson gson = new Gson();
            String data = new String(Files.readAllBytes(configPath)).replace("\n", "");
            ConfigJsonTransfer config = gson.fromJson(data, ConfigJsonTransfer.class);
            activeParticles.clear();
            for(String particle: config.getActiveParticles()) {
                setValue(particle, true);
            }

            for(Identifier id: Registry.PARTICLE_TYPE.getIds()) {
                if (!config.getActiveParticles().contains(id.toString()) && !config.getInactiveParticles().contains(id.toString())) {
                    ParticleBlocker.LOG.warn("Couldn't Find " + id + " in the config file, setting value to true");
                    setValue(id.toString(), true);
                }
            }
            ParticleBlocker.LOG.info("Successfully Loaded Configuration");

        } catch (Exception e) {
            ParticleBlocker.LOG.error("Failed to Load Configuration\n" + e.getMessage());
        }

    }

    public static void saveConfig() {
        try {
            ParticleBlocker.LOG.info("Saving Configuration");

            Path configPath = Path.of(FabricLoader.getInstance().getConfigDir().toString() + "/particle-blocker.json");
            File configFile = configPath.toFile();

            JsonArray inactiveArray = new JsonArray();
            for(Identifier id: Registry.PARTICLE_TYPE.getIds()) {
                if(!activeParticles.contains(id.toString())) inactiveArray.add(id.toString());
            }
            JsonArray activeArray = new JsonArray();
            for(String particle: activeParticles) {
                activeArray.add(particle);
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.add("activeParticles", activeArray);
            jsonObject.add("inactiveParticles", inactiveArray);

            String data = jsonObject.toString();


            FileOutputStream fos = new FileOutputStream(configFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] bytes = data.getBytes();
            bos.write(bytes);
            bos.close();
            fos.close();

            ParticleBlocker.LOG.info("Successfully Saved Configuration");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
