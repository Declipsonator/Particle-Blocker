package me.declipsonator.particleblocker.screen;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.Collectors;

import me.declipsonator.particleblocker.Config;
import me.declipsonator.particleblocker.utils.idComparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

@Environment(EnvType.CLIENT)
public class ParticlesWidget extends ElementListWidget<ParticlesWidget.Entry> {
    final ParticlesScreen parent;
    int maxWidth;

    public ParticlesWidget(ParticlesScreen parent, MinecraftClient client) {
        super(client, parent.width + 45, parent.height, 20, parent.height - 32, 20);
        this.parent = parent;
        ArrayList<Identifier> particles = new ArrayList<>(Registries.PARTICLE_TYPE.getIds());
        particles.sort(new idComparator());

        for (Identifier idParticle : particles) {

            Text text = Text.of(Arrays.stream(idParticle.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" ")));
            int width = client.textRenderer.getWidth(text);
            if (width > this.maxWidth) {
                this.maxWidth = width;
            }

            this.addEntry(new ParticleEntry(idParticle, text));
        }

    }

    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 15;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 32;
    }

    @Environment(EnvType.CLIENT)
    public class ParticleEntry extends Entry {
        private final Identifier particle;
        private final Text particleName;
        private final ButtonWidget editButton;

        ParticleEntry(Identifier particle, Text particleName) {
            this.particle = particle;
            this.particleName = particleName;
            this.editButton = new ButtonWidget.Builder(particleName, (button) -> Config.changeValue(particle.toString()))
                    .size(50, 20)
                    .position(0, 0)
                    .narrationSupplier(textSupplier -> (MutableText) Text.of("Toggled " + particleName + " " + (Config.getValue(particle.toString()) ? "Off" : "On")))
                    .build();
        }



        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.editButton);
        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(this.editButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer textRenderer = client.textRenderer;
            Objects.requireNonNull(client.textRenderer);
            textRenderer.draw(matrices, this.particleName, x + 90 - maxWidth, (float) ((y + entryHeight / 2) - 4.5), 16777215);
            this.editButton.setX(x + 190);
            this.editButton.setY(y);

            this.editButton.setMessage(Text.of(String.valueOf(Config.getValue(particle.toString()))));

            if (Config.getValue(particle.toString())) {
                this.editButton.setMessage(editButton.getMessage().copy().formatted(Formatting.GREEN));
            } else {
                this.editButton.setMessage(this.editButton.getMessage().copy().formatted(Formatting.RED));
            }

            this.editButton.render(matrices, mouseX, mouseY, tickDelta);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
        public Entry() {
        }
    }
}


