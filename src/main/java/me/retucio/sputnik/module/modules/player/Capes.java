package me.retucio.sputnik.module.modules.player;

import me.retucio.sputnik.cape.Cape;
import me.retucio.sputnik.cape.CapeManager;
import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.settings.OptionSetting;

public class Capes extends Module {

    public OptionSetting<Cape> cape = addSetting(new OptionSetting<>("capa", "qué capa llevar puesta",
            CapeManager.INSTANCE.getCapes(), CapeManager.INSTANCE.getCape("hollow-knight")));

    public Capes() {
        super("capas", "elige qué capa llevar (solo visible para ti)", Category.PLAYER);
    }
}
