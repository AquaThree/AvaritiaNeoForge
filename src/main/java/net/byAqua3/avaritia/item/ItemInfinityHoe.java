package net.byAqua3.avaritia.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemInfinityHoe extends HoeItem {

	public ItemInfinityHoe(Properties properties) {
		super(InfinityTiers.INFINITY, 29, 0.0F, properties);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity itemEntity) {
		itemEntity.setInvulnerable(true);
		return super.onEntityItemUpdate(stack, itemEntity);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		Player player = context.getPlayer();
		if (player.isCrouching()) {
			return super.useOn(context);
		} else {
			int blockrange = (int) Math.round(4.0D);

			for (int x = -blockrange; x <= blockrange; x++) {
				for (int z = -blockrange; z <= blockrange; z++) {
					BlockPos rangePos = new BlockPos(Mth.floor(blockPos.getX() + x), Mth.floor(blockPos.getY()),
							Mth.floor(blockPos.getZ() + z));
					BlockState rangeState = level.getBlockState(rangePos).getToolModifiedState(context,
							net.neoforged.neoforge.common.ToolActions.HOE_TILL, false);

					if (rangeState != null) {
						level.playSound(player, rangePos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
						if (!level.isClientSide) {
							level.setBlock(rangePos, rangeState, 11);
							level.gameEvent(GameEvent.BLOCK_CHANGE, rangePos, GameEvent.Context.of(player, rangeState));
						}
					}
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

}
