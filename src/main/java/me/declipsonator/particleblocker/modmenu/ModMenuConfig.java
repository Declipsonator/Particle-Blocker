/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.declipsonator.particleblocker.screen.ParticlesScreen;
import net.minecraft.client.MinecraftClient;

public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new ParticlesScreen(screen, MinecraftClient.getInstance().options);
    }
}
