package me.declipsonator.particleblocker.mixins.sodium;

import com.google.common.collect.ImmutableList;
import me.declipsonator.particleblocker.MixinConfigPlugin;
import me.declipsonator.particleblocker.sodium.ParticlesSodiumPage;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Restriction(require = @Condition("sodium"))
@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumGameOptionPagesMixin extends Screen {
    @Shadow @Final private List<OptionPage> pages;

    @Shadow private OptionPage currentPage;

    protected SodiumGameOptionPagesMixin() {
        super(Text.translatable("Sodium Options"));
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

        drawContext.drawCenteredTextWithShadow(
                textRenderer,
                Text.of("Particles Menu Requires that Reeses Sodium Options be installed."),
                drawContext.getScaledWindowWidth() / 2,
                drawContext.getScaledWindowHeight() / 2,
                0xFFFFFF
        );
    }
}
