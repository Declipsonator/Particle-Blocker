/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.mixins;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public class FireworkParticleMixin {
    @Shadow @Final private ParticleManager particleManager;

    @Inject(method = "addExplosionParticle", at = @At("HEAD"), cancellable = true)
    public void seeIfNull(double x, double y, double z, double velocityX, double velocityY, double velocityZ, IntList colors, IntList targetColors, boolean trail, boolean flicker, CallbackInfo ci) {
        if(particleManager.addParticle(ParticleTypes.FIREWORK, x, y, z, velocityX, velocityY, velocityZ) == null) ci.cancel();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;setColor(FFF)V"))
    public void seeIfNull(Particle instance, float red, float green, float blue) {
        if(instance == null) return;
        instance.setColor(red, green, blue);
    }
}
