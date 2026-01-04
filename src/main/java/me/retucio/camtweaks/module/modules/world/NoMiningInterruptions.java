package me.retucio.camtweaks.module.modules.world;

import me.retucio.camtweaks.module.Category;
import me.retucio.camtweaks.module.Module;
import me.retucio.camtweaks.module.settings.BooleanSetting;
import me.retucio.camtweaks.module.settings.ListSetting;
import me.retucio.camtweaks.util.ChatUtil;
import me.retucio.camtweaks.util.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.ItemTags;

public class NoMiningInterruptions extends Module {

    public BooleanSetting withPickaxeOnly = addSetting(new BooleanSetting("solo con pico", "ignorar entidades solamente cuando se sujeta un pico", false));
    public ListSetting<EntityType<?>> entities = addSetting(new ListSetting<>("entidades", "entidades a ignorar",
            Lists.entityList, Lists.allTrue(Lists.entityList), Lists.entityNames));

    public NoMiningInterruptions() {
        super("minar sin interrupción",
                "te permite minar bloques a través de entidades",
                Category.WORLD);
    }

    public boolean shouldIgnoreEntity(Entity entity) {
        if (entity == null || !isEnabled() || mc.player == null) return false;

        boolean pickaxe = true;
        if (withPickaxeOnly.isEnabled())
            pickaxe = (mc.player.getMainHandStack().isIn(ItemTags.PICKAXES)
                    || mc.player.getOffHandStack().isIn(ItemTags.PICKAXES));

        return entities.isEnabled(entity.getType()) && pickaxe;
    }
}
