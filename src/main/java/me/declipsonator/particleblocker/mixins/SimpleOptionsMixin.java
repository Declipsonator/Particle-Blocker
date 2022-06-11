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

    @Inject(method="createButton", at = @At("HEAD"), cancellable = true)
    public void makeParticleButton(GameOptions options, int x, int y, int width, CallbackInfoReturnable<ClickableWidget> cir) {
        if(text.getString().equals(Text.translatable("here.is.a.fake.key").getString())) {
            cir.setReturnValue(new ButtonWidget(x, y, width, 20, Text.of("Particles"), (button) -> MinecraftClient.getInstance().setScreen(new ParticlesScreen(MinecraftClient.getInstance().currentScreen, options))));
            cir.cancel();
        }
    }
}
