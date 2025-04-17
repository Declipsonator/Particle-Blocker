/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.sodium;

import com.google.common.collect.ImmutableList;
import me.declipsonator.particleblocker.Config;
import me.declipsonator.particleblocker.utils.idComparator;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParticlesSodiumPage {
    static EmptyOptionStorage emptyOptionStorage = new EmptyOptionStorage();

    public static OptionPage createPage() {
        List<OptionGroup> groups = new ArrayList<>();

        if(!FabricLoader.getInstance().isModLoaded("reeses-sodium-options")) {
            return new OptionPage(Text.of("Particles"), ImmutableList.copyOf(groups));
        }

        var builder = OptionGroup.createBuilder();

        ArrayList<Identifier> particles = new ArrayList<>(Registries.PARTICLE_TYPE.getIds());
        particles.sort(new idComparator());

        ArrayList<ParticlePair> pairs = new ArrayList<>();

        for (Identifier idParticle : particles) {
            pairs.add(new ParticlePair(idParticle, Text.of(Arrays.stream(idParticle.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" ")))));
        }

        for(var pair : pairs) {
            builder.add(makeSodiumOptionFromParticlePair(pair));
        }

        groups.add(builder.build());

        return new OptionPage(Text.of("Particles"), ImmutableList.copyOf(groups));
    }


    private static Option<Boolean> makeSodiumOptionFromParticlePair(ParticlePair pair) {
        return OptionImpl.createBuilder(boolean.class, emptyOptionStorage)
                .setName(pair.title)
                .setControl(TickBoxControl::new)
                .setImpact(OptionImpact.LOW)
                .setTooltip(Text.of("Disables rendering of " + pair.title.getString()))
                .setBinding((opts, value) -> Config.changeValue(pair.idParticle.toString()), (opts) -> Config.getValue(pair.idParticle.toString()))
            .build();
    }


    record ParticlePair(Identifier idParticle, Text title) {

    }
}
