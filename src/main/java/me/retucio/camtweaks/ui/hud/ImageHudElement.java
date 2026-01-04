package me.retucio.camtweaks.ui.hud;

import me.retucio.camtweaks.CameraTweaks;
import me.retucio.camtweaks.module.modules.client.HUD;
import me.retucio.camtweaks.util.Colors;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.function.Supplier;

public abstract class ImageHudElement extends HudElement {

    protected Identifier textureId;
    protected int imageWidth = 64;
    protected int imageHeight = 64;
    protected boolean textureLoaded = false;
    protected NativeImageBackedTexture texture;

    public ImageHudElement(String id, int defaultX, int defaultY) {
        super(id, defaultX, defaultY);
        this.textureId = Identifier.of(CameraTweaks.MOD_ID, "textures/" + id);
        this.w = imageWidth;
        this.h = imageHeight;
    }

    protected abstract String getImagePath();

    public void reloadTexture() {
        textureLoaded = false;
        String imagePath = getImagePath();
        if (imagePath == null || imagePath.isEmpty()) return;

        try {
            // cargar
            Identifier resourceId = Identifier.of(CameraTweaks.MOD_ID, imagePath);
            try (InputStream stream = mc.getResourceManager().getResource(resourceId).get().getInputStream()) {
                NativeImage image = NativeImage.read(stream);

                // deshacerse de la textura vieja
                if (texture != null) texture.close();
                if (mc.getTextureManager().getTexture(textureId) != null)
                    mc.getTextureManager().destroyTexture(textureId);

                // crear la textura nueva
                Supplier<String> nameSupplier = () -> textureId.toString();
                texture = new NativeImageBackedTexture(nameSupplier, image);

                this.imageWidth = image.getWidth();
                this.imageHeight = image.getHeight();
                this.w = imageWidth;
                this.h = imageHeight;

                mc.getTextureManager().registerTexture(textureId, texture);
                textureLoaded = true;

                CameraTweaks.LOGGER.info("textura para el elemento del HUD {} cargada", getId());
            }
        } catch (Exception e) {
            CameraTweaks.LOGGER.error("no se pudo cargar la textura para el elemento del HUD {}: {}", getId(), e.getMessage());
        }
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
                imageWidth, imageHeight
        );
    }

    @Override
    public void renderInEditor(DrawContext ctx, HUD hud) {
        w = imageWidth;
        h = imageHeight;

        drawEditorBackground(ctx);

        if (textureLoaded) {
            ctx.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    textureId,
                    x, y,
                    0, 0,
                    imageWidth, imageHeight,
                    imageWidth, imageHeight
            );
        } else {
            String placeholder = "imagen: " + getId();
            int textWidth = mc.textRenderer.getWidth(placeholder);
            int textX = x + Math.max(0, (w - textWidth) / 2);
            int textY = y + Math.max(0, (h - mc.textRenderer.fontHeight) / 2);
            ctx.drawText(mc.textRenderer, placeholder, textX, textY, -1, true);
        }
    }

    protected void drawEditorBackground(DrawContext ctx) {
        int bgColor = visible ? Colors.visibleHudElementColor.getRGB() : Colors.disabledHudElementColor.getRGB();
        int outlineColor = HudEditorScreen.INSTANCE != null && HudEditorScreen.INSTANCE.isSelected(this)
                ? Colors.selectedHudElementOutlineColor.getRGB()
                : Colors.unselectedHudElementOutlineColor.getRGB();

        // fondo
        ctx.fill(x - 1, y - 1, x + w + 1, y + h + 1, bgColor);

        // contorno
        ctx.fill(x - 1, y - 1, x + w + 1, y, outlineColor);
        ctx.fill(x - 1, y + h, x + w + 1, y + h + 1, outlineColor);
        ctx.fill(x - 1, y, x, y + h, outlineColor);
        ctx.fill(x + w, y, x + w + 1, y + h, outlineColor);
    }
}