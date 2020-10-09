package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;

import java.util.List;

/**
 * Callback for breaking a block 
 * Called before item drops are calculated
/**/

public interface GenerateLootCallbackModifyLoot {
    Event<GenerateLootCallbackModifyLoot> EVENT = EventFactory.createArrayBacked(GenerateLootCallbackModifyLoot.class,
        (listeners) -> (type, context, loot) -> {
            for (GenerateLootCallbackModifyLoot listener : listeners) {
                loot = listener.ModifyDrops(type, context,loot);
            }
            return loot;
    });

    List<ItemStack> ModifyDrops(LootContextType type, LootContext context, List<ItemStack> loot);
}