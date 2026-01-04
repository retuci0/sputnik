package me.retucio.camtweaks.ui.hud.elements;

import me.retucio.camtweaks.module.modules.client.HUD;
import me.retucio.camtweaks.ui.hud.ImageHudElement;
import me.retucio.camtweaks.util.Colors;
import me.retucio.camtweaks.util.InventoryUtil;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EchestElement extends ImageHudElement {

    public EchestElement() {
        super("echest", 10, 50);
        reloadTexture();
    }

    @Override
    protected String getImagePath() {
        return "textures/gui/preview-hud.png";
    }

    @Override
    public void renderInGame(DrawContext ctx, float delta, HUD hud) {
        if (!textureLoaded || !isVisible()) return;

        ctx.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                textureId,
                x, y,
                0, 0,
                imageWidth, imageHeight,
                imageWidth, imageHeight,
                Colors.PURPLE.getRGB()
        );

        ctx.drawText(
                mc.textRenderer,
                "echest",
                x + w / 2 - mc.textRenderer.getWidth("echest") / 2,
                y + mc.textRenderer.fontHeight - 3,
                Colors.instructionsTextColor.getRGB(),
                false
        );

        renderItems(ctx, InventoryUtil.getEchestInv());
    }

    @Override
    public void renderInEditor(DrawContext ctx, HUD hud) {
        if (!textureLoaded) return;

        drawEditorBackground(ctx);

        ctx.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                textureId,
                x, y,
                0, 0,
                imageWidth, imageHeight,
                imageWidth, imageHeight,
                visible
                        ? Colors.GREEN.getRGB()
                        : Colors.RED.getRGB()
        );

        ctx.drawText(
                mc.textRenderer,
                "echest",
                x + w / 2 - mc.textRenderer.getWidth("echest") / 2,
                y + mc.textRenderer.fontHeight - 3,
                Colors.instructionsTextColor.getRGB(),
                false
        );

        renderItems(ctx, InventoryUtil.getEchestInv());
    }

    @Override
    public List<Text> getTooltip() {
        List<Text> tooltip = new ArrayList<>();
        tooltip.add(Text.literal("echest"));
        tooltip.add(Text.literal("items en tu echest"));
        return tooltip;
    }

    private void renderItems(DrawContext ctx, Inventory inventory) {
        if (inventory == null) return;

        int drawX = x + 7;
        int drawY = y + 17;
        int startX = drawX;

        int count = 0;
        int rows = 0;

        for (ItemStack item : inventory) {
            if (item.isEmpty()) continue;

            ctx.drawItem(item, drawX, drawY);
            ctx.drawStackOverlay(mc.textRenderer, item, drawX, drawY);

            drawX += 18;
            count++;

            if (count % 9 == 0) {
                drawX = startX;
                drawY += 18;
                rows++;

                if (rows >= 3) break;
            }
        }

        int itemsDrawn = Math.min(count, 27);
        int columns = Math.min(itemsDrawn, 9);
        int drawnRows = (int) Math.ceil(itemsDrawn / 9.0);

        w = Math.max(columns * 18, w);
        h = Math.max(drawnRows * 18, h);
    }
}