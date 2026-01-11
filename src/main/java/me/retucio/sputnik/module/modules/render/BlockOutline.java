package me.retucio.sputnik.module.modules.render;

import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.setting.settings.ColorSetting;
import me.retucio.sputnik.module.setting.settings.NumberSetting;
import me.retucio.sputnik.util.Colors;

import java.awt.*;

/** continúa en:
 * @see me.retucio.sputnik.mixin.WorldRendererMixin
 */

public class BlockOutline extends Module {

    public ColorSetting color = sgGeneral.add(new ColorSetting(
            "color",
            "color del contorno de los bloques",
            Colors.mainColor,
            true)
    );

    public NumberSetting lineWidth = sgGeneral.add(new NumberSetting(
            "grosor",
            "grosor de las líneas",
            0.5,
            0.1,
            10,
            0.1
    ));

    public BlockOutline() {
        super("contorno",
                "customiza el contorno de los bloques",
                Category.RENDER);
    }
}
