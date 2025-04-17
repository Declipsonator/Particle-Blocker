/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.sodium;


import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

public class EmptyOptionStorage implements OptionStorage<Object> {

    @Override
    public Object getData() {
        return new Object();
    }

    @Override
    public void save() {

    }
}
