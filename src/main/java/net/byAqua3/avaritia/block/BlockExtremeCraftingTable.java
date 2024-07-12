package net.byAqua3.avaritia.block;

import net.byAqua3.avaritia.inventory.MenuExtremeCrafting;
import net.byAqua3.avaritia.tile.TileExtremeCraftingTable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockExtremeCraftingTable extends Block implements EntityBlock {

	public BlockExtremeCraftingTable(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileExtremeCraftingTable(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof TileExtremeCraftingTable) {
				TileExtremeCraftingTable tile = (TileExtremeCraftingTable) blockEntity;
				SimpleMenuProvider simpleMenuProvider = new SimpleMenuProvider(
						(id, inventory, access) -> new MenuExtremeCrafting(id, inventory, tile), Component.empty());

				player.openMenu(simpleMenuProvider, pos);
			}
			return InteractionResult.CONSUME;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);

			if (blockEntity instanceof TileExtremeCraftingTable) {
				TileExtremeCraftingTable tile = (TileExtremeCraftingTable) blockEntity;
				NonNullList<ItemStack> itemStacks = tile.matrix.getItems();
				for (ItemStack itemStack : itemStacks) {
					if (!itemStack.isEmpty()) {
						ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
						itemEntity.setDefaultPickUpDelay();
						level.addFreshEntity(itemEntity);
					}
				}
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

}
