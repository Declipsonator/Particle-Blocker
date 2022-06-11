/*
 *hello
 * Copyright (c) 2021 Meteor Development.
 */

package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.Config;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@SuppressWarnings("ALL")
@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    final HashMap<String, Identifier> particlesAndClasses = new HashMap<>();

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void RemoveParticles(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        Identifier id = Registry.PARTICLE_TYPE.getId(parameters.getType());
        if(!Config.getValue(id.toString())) cir.cancel();
    }

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void removeDirectParticles(Particle particle, CallbackInfo ci) {
        Object p = particlesAndClasses.get(particle.getClass().getSimpleName());
        if(p == null) return;
        else if (!Config.getValue(p.toString())) ci.cancel();


    }

    @Inject(method = "registerFactory(Lnet/minecraft/particle/ParticleType;Lnet/minecraft/client/particle/ParticleFactory;)V", at = @At("HEAD"))
    private void addClass(ParticleType<?> type, ParticleFactory<?> factory, CallbackInfo ci) {
        particlesAndClasses.put(factory.getClass().getDeclaringClass().getSimpleName(), Registry.PARTICLE_TYPE.getId(type));
    }



}