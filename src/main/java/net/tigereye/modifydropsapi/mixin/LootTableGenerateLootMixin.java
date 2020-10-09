package net.tigereye.modifydropsapi.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.tigereye.modifydropsapi.ModifyDropsAPI;
import net.tigereye.modifydropsapi.api.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public class LootTableGenerateLootMixin {
    @Overwrite
    public void generateLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
        List<ItemStack> loot = new ArrayList<>();
        ((LootTable)(Object)this).generateUnprocessedLoot(context, loot::add);
        ModifyDropsAPI.LOGGER.info("modifyDropsAPI is modifying "+loot.size()+" drops");
        //ModifyDropsAPI.LOGGER.debug("entity "+((LivingEntity) (Object) this).getName().asString()+" generated "+loot.size()+" itemStacks before modifyDropsAPI");
        loot.addAll(GenerateLootCallbackAddLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        //ModifyDropsAPI.LOGGER.debug("entity "+((LivingEntity) (Object) this).getName().asString()+" generated "+loot.size()+" itemStacks after addDrops");
        loot = GenerateLootCallbackModifyLoot.EVENT.invoker().ModifyDrops(((LootTable)(Object)this).getType(),context, loot);
        //ModifyDropsAPI.LOGGER.debug("entity "+((LivingEntity) (Object) this).getName().asString()+" generated "+loot.size()+" itemStacks after modifyDrops");
        loot.addAll(GenerateLootCallbackAddUnmodifiableLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        ModifyDropsAPI.LOGGER.debug(loot.size() + " itemStacks returned");
        Consumer<ItemStack> processedConsumer = LootTable.processStacks(lootConsumer);
        for (ItemStack stack:
             loot) {
            processedConsumer.accept(stack);
        }
    }
}
