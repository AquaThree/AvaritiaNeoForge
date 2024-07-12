package net.byAqua3.avaritia.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
		int blockrange = (int) Math.round(8.0D);

		if (stack.hasTag() && stack.getOrCreateTag().getBoolean("destroyer")) {
			
			List<ItemStack> drops = new ArrayList<>();
			
			for (int x = -blockrange; x <= blockrange; x++) {
				for (int y = -blockrange; y <= blockrange; y++) {
					for (int z = -blockrange; z <= blockrange; z++) {
						BlockPos rangePos = new BlockPos(Mth.floor(blockPos.getX() + x), Mth.floor(blockPos.getY() + y),
								Mth.floor(blockPos.getZ() + z));
						BlockState rangeState = level.getBlockState(rangePos);
						Block rangeBlock = rangeState.getBlock();
						ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(rangeBlock);
						if (blockKey.getPath().contains("dirt") || blockKey.getPath().equalsIgnoreCase("grass_block")) {
							Item blockItem = BuiltInRegistries.ITEM.get(blockKey);
							drops.add(new ItemStack(blockItem));
							level.destroyBlock(rangePos, false);
						}
					}
				}
			}
			if (!level.isClientSide()) {
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
