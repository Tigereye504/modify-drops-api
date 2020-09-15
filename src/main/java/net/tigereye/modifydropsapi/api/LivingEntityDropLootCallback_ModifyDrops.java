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

public interface LivingEntityDropLootCallback_ModifyDrops {
    Event<LivingEntityDropLootCallback_ModifyDrops> EVENT = EventFactory.createArrayBacked(LivingEntityDropLootCallback_ModifyDrops.class,
            (listeners) -> (entity, source, causedByPlayer, loot) -> {
            List<ItemStack> result = new ArrayList<ItemStack>();
            for (LivingEntityDropLootCallback_ModifyDrops listener : listeners) {
                result = listener.ModifyDrops(entity, source, causedByPlayer, loot);
            }
            return result;
    });

    List<ItemStack> ModifyDrops(LivingEntity entity, DamageSource source, boolean causedByPlayer, List<ItemStack> loot);
}