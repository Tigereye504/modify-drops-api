package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Callback for breaking a block 
 * Called before item drops are calculated
/**/

public interface BlockDropStacksCallback_ModifyDrops {
    Event<BlockDropStacksCallback_ModifyDrops> EVENT = EventFactory.createArrayBacked(BlockDropStacksCallback_ModifyDrops.class,
        (listeners) -> (state, world, pos, blockEntity, entity, stack, stacksToDrop) -> {
            for (BlockDropStacksCallback_ModifyDrops listener : listeners) {
                stacksToDrop = listener.ModifyDrops(state, world, pos, blockEntity, entity, stack, stacksToDrop);
            }
            return stacksToDrop;
    });

    List<ItemStack> ModifyDrops(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, List<ItemStack> stacksToDrop);
}