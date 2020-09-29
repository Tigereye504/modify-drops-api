package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Callback for killing a dood 
 * Called before item drops are dropped
/**/

public interface LivingEntityDropLootCallback_AddUnmodifiableDrops {
    Event<LivingEntityDropLootCallback_AddUnmodifiableDrops> EVENT = EventFactory.createArrayBacked(LivingEntityDropLootCallback_AddUnmodifiableDrops.class,
            (listeners) -> (entity, source, causedByPlayer) -> {
            List<ItemStack> result = new ArrayList<>();
            for (LivingEntityDropLootCallback_AddUnmodifiableDrops listener : listeners) {
                result.addAll(listener.AddUnmodifiableDrops(entity, source, causedByPlayer));
            }
            return result;
    });

    List<ItemStack> AddUnmodifiableDrops(LivingEntity entity, DamageSource source, boolean causedByPlayer);
}