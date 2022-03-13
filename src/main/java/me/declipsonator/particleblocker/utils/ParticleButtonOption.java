package me.declipsonator.particleblocker.utils;

import me.declipsonator.particleblocker.screen.ParticlesScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ParticleButtonOption extends Option {
    
    public ParticleButtonOption() {
        super("particles");
    }

    public ClickableWidget createButton(final GameOptions options, final int x, final int y, final int width) {
        return new ButtonWidget(x, y, width, 20, Text.of("Particles"), (button) -> MinecraftClient.getInstance().setScreen(new ParticlesScreen(MinecraftClient.getInstance().currentScreen, options)));
    }


}