package net.tigereye.modifydropsapi.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tigereye.modifydropsapi.api.BlockDropStacksCallback_AddDrops;
import net.tigereye.modifydropsapi.api.BlockDropStacksCallback_AddUnmodifiableDrops;
import net.tigereye.modifydropsapi.api.BlockDropStacksCallback_ModifyDrops;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class BlockDropStacksMixin {

    @Inject(
            at = @At("RETURN"),
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            cancellable = true)
    private static void getDroppedStacksMixin(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> ci) {
    //public static List<ItemStack> getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        //LootContext.Builder builder = (new LootContext.Builder(world)).random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, stack).optionalParameter(LootContextParameters.THIS_ENTITY, entity).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
        //List<ItemStack> stacksToDrop = state.getDroppedStacks(builder);
        List<ItemStack> stacksToDrop = ci.getReturnValue();
        stacksToDrop.addAll(BlockDropStacksCallback_AddDrops.EVENT.invoker().AddDrops(state, world, pos, blockEntity, entity, stack));
        stacksToDrop = BlockDropStacksCallback_ModifyDrops.EVENT.invoker().ModifyDrops(state, world, pos, blockEntity, entity, stack, stacksToDrop);
        stacksToDrop.addAll(BlockDropStacksCallback_AddUnmodifiableDrops.EVENT.invoker().AddUnmodifiableDrops(state, world, pos, blockEntity, entity, stack));
        ci.setReturnValue(stacksToDrop);
        //return stacksToDrop;
    }

}