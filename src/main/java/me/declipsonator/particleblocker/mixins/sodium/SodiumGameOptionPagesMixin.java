/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.mixins.sodium;

import me.declipsonator.particleblocker.sodium.ParticlesSodiumPage;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Restriction(require = @Condition("sodium"))
@Mixin(net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI.class)
public abstract class SodiumGameOptionPagesMixin extends Screen {
    @Shadow @Final
    private List<OptionPage> pages;

    @Shadow
    private OptionPage currentPage;

    protected SodiumGameOptionPagesMixin() {
        super(Text.of("Sodium Options"));
    }


    @Inject(method = "<init>", at = @At(value = "RETURN"))
    void init(CallbackInfo ci) {
        this.pages.add(ParticlesSodiumPage.createPage());
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    void addReesesSodiumOptionsRequiredText(DrawContext drawContext, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(FabricLoader.getInstance().isModLoaded("reeses-sodium-options") || !this.currentPage.getName().getString().equals("Particles")) {
            return;
        }

        String text1 = "Particles Menu Requires that Reeses Sodium Options be installed.";

        drawContext.drawCenteredTextWithShadow(
                textRenderer,
                Text.of(text1),
                drawContext.getScaledWindowWidth() / 2,
                drawContext.getScaledWindowHeight() / 2,
                0xFFFFFF
        );

        drawContext.drawCenteredTextWithShadow(
                textRenderer,
                Text.of("A Second Menu is present in accessibility settings or ModMenu config"),
                drawContext.getScaledWindowWidth() / 2,
                drawContext.getScaledWindowHeight() / 2 + textRenderer.getWrappedLinesHeight(text1, Integer.MAX_VALUE) + 3,
                0xFFFFFF
        );
    }
}
