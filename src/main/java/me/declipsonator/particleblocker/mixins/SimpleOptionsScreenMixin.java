package me.declipsonator.particleblocker.mixins;

import me.declipsonator.particleblocker.utils.ParticleButtonOption;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleOptionsScreen.class)
public class SimpleOptionsScreenMixin {

    @Shadow
    private ButtonListWidget buttonList;

    private Text title = Text.of("");

    @Inject(method = "<init>", at = @At("TAIL"))
    private void getTitle(Screen parent, GameOptions gameOptions, Text title, Option[] options, CallbackInfo ci) {
        this.title = title;
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/option/Option;)V"))
    private void addParticleButton(CallbackInfo ci) {
        if(title.asString().equals(new TranslatableText("options.accessibility.title").asString()) && FabricLoader.getInstance().isModLoaded("sodium")) {
            buttonList.addSingleOptionEntry(new ParticleButtonOption());
        }
    }

}
