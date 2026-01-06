package me.retucio.sputnik.module.modules.render;

import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.setting.SettingGroup;
import me.retucio.sputnik.module.setting.settings.BooleanSetting;
import me.retucio.sputnik.module.setting.settings.ColorSetting;
import me.retucio.sputnik.module.setting.settings.NumberSetting;

import java.awt.*;

/** continúa en:
 * @see me.retucio.sputnik.mixin.DamageParticleMixin
 * @see me.retucio.sputnik.mixin.DamageParticleFactoryMixin
 */

public class CritsPlus extends Module {

    SettingGroup sgAppearance = addSg(new SettingGroup("aspecto", true));
    SettingGroup sgBehaviour = addSg(new SettingGroup("funcionamiento", true));

    public ColorSetting color = sgAppearance.add(new ColorSetting("color", "color de las partículas", new Color(0, 0, 255, 255), false));
    public NumberSetting scale = sgAppearance.add(new NumberSetting("escala", "tamaño", 1, 0, 2, 0.05));
    public NumberSetting multiplier = sgAppearance.add(new NumberSetting("múltiplo", "número por el que multiplicar la cantidad de partículas generadas", 1, 0, 10, 0.1));

    public NumberSetting velocityMultipler = sgBehaviour.add(new NumberSetting("dispersión", "multiplicador de la velocidad de dispersión", 1, 0, 10, 0.1));
    public NumberSetting gravity = sgBehaviour.add(new NumberSetting("gravedad", "multiplicador de fuerza de gravedad", 1, 0, 10, 0.1));
    public NumberSetting maxAge = sgBehaviour.add(new NumberSetting("vida máxima", "cuánto persiste la partícula", 10, 0, 50, 1));
    public BooleanSetting collide = sgBehaviour.add(new BooleanSetting("colisionar", "colisionar con bloques", false));

    public CritsPlus() {
        super("críticos",
                "cambia la apariencia de los críticos al gusto",
                Category.RENDER);
    }
}
