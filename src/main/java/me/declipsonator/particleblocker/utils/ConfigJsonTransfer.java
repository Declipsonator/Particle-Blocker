/*
 * Copyright (c) 2024  Declipsonator. All rights reserved.
 *
 * This software is licensed under the GNU Lesser General Public License version 3 (LGPL-3.0).
 * You may obtain a copy of the license at <https://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */

package me.declipsonator.particleblocker.utils;

import java.util.ArrayList;

public class ConfigJsonTransfer {
    ArrayList<String> activeParticles;
    ArrayList<String> inactiveParticles;

    public ArrayList<String> getActiveParticles() {
        return activeParticles;
    }

    public ArrayList<String> getInactiveParticles() {
        return inactiveParticles;
    }
}
