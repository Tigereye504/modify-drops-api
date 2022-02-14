package net.tigereye.modifydropsapi.internal;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.tigereye.modifydropsapi.api.*;

import java.util.ArrayList;
import java.util.List;

public class Listeners {
    public static void register(){
        GenerateLootCallbackAddLoot.EVENT.register(Listeners::AddLootSubEventListener);
        GenerateLootCallbackModifyLoot.EVENT.register(Listeners::ModifyLootSubEventListener);
        GenerateLootCallbackAddUnmodifiableLoot.EVENT.register(Listeners::AddUnmodifiableLootSubEventListener);

        //simple test listener, adds a diamond to all loot tables
        //GenerateBlockLootCallbackAddLoot.EVENT.register((type, context) -> {
        //	List<ItemStack> oneDiamondList = new ArrayList<>();
        //	oneDiamondList.add(new ItemStack(Items.DIAMOND));
        //	return oneDiamondList;
        //});
    }

    private static List<ItemStack> AddLootSubEventListener(LootContextType type, LootContext context) {
        List<ItemStack> loot = new ArrayList<>();
        if(type == LootContextTypes.BLOCK){
            loot.addAll(GenerateBlockLootCallbackAddLoot.EVENT.invoker().AddDrops(type,context));
        }
        else if(type == LootContextTypes.ENTITY){
            loot.addAll(GenerateEntityLootCallbackAddLoot.EVENT.invoker().AddDrops(type,context));
        }
        return loot;
    }

    private static List<ItemStack> AddUnmodifiableLootSubEventListener(LootContextType type, LootContext context) {
        List<ItemStack> loot = new ArrayList<>();
        if(type == LootContextTypes.BLOCK){
            loot.addAll(GenerateBlockLootCallbackAddUnmodifiableLoot.EVENT.invoker().AddUnmodifiableLoot(type,context));
        }
        else if(type == LootContextTypes.ENTITY){
            loot.addAll(GenerateEntityLootCallbackAddUnmodifiableLoot.EVENT.invoker().AddUnmodifiableLoot(type,context));
        }
        return loot;
    }

    private static List<ItemStack> ModifyLootSubEventListener(LootContextType type, LootContext context, List<ItemStack> loot) {
        if(type == LootContextTypes.BLOCK){
            loot = GenerateBlockLootCallbackModifyLoot.EVENT.invoker().ModifyLoot(type,context,loot);
        }
        else if(type == LootContextTypes.ENTITY){
            loot = GenerateEntityLootCallbackModifyLoot.EVENT.invoker().ModifyLoot(type,context,loot);
        }
        return loot;
    }


}
