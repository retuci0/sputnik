package me.retucio.sputnik.module.modules.player;

import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;

/** continúa en:
 * @see me.retucio.sputnik.mixin.ClientPlayerEntityMixin
 */

public class PortalGUI extends Module {

    public PortalGUI() {
        super("interfaz en portales",
                "te permite abrir interfaces dentro de portales, como el chat o el inventario",
                Category.PLAYER);
    }
}