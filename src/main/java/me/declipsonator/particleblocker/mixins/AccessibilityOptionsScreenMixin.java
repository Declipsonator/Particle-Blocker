package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.ParticleBlocker;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {

    @Inject(method = "getOptions", at = @At("HEAD"), cancellable = true)
    private static void addParticleButton(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        cir.setReturnValue(new SimpleOption[]{gameOptions.getNarrator(), gameOptions.getShowSubtitles(), gameOptions.getTextBackgroundOpacity(), gameOptions.getBackgroundForChatOnly(), gameOptions.getChtOpacity(), gameOptions.getChatLineSpacing(), gameOptions.getChatDelay(), gameOptions.getAutoJump(), gameOptions.getSneakToggled(), gameOptions.getSprintToggled(), gameOptions.getDistortionEffectScale(), gameOptions.getFovEffectScale(), gameOptions.getMonochromeLogo(), gameOptions.getHideLightningFlashes(), gameOptions.getDarknessEffectScale(), ParticleBlocker.particleButton});
    }

}
