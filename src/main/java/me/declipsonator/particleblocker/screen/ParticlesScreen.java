package me.declipsonator.particleblocker.screen;

import me.declipsonator.particleblocker.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class ParticlesScreen extends GameOptionsScreen {
    private ParticlesWidget particleWidget;
    private ButtonWidget changeAllButton;
    private boolean allOn = true;

    public ParticlesScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, Text.of("Particles"));
    }

    protected void init() {
        particleWidget = new ParticlesWidget(this, client);
        addSelectableChild(particleWidget);
        changeAllButton = addDrawableChild(new ButtonWidget.Builder(Text.of("Toggle All On").copy().formatted(Formatting.GREEN), (button) -> {
            Set<Identifier> particles = Registries.PARTICLE_TYPE.getIds();
            for(Identifier particle: particles) {
                Config.setValue(particle.toString(), !allOn);
            }
        })
                .size(150, 20)
                .position(width / 2 -155, height - 29)
                .build());

        addDrawableChild(new ButtonWidget.Builder(ScreenTexts.DONE, (button) -> client.setScreen(parent))
                .position(width / 2 - 155 + 160, height - 29)
                .size(150, 20)
                .build());
    }


    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        particleWidget.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, textRenderer, title, width / 2, 8, 16777215);
        boolean allTrue = true;
        Set<Identifier> particles = Registries.PARTICLE_TYPE.getIds();

        for (Identifier particle : particles) {
            if (!Config.getValue(particle.toString())) {
                allTrue = false;
                break;
            }
        }

        allOn = allTrue;

        if(allOn) changeAllButton.setMessage(Text.of("Toggle All Off").copy().formatted(Formatting.RED));
        else changeAllButton.setMessage(Text.of("Toggle All On").copy().formatted(Formatting.GREEN));

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        Config.saveConfig();
        this.client.setScreen(this.parent);
    }
}
