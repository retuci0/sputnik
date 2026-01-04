package me.retucio.sputnik.module.settings;

import me.retucio.sputnik.event.events.sputnik.UpdateSettingEvent;
import me.retucio.sputnik.module.Module;

// base para los tipos de ajustes
public abstract class Setting {

    private final String name;
    private final String description;

    private boolean visible = true;
    private boolean searchMatch = true;

    private Module module;

    protected Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSearchMatch() {
        return searchMatch;
    }

    public void setSearchMatch(boolean searchMatch) {
        this.searchMatch = searchMatch;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void fireUpdateEvent() {
        me.retucio.sputnik.Sputnik.EVENT_BUS.post(new UpdateSettingEvent(this, module.shouldSaveSettings()));
    }
}
