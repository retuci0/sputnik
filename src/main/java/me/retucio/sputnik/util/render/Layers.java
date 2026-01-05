package me.retucio.sputnik.util.render;

import me.retucio.sputnik.Sputnik;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;

import static me.retucio.sputnik.util.render.Pipelines.*;

public class Layers {

    private static final RenderLayer LINES;
    private static final RenderLayer LINES_CULL;

    private static final RenderLayer QUADS;
    private static final RenderLayer QUADS_CULL;


    public static RenderLayer lines() {
        return LINES;
    }

    public static RenderLayer linesCull() {
        return LINES_CULL;
    }

    public static RenderLayer quads() {
        return QUADS;
    }

    public static RenderLayer quadsCull() {
        return QUADS_CULL;
    }

    static {
        LINES = RenderLayer.of(Sputnik.MOD_ID + "_lines", RenderSetup.builder(LINES_PIPELINE).build());
        LINES_CULL = RenderLayer.of(Sputnik.MOD_ID + "_lines_cull", RenderSetup.builder(LINES_CULL_PIPELINE).build());

        QUADS = RenderLayer.of(Sputnik.MOD_ID + "_quads", RenderSetup.builder(QUADS_PIPELINE).build());
        QUADS_CULL = RenderLayer.of(Sputnik.MOD_ID + "quads_cull", RenderSetup.builder(QUADS_CULL_PIPELINE).build());
    }

}