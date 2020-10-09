package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;

import java.util.ArrayList;
import java.util.List;

/**
 * Callback for killing a dood 
 * Called before item drops are dropped
/**/

public interface GenerateEntityLootCallbackAddLoot {
    Event<GenerateEntityLootCallbackAddLoot> EVENT = EventFactory.createArrayBacked(GenerateEntityLootCallbackAddLoot.class,
            (listeners) -> (type, context) -> {
            List<ItemStack> result = new ArrayList<>();
            for (GenerateEntityLootCallbackAddLoot listener : listeners) {
                result.addAll(listener.AddDrops(type, context));
            }
            return result;
    });

    List<ItemStack> AddDrops(LootContextType type, LootContext context);
}