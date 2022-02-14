package net.tigereye.modifydropsapi.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.tigereye.modifydropsapi.ModifyDropsAPI;
import net.tigereye.modifydropsapi.api.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public class LootTableGenerateLootMixin {

    List<ItemStack> interceptedLoot = new ArrayList<>();
    Consumer<ItemStack> interceptedConsumer;

    /*
    @Overwrite
    public void generateLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
        List<ItemStack> loot = new ArrayList<>();
        ((LootTable)(Object)this).generateUnprocessedLoot(context, loot::add);
        ModifyDropsAPI.LOGGER.info("modifyDropsAPI is modifying "+loot.size()+" drops");
        loot.addAll(GenerateLootCallbackAddLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        loot = GenerateLootCallbackModifyLoot.EVENT.invoker().ModifyDrops(((LootTable)(Object)this).getType(),context, loot);
        loot.addAll(GenerateLootCallbackAddUnmodifiableLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        ModifyDropsAPI.LOGGER.debug(loot.size() + " itemStacks returned");
        Consumer<ItemStack> processedConsumer = LootTable.processStacks(lootConsumer);
        for (ItemStack stack:
             loot) {
            processedConsumer.accept(stack);
        }
    }
    */

    @ModifyVariable(method = "generateUnprocessedLoot",  at = @At("HEAD"), ordinal = 0)
    public Consumer<ItemStack> generateUnprocessedLootMixin_InterceptConsumerReplaceWithList(Consumer<ItemStack> lootConsumer){
        interceptedLoot.clear();
        interceptedConsumer = lootConsumer;
        return interceptedLoot::add;
    }

    @Inject(method = "generateUnprocessedLoot", at = @At("TAIL"))
    public void generateUnprocessedLootMixin_ModifyPopulatedListAndFeedConsumer(LootContext context, Consumer<ItemStack> lootConsumer, CallbackInfo ci){
        ModifyDropsAPI.LOGGER.debug("modifyDropsAPI is modifying "+interceptedLoot.size()+" drops");
        interceptedLoot.addAll(GenerateLootCallbackAddLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        interceptedLoot = GenerateLootCallbackModifyLoot.EVENT.invoker().ModifyDrops(((LootTable)(Object)this).getType(),context, interceptedLoot);
        interceptedLoot.addAll(GenerateLootCallbackAddUnmodifiableLoot.EVENT.invoker().AddDrops(((LootTable)(Object)this).getType(),context));
        ModifyDropsAPI.LOGGER.debug(interceptedLoot.size() + " itemStacks returned");
        Consumer<ItemStack> processedConsumer = LootTable.processStacks(interceptedConsumer);
        for (ItemStack stack:
                interceptedLoot) {
            processedConsumer.accept(stack);
        }
    }

}
