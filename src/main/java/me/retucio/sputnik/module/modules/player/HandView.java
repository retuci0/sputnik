package me.retucio.sputnik.module.modules.player;

import me.retucio.sputnik.event.SubscribeEvent;
import me.retucio.sputnik.event.events.RenderHeldItemEvent;
import me.retucio.sputnik.event.events.RenderArmEvent;
import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.setting.SettingGroup;
import me.retucio.sputnik.module.setting.settings.BooleanSetting;
import me.retucio.sputnik.module.setting.settings.NumberSetting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3d;

/** continúa en:
 * @see me.retucio.sputnik.mixin.HeldItemRendererMixin
 */

public class HandView extends Module {

    SettingGroup sgAnimations = addSg(new SettingGroup("animaciones", false));
    SettingGroup sgMainHand = addSg(new SettingGroup("mano principal", false));
    SettingGroup sgOffhand = addSg(new SettingGroup("mano secundaria", false));
    SettingGroup sgArm = addSg(new SettingGroup("brazo", false));

    // ajustes de animaciones
    public BooleanSetting oldAnimations = sgAnimations.add(new BooleanSetting("animaciones viejas", "como las de la 1.8, al pegar", false));
    public BooleanSetting skipSwapping = sgAnimations.add(new BooleanSetting("no swap", "se salta la animación de cambiar items de mano", false));
    public BooleanSetting noFood = sgAnimations.add(new BooleanSetting("no comer", "no renderiza la animación de comer", false));

    // ajustes de mano principal
    public NumberSetting scaleMainX = sgMainHand.add(new NumberSetting("escala de la mano principal (X)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleMainY = sgMainHand.add(new NumberSetting("escala de la mano principal (Y)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleMainZ = sgMainHand.add(new NumberSetting("escala de la mano principal (Z)", ".",
            1, 0.01, 5, 0.1));

    public NumberSetting posMainX = sgMainHand.add(new NumberSetting("posición de la mano principal (X)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posMainY = sgMainHand.add(new NumberSetting("posición de la mano principal (Y)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posMainZ = sgMainHand.add(new NumberSetting("posición de la mano principal (Z)", ".",
            0, -3, 3, 0.1));

    public NumberSetting rotMainX = sgMainHand.add(new NumberSetting("rotación de la mano principal (X)", ".",
            0, -180, 180, 1));
    public NumberSetting rotMainY = sgMainHand.add(new NumberSetting("rotación de la mano principal (Y)", ".",
            0, -180, 180, 1));
    public NumberSetting rotMainZ = sgMainHand.add(new NumberSetting("rotación de la mano principal (Z)", ".",
            0, -180, 180, 1));

    // ajustes de mano secundaria
    public NumberSetting scaleOffX = sgOffhand.add(new NumberSetting("escala de la mano secundaria (X)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleOffY = sgOffhand.add(new NumberSetting("escala de la mano secundaria (Y)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleOffZ = sgOffhand.add(new NumberSetting("escala de la mano secundaria (Z)", ".",
            1, 0.01, 5, 0.1));

    public NumberSetting posOffX = sgOffhand.add(new NumberSetting("posición de la mano secundaria (X)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posOffY = sgOffhand.add(new NumberSetting("posición de la mano secundaria (Y)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posOffZ = sgOffhand.add(new NumberSetting("posición de la mano secundaria (Z)", ".",
            0, -3, 3, 0.1));

    public NumberSetting rotOffX = sgOffhand.add(new NumberSetting("rotación de la mano secundaria (X)", ".",
            0, -180, 180, 1));
    public NumberSetting rotOffY = sgOffhand.add(new NumberSetting("rotación de la mano secundaria (Y)", ".",
            0, -180, 180, 1));
    public NumberSetting rotOffZ = sgOffhand.add(new NumberSetting("rotación de la mano secundaria (Z)", ".",
            0, -180, 180, 1));

    // brazo
    public NumberSetting scaleArmX = sgArm.add(new NumberSetting("escala del brazo (X)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleArmY = sgArm.add(new NumberSetting("escala del brazo (Y)", ".",
            1, 0.01, 5, 0.1));
    public NumberSetting scaleArmZ = sgArm.add(new NumberSetting("escala del brazo (Z)", ".",
            1, 0.01, 5, 0.1));

    public NumberSetting posArmX = sgArm.add(new NumberSetting("posición del brazo (X)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posArmY = sgArm.add(new NumberSetting("posición del brazo (Y)", ".",
            0, -3, 3, 0.1));
    public NumberSetting posArmZ = sgArm.add(new NumberSetting("posición del brazo (Z)", ".",
            0, -3, 3, 0.1));

    public NumberSetting rotArmX = sgArm.add(new NumberSetting("rotación del brazo (X)", ".",
            0, -180, 180, 1));
    public NumberSetting rotArmY = sgArm.add(new NumberSetting("rotación del brazo (Y)", ".",
            0, -180, 180, 1));
    public NumberSetting rotArmZ = sgArm.add(new NumberSetting("rotación del brazo (Z)", ".",
            0, -180, 180, 1));

    public HandView() {
        super("manos",
                "modifica la manera en la que se renderizan las manos",
                Category.PLAYER);
    }

    @SubscribeEvent
    public void onHeldItemRender(RenderHeldItemEvent event) {
        if (event.getHand() == Hand.MAIN_HAND) {
            scale(event.getMatrices(), new Vector3d(scaleMainX.getValue(), scaleMainY.getValue(), scaleMainZ.getValue()));
            translate(event.getMatrices(), new Vector3d(posMainX.getValue(), posMainY.getValue(), posMainZ.getValue()));
            rotate(event.getMatrices(), new Vector3d(rotMainX.getValue(), rotMainY.getValue(), rotMainZ.getValue()));
        }
        else {
            scale(event.getMatrices(), new Vector3d(scaleOffX.getValue(), scaleOffY.getValue(), scaleOffZ.getValue()));
            translate(event.getMatrices(), new Vector3d(posOffX.getValue(), posOffY.getValue(), posOffZ.getValue()));
            rotate(event.getMatrices(), new Vector3d(rotOffX.getValue(), rotOffY.getValue(), rotOffZ.getValue()));
        }
    }

    @SubscribeEvent
    public void onRenderArm(RenderArmEvent event) {
        scale(event.getMatrices(), new Vector3d(scaleArmX.getValue(), scaleArmY.getValue(), scaleArmZ.getValue()));
        translate(event.getMatrices(), new Vector3d(posArmX.getValue(), posArmY.getValue(), posArmZ.getValue()));
        rotate(event.getMatrices(), new Vector3d(rotArmX.getValue(), rotArmY.getValue(), rotArmZ.getValue()));
    }

    private void rotate(MatrixStack matrix, Vector3d rotation) {
        matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) rotation.x));
        matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) rotation.y));
        matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) rotation.z));
    }

    private void scale(MatrixStack matrix, Vector3d scale) {
        matrix.scale((float) scale.x, (float) scale.y, (float) scale.z);
    }

    private void translate(MatrixStack matrix, Vector3d translation) {
        matrix.translate((float) translation.x, (float) translation.y, (float) translation.z);
    }


}
