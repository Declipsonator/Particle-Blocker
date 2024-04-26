/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.screen.ParticlesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.class)
public class SimpleOptionsMixin {

    @Shadow @Final
    Text text;

    @Inject(method= "createWidget(Lnet/minecraft/client/option/GameOptions;III)Lnet/minecraft/client/gui/widget/ClickableWidget;", at = @At("HEAD"), cancellable = true)
    public void makeParticleButton(GameOptions options, int x, int y, int width, CallbackInfoReturnable<ClickableWidget> cir) {
        if(text.getString().equals(Text.translatable("here.is.a.fake.key").getString())) {
            cir.setReturnValue(new ButtonWidget.Builder(Text.of("Particles"), (button) -> MinecraftClient.getInstance().setScreen(new ParticlesScreen(MinecraftClient.getInstance().currentScreen, options)))
                    .position(x, y)
                    .size(width, 20)
                    .build());
            cir.cancel();
        }
    }
}
