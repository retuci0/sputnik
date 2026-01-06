package me.retucio.sputnik.module.setting;

import me.retucio.sputnik.Sputnik;
import me.retucio.sputnik.event.events.sputnik.UpdateSettingEvent;
import me.retucio.sputnik.module.Module;

// base para los tipos de ajustes
public abstract class Setting {

    private final String name;
    private final String description;

    private boolean visible = true;
    private boolean searchMatch = true;

    private SettingGroup sg;

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

    public SettingGroup getSg() {
        return sg;
    }

    public void setSg(SettingGroup sg) {
        this.sg = sg;
    }

    public void fireUpdateEvent() {
        Sputnik.EVENT_BUS.post(
                new UpdateSettingEvent(
                        this,
                        sg.getModule()
                                .shouldSaveSettings()
                )
        );
    }
}
