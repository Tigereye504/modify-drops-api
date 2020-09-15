package net.tigereye.modifydropsapi.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.tigereye.modifydropsapi.api.LivingEntityDropLootCallback_AddDrops;
import net.tigereye.modifydropsapi.api.LivingEntityDropLootCallback_AddUnmodifiableDrops;
import net.tigereye.modifydropsapi.api.LivingEntityDropLootCallback_ModifyDrops;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityDropLootMixin {
    
    @Shadow
    protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source){return null;}

    @Inject(
            at = @At("HEAD"),
            method = "dropLoot",
            cancellable = true)
    private void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo info){
        
        Identifier identifier = ((LivingEntity) (Object) this).getLootTable();
        LootTable lootTable = ((LivingEntity) (Object) this).world.getServer().getLootManager().getTable(identifier);
        LootContext.Builder builder = /*((LivingEntity) (Object))*/ this.getLootContextBuilder(causedByPlayer, source);
        List<ItemStack> loot = lootTable.generateLoot(builder.build(LootContextTypes.ENTITY));

        loot.addAll(LivingEntityDropLootCallback_AddDrops.EVENT.invoker().AddDrops(((LivingEntity) (Object) this), source, causedByPlayer));
        loot = LivingEntityDropLootCallback_ModifyDrops.EVENT.invoker().ModifyDrops(((LivingEntity) (Object) this), source, causedByPlayer, loot);
        loot.addAll(LivingEntityDropLootCallback_AddUnmodifiableDrops.EVENT.invoker().AddUnmodifiableDrops(((LivingEntity) (Object) this), source, causedByPlayer));
        //loot = result;//.getValue();
        loot.forEach((stackToDrop) -> {
            ((LivingEntity) (Object) this).dropStack(stackToDrop);
        });

        info.cancel();
    }
}