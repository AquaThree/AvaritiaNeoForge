package net.byAqua3.avaritia.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemInfinityShovel extends ShovelItem {

	public ItemInfinityShovel(Properties properties) {
		super(InfinityTiers.INFINITY, 16, -3.0F, properties);
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
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
		Level level = player.level();
		BlockPos blockPos = player.blockPosition();
		int blockRange = (int) Math.round(8.0D);

		if (stack.hasTag() && stack.getOrCreateTag().getBoolean("destroyer")) {
			List<ItemStack> drops = new ArrayList<>();
			
			for (int x = -blockRange; x <= blockRange; x++) {
				for (int y = -blockRange; y <= blockRange; y++) {
					for (int z = -blockRange; z <= blockRange; z++) {
						BlockPos rangePos = new BlockPos(Mth.floor(blockPos.getX() + x), Mth.floor(blockPos.getY() + y),
								Mth.floor(blockPos.getZ() + z));
						BlockState rangeState = level.getBlockState(rangePos);
						Block rangeBlock = rangeState.getBlock();
						List<TagKey<Block>> tags = rangeState.getTags().toList();
						if (!rangeState.isAir() && tags.contains(BlockTags.MINEABLE_WITH_SHOVEL)) {
							if (!level.isClientSide() && !player.isCreative()) {
								List<ItemStack> blockDrops = Block.getDrops(rangeState, (ServerLevel) level, blockPos, null);
								if(!blockDrops.isEmpty()) {
									drops.addAll(blockDrops);
								} else {
									ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(rangeBlock);
									Item blockItem = BuiltInRegistries.ITEM.get(blockKey);
									drops.add(new ItemStack(blockItem));
								}
							}
							level.destroyBlock(rangePos, false);
						}
					}
				}
			}
			if (!level.isClientSide() && !drops.isEmpty()) {
				ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(),
						blockPos.getZ(), ItemMatterCluster.makeCluster(drops));
				itemEntity.setDefaultPickUpDelay();
				level.addFreshEntity(itemEntity);
			}
		}
		return super.onBlockStartBreak(stack, pos, player);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (player.isCrouching()) {
			if (!level.isClientSide()) {
				stack.getOrCreateTag().putBoolean("destroyer", !stack.getOrCreateTag().getBoolean("destroyer"));
			}
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

}
