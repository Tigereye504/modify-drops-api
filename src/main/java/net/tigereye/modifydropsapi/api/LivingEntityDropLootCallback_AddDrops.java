package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Callback for killing a dood 
 * Called before item drops are dropped
/**/

public interface LivingEntityDropLootCallback_AddDrops {
    Event<LivingEntityDropLootCallback_AddDrops> EVENT = EventFactory.createArrayBacked(LivingEntityDropLootCallback_AddDrops.class,
            (listeners) -> (entity, source, causedByPlayer) -> {
            List<ItemStack> result = new ArrayList<ItemStack>();
            for (LivingEntityDropLootCallback_AddDrops listener : listeners) {
                result = listener.AddDrops(entity, source, causedByPlayer);
            }
            return result;
    });

    List<ItemStack> AddDrops(LivingEntity entity, DamageSource source, boolean causedByPlayer);
}