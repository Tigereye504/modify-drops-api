package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;

import java.util.ArrayList;
import java.util.List;

/**
 * Callback for breaking a block 
 * Called before item drops are calculated
/**/

public interface GenerateLootCallbackAddUnmodifiableLoot {
    Event<GenerateLootCallbackAddUnmodifiableLoot> EVENT = EventFactory.createArrayBacked(GenerateLootCallbackAddUnmodifiableLoot.class,
        (listeners) -> (type, context) -> {
            List<ItemStack> loot = new ArrayList<>();
            for (GenerateLootCallbackAddUnmodifiableLoot listener : listeners) {
                loot.addAll(listener.AddDrops(type, context));
            }
            return loot;
    });

    List<ItemStack> AddDrops(LootContextType type, LootContext context);
}