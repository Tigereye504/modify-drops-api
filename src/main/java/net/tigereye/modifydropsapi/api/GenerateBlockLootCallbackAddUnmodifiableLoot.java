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

public interface GenerateBlockLootCallbackAddUnmodifiableLoot {
    Event<GenerateBlockLootCallbackAddUnmodifiableLoot> EVENT = EventFactory.createArrayBacked(GenerateBlockLootCallbackAddUnmodifiableLoot.class,
        (listeners) -> (type, context) -> {
            List<ItemStack> loot = new ArrayList<>();
            for (GenerateBlockLootCallbackAddUnmodifiableLoot listener : listeners) {
                loot.addAll(listener.AddUnmodifiableLoot(type, context));
            }
            return loot;
    });

    List<ItemStack> AddUnmodifiableLoot(LootContextType type, LootContext context);
}