package me.retucio.sputnik.module.modules.world;

import me.retucio.sputnik.event.SubscribeEvent;
import me.retucio.sputnik.event.events.RenderWorldEvent;
import me.retucio.sputnik.module.Category;
import me.retucio.sputnik.module.Module;
import me.retucio.sputnik.module.settings.BooleanSetting;
import me.retucio.sputnik.module.settings.ColorSetting;
import me.retucio.sputnik.module.settings.NumberSetting;
import me.retucio.sputnik.util.render.RenderUtil;
import net.minecraft.block.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LightOverlay extends Module {

    public NumberSetting radius = addSetting(new NumberSetting("radio", "radio a tener en cuenta al renderizar superposición",
            16, 5, 128, 1));
    public NumberSetting yRadius = addSetting(new NumberSetting("rango vertical", "distancia vertical a tener en cuenta",
            4, 1, 16, 1));

    public NumberSetting interval = addSetting(new NumberSetting("intervalo", "cada cuántos ticks comprobar el nivel de luz",
            10, 1, 80, 1));

    public ColorSetting lightColor = addSetting(new ColorSetting("color de la luz", "color de los bloques con luz",
            new Color(0, 255, 0, 67), false));
    public ColorSetting darknessColor = addSetting(new ColorSetting("color de oscuridad", "color de bloques con 0 luz",
            new Color(255, 0, 0, 67), false));

    public BooleanSetting onWater = addSetting(new BooleanSetting("en agua", "mostrar también bloques cubiertos en agua", true));

    List<BlockPos> blocks = new ArrayList<>();

    public LightOverlay() {
        super("superposición de luz",
                "te muestra el nivel de luz en bloques, para prevenir mob spawns",
                Category.WORLD);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.age % interval.getIntValue() == 0) {
            blocks = findExposedBlocks(
                    radius.getIntValue(),
                    yRadius.getIntValue()
            );
        }

        for (BlockPos block : blocks) {
            if (mc.world.getBlockState(block).isAir() || !canSpawnOn(block)) continue;
            if (!mc.world.getBlockState(block).isOpaque()) continue;
            int light = mc.world.getLightLevel(LightType.BLOCK, block.up());

            Color color = light == 0 ? darknessColor.getColor()
                    : new Color(
                            (lightColor.getR() / 15) * light,
                            (lightColor.getG() / 15) * light,
                            (lightColor.getB() / 15) * light,
                            lightColor.getA());

            RenderUtil.drawBlockFaceFilled(event.getMatrices(), block, Direction.UP, color, 0.001f, true);
        }
    }

    private List<BlockPos> findExposedBlocks(int radius, int yRadius) {
        List<BlockPos> results = new ArrayList<>();

        BlockPos pos = mc.player.getBlockPos();

        int minX = pos.getX() - radius;
        int maxX = pos.getX() + radius;
        int minY = Math.max(mc.world.getDimension().minY(), pos.getY() - yRadius);
        int maxY = Math.min(mc.world.getDimension().height() - 1, pos.getY() + yRadius);
        int minZ = pos.getZ() - radius;
        int maxZ = pos.getZ() + radius;

        maxY = Math.min(maxY, mc.world.getDimension().height() - 2);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    BlockPos current = new BlockPos(x, y, z);

                    if (!mc.world.isAir(current) && canSpawnOn(current))
                        results.add(current);
                }
            }
        }

        return results;
    }

    private boolean canSpawnOn(BlockPos pos) {
        BlockState aboveState = mc.world.getBlockState(pos.up());
        Block aboveBlock = aboveState.getBlock();

        if (aboveState.isAir()) return true;
        if (aboveBlock == Blocks.WATER && onWater.isEnabled()) return true;

        if (aboveBlock instanceof PlantBlock) return true;

        if (!aboveState.isOpaque() && !aboveState.isFullCube(mc.world, pos.up())) {

            if (aboveBlock instanceof SlabBlock
                    || aboveBlock instanceof StairsBlock
                    || aboveBlock instanceof ButtonBlock
                    || aboveBlock instanceof CarpetBlock
                    || aboveBlock instanceof PressurePlateBlock
                    || aboveBlock instanceof PaneBlock
                    || aboveBlock instanceof TrapdoorBlock
                    || aboveBlock instanceof FenceBlock
                    || aboveBlock instanceof WallBlock
                    || aboveBlock instanceof AnvilBlock
                    || aboveBlock instanceof BedBlock) {
                return false;
            }

            if (aboveBlock == Blocks.CHEST ||
                    aboveBlock == Blocks.ENDER_CHEST ||
                    aboveBlock == Blocks.BARREL ||
                    aboveBlock == Blocks.TRAPPED_CHEST) {
                return false;
            }

            return aboveBlock != Blocks.ENCHANTING_TABLE
                    && aboveBlock != Blocks.GRINDSTONE
                    && aboveBlock != Blocks.STONECUTTER
                    && aboveBlock != Blocks.LOOM
                    && aboveBlock != Blocks.COMPOSTER
                    && aboveBlock != Blocks.CAKE;
        }

        return true;
    }
}
