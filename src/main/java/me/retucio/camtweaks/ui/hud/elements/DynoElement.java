package me.retucio.camtweaks.ui.hud.elements;

import me.retucio.camtweaks.module.ModuleManager;
import me.retucio.camtweaks.module.modules.client.HUD;
import me.retucio.camtweaks.ui.hud.ImageHudElement;
import net.minecraft.text.Text;

import java.util.List;

public class DynoElement extends ImageHudElement {

    private HUD.Dynosaurs dyno;

    public DynoElement() {
        super("dyno",
                mc.getWindow().getScaledWidth() - 100,
                mc.getWindow().getScaledHeight() - 100);
        reloadTexture();
    }

    @Override
    protected String getImagePath() {
        HUD hud = ModuleManager.INSTANCE.getModuleByClass(HUD.class);
        if (hud == null) return "textures/dynos/spinosaurus.png";

        dyno = hud.dyno.getValue();
        return "textures/dynos/" + getDynoFileName(dyno) + ".png";
    }

    private String getDynoFileName(HUD.Dynosaurs dyno) {
        return dyno.toRealString().toLowerCase();
    }

    @Override
    public List<Text> getTooltip() {
        return List.of(
                Text.literal("dinosaurio: " + dyno),
                Text.literal("que viva el autismo joder")
        );
    }
}
