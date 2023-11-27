package me.declipsonator.particleblocker.sodium;

import me.declipsonator.particleblocker.Config;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class EmptyOptionStorage implements OptionStorage<Object> {

    @Override
    public Object getData() {
        return new Object();
    }

    @Override
    public void save() {

    }
}
