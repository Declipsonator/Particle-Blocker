/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.ParticleBlocker;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin {
    @Shadow
    private OptionListWidget list;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addAll([Lnet/minecraft/client/option/SimpleOption;)V"))
    private void addButton(CallbackInfo ci) {
        boolean sodiumPresent = FabricLoader.getInstance().isModLoaded("sodium");
        if(!sodiumPresent) list.addSingleOptionEntry(ParticleBlocker.particleButton);
    }
}
