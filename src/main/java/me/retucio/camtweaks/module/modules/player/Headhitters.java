package me.retucio.camtweaks.module.modules.player;

import me.retucio.camtweaks.module.Category;
import me.retucio.camtweaks.module.Module;
import me.retucio.camtweaks.module.settings.NumberSetting;

public class Headhitters extends Module {

    public NumberSetting delay = addSetting(new NumberSetting("delay", "delay de saltos", 0, 0, 20, 1));

    public Headhitters() {
        super("headhitters",
                "mantén pulsado el espacio para hacer headhitting sin tener que espamearlo",
                Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        mc.player.jumpingCooldown = delay.getIntValue();
    }
}
