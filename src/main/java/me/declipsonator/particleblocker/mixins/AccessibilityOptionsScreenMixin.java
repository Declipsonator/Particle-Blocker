/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.ParticleBlocker;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {

    @Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
    private static void addParticleButton(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        ArrayList<SimpleOption<?>> arrayList = new ArrayList<>(Arrays.asList(cir.getReturnValue()));
        arrayList.add(ParticleBlocker.particleButton);
        cir.setReturnValue(arrayList.toArray(cir.getReturnValue()));
    }

}
