package me.retucio.sputnik.module.modules.render;

import me.retucio.sputnik.event.SubscribeEvent;
import me.retucio.sputnik.event.events.Render3DEvent;
import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.setting.SettingGroup;
import me.retucio.sputnik.module.setting.settings.BooleanSetting;
import me.retucio.sputnik.module.setting.settings.ColorSetting;
import me.retucio.sputnik.module.setting.settings.ListSetting;
import me.retucio.sputnik.module.setting.settings.NumberSetting;
import me.retucio.sputnik.util.Colors;
import me.retucio.sputnik.util.Lists;
import me.retucio.sputnik.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BlockESP extends Module {

    SettingGroup sgOulines = addSg(new SettingGroup("contorno", true));
    SettingGroup sgFilling = addSg(new SettingGroup("relleno", true));

    public ListSetting<Block> blocks = sgGeneral.add(new ListSetting<>("bloques", "bloques a resaltar",
            Lists.blockList, Lists.allFalse(Lists.blockList), Lists.blockNames));
    public NumberSetting radius = sgGeneral.add(new NumberSetting("radio", "radio de búsqueda de bloques",
            24, 2, 128, 1));

    public BooleanSetting outlines = sgOulines.add(new BooleanSetting("contorno", "renderizar contorno de bloques", true));
    public ColorSetting outlineColor = sgOulines.add(new ColorSetting("color del contorno", "color del contorno", Colors.CELESTE, false));
    public NumberSetting lineWidth = sgOulines.add(new NumberSetting("grosor del contorno", "grosor de las líneas del contorno", 1, 1, 5, 0.2));

    public BooleanSetting fillings = sgFilling.add(new BooleanSetting("relleno", "renderizar relleno de bloques", true));
    public ColorSetting fillingColor = sgFilling.add(new ColorSetting("color del relleno", "color del relleno", new Color(57, 177, 215, 67), false));


    public BlockESP() {
        super("resaltado de bloques",
                "te muestra la posición de cierto tipo de bloque renderizando una caja con su forma",
                Category.RENDER);

        outlines.onUpdate(v -> {
            outlineColor.setVisible(v);
            lineWidth.setVisible(v);
        });

        fillings.onUpdate(v -> fillingColor.setVisible(v));
    }

    @SubscribeEvent
    public void onRenderWorld(Render3DEvent event) {
        if (mc.player == null || mc.world == null) return;

        for (Block block : blocks.getOptions()) {
            if (blocks.isEnabled(block)) {
                List<BlockPos> blocksToHighlight = getNearbyBlocks(block);
                for (BlockPos pos : blocksToHighlight) {
                    if (outlines.isEnabled()) {
                        RenderUtil.drawVoxelShapeOutline(
                                event.getMatrices(),
                                getVoxelShape(pos),
                                pos,
                                outlineColor.getColor(),
                                lineWidth.getFloatValue(),
                                false);
                    }
                    if (fillings.isEnabled()) {
                        RenderUtil.drawVoxelShapeFilled(
                                event.getMatrices(),
                                getVoxelShape(pos),
                                pos,
                                fillingColor.getColor(),
                                false);
                    }
                }
            }
        }
    }

    private List<BlockPos> getNearbyBlocks(Block block) {
        List<BlockPos> blockPosList = new ArrayList<>();

        for (int i = -radius.getIntValue(); i < radius.getIntValue(); i++) {
            for (int j = -radius.getIntValue(); j < radius.getIntValue(); j++) {
                for (int k = -radius.getIntValue(); k < radius.getIntValue(); k++) {
                    BlockPos pos = mc.player.getBlockPos().add(new Vec3i(i, j, k));
                    if (mc.world.getBlockState(pos).getBlock() == block) {
                        blockPosList.add(pos);
                    }
                }
            }
        }
        return blockPosList;
    }

    private VoxelShape getVoxelShape(BlockPos pos) {
        return mc.world.getBlockState(pos).getOutlineShape(mc.world, pos);
    }
}
