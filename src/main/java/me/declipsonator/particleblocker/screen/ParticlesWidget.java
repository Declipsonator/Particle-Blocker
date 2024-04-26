/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.screen;

import com.google.common.collect.ImmutableList;
import me.declipsonator.particleblocker.Config;
import me.declipsonator.particleblocker.utils.idComparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ParticlesWidget extends ElementListWidget<ParticlesWidget.Entry> {
    final ParticlesScreen parent;
    int maxWidth;


    public ParticlesWidget(ParticlesScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 25);
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
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer textRenderer = client.textRenderer;
            Objects.requireNonNull(client.textRenderer);
            context.drawTextWithShadow(textRenderer, this.particleName, x, (y + entryHeight / 2) - 2, 16777215);
            this.editButton.setX(x + entryWidth - 50);
            this.editButton.setY(y);

            this.editButton.setMessage(Text.of(String.valueOf(Config.getValue(particle.toString()))));

            if (Config.getValue(particle.toString())) {
                this.editButton.setMessage(editButton.getMessage().copy().formatted(Formatting.GREEN));
            } else {
                this.editButton.setMessage(this.editButton.getMessage().copy().formatted(Formatting.RED));
            }

            this.editButton.render(context, mouseX, mouseY, tickDelta);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
        public Entry() {
        }
    }
}


