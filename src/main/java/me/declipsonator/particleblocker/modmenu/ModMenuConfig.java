package me.declipsonator.particleblocker.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.declipsonator.particleblocker.screen.ParticlesScreen;
import net.minecraft.client.MinecraftClient;

public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new ParticlesScreen(screen, MinecraftClient.getInstance().options);
    }
}
