package net.tigereye.modifydropsapi.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Callback for breaking a block 
 * Called before item drops are calculated
/**/

public interface BlockDropStacksCallback_AddUnmodifiableDrops {
    Event<BlockDropStacksCallback_AddUnmodifiableDrops> EVENT = EventFactory.createArrayBacked(BlockDropStacksCallback_AddUnmodifiableDrops.class,
        (listeners) -> (state, world, pos, blockEntity, entity, stack) -> {
            List<ItemStack> stacksToDrop = new ArrayList<>();
            for (BlockDropStacksCallback_AddUnmodifiableDrops listener : listeners) {
                stacksToDrop = listener.AddUnmodifiableDrops(state, world, pos, blockEntity, entity, stack);
            }
            return stacksToDrop;
    });

    List<ItemStack> AddUnmodifiableDrops(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack);
}