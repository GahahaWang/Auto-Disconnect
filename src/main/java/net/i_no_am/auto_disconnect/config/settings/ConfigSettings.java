package net.i_no_am.auto_disconnect.config.settings;

import net.i_no_am.auto_disconnect.config.Config;

public class ConfigSettings<T> {
    private final T value;

    public ConfigSettings(T value) {
        this.value = value;
    }

    public T getVal() {
        return value;
    }

    public void setVal(String name, T value) {
        Config.ADconfig.write(name,value);
    }

}
