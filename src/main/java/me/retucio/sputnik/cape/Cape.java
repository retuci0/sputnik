package me.retucio.sputnik.cape;

import me.retucio.sputnik.Sputnik;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class Cape {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final Identifier id;
    private NativeImage img;
    private NativeImageBackedTexture texture;

    public Cape(String name) {
        this.name = name;
        this.id = Identifier.of(Sputnik.MOD_ID, "textures/capes/" + name + ".png");
    }

    public void load() {
        this.img = loadTexture(this.id);
    }

    public NativeImage loadTexture(Identifier id) {
        try (InputStream inputStream = mc.getResourceManager().getResource(id).get().getInputStream()) {
            return NativeImage.read(inputStream);
        } catch (IOException e) {
            Sputnik.LOGGER.error("ups: {}", e.getMessage());
            return null;
        }
    }

    public void register() {
        texture = new NativeImageBackedTexture(null, img);
        mc.getTextureManager().registerTexture(id, texture);
        img = null;
    }

    public String getName() {
        return name;
    }

    public Identifier getId() {
        return id;
    }

    public NativeImageBackedTexture getTexture() {
        return texture;
    }

    @Override
    public String toString() {
        return name.replace('-', ' ');
    }
}