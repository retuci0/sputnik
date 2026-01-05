package me.retucio.sputnik.cape;

import java.util.ArrayList;
import java.util.List;

import static me.retucio.sputnik.Sputnik.mc;

public class CapeManager {

    public static CapeManager INSTANCE;

    private final List<Cape> capes = new ArrayList<>();

    public CapeManager() {
        addCapes();
    }

    public void addCapes() {
        addCape("sputnik");
        addCape("half-life");
        addCape("hollow-knight");

        addCape("cherry-blossom");
        addCape("migrator");
        addCape("minecraft");
        addCape("pancakes");
        addCape("vanilla");

        addCape("tiktok");
        addCape("twitch");

        addCape("mojang");
        addCape("mojang-old");
        addCape("mojang-old-old");

        addCape("minecon-2011");
        addCape("minecon-2012");
        addCape("minecon-2013");
        addCape("minecon-2015");
        addCape("minecon-2016");
        addCape("experience-2025");

        addCape("15th-anniversary");
        addCape("bedrock");
        addCape("birthday");
        addCape("copper");
        addCape("eye-blossom");
        addCape("mcc-15");
        addCape("prismarine");
        addCape("turtle");
        addCape("valentine");

        addCape("chinese-translator");
        addCape("cobalt");
        addCape("mojira-mod");
        addCape("millionth-customer");
        addCape("realms-mapmaker");
        addCape("scrolls-champion");
        addCape("translator");

        addCape("cheapsh0t");
        addCape("dannybstyle");
        addCape("julianclark");
        addCape("mrmessiah");

        for (Cape cape : capes) {
            mc.execute(cape::load);
            mc.execute(cape::register);
        }
    }

    private void addCape(String name) {
        capes.add(new Cape(name));
    }

    public Cape getCape(String name) {
        for (Cape cape : capes)
            if (cape.getName().equalsIgnoreCase(name))
                return cape;

        return null;
    }

    public List<Cape> getCapes() {
        return capes;
    }
}