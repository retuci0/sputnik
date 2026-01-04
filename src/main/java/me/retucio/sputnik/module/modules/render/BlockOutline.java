package me.retucio.sputnik.module.modules.render;

import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.settings.ColorSetting;

import java.awt.*;

/** continúa en:
 * @see me.retucio.sputnik.mixin.WorldRendererMixin
 */

public class BlockOutline extends Module {

    public ColorSetting color = addSetting(new ColorSetting("color", "color del contorno de los bloques",
            new Color(0, 0, 0, 102), true));

    public BlockOutline() {
        super("contorno",
                "customiza el contorno de los bloques",
                Category.RENDER);
    }
}
