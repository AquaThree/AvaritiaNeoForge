package net.byAqua3.avaritia.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemInfinityPickaxe extends PickaxeItem {
	
	private final UUID uuid = UUID.randomUUID();

	public ItemInfinityPickaxe(Properties properties) {
		super(InfinityTiers.INFINITY, 15, -2.8F, properties);
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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		HashMultimap<Attribute, AttributeModifier> hashMultimap = HashMultimap
				.create(super.getAttributeModifiers(slot, stack));
		if(!stack.getAllEnchantments().containsKey(Enchantments.BLOCK_FORTUNE)) {
			stack.enchant(Enchantments.BLOCK_FORTUNE, 10);
		}
		if (slot == EquipmentSlot.MAINHAND && stack.hasTag() && stack.getOrCreateTag().getBoolean("hammer")) {
			hashMultimap.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(uuid, "Tool modifier",
					20.0D, AttributeModifier.Operation.ADDITION));
		}
		return hashMultimap;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
		Level level = player.level();
		BlockPos blockPos = player.blockPosition();
		int blockrange = (int) Math.round(8.0D);

		if (stack.hasTag() && stack.getOrCreateTag().getBoolean("hammer")) {

			List<ItemStack> drops = new ArrayList<>();

			for (int x = -blockrange; x <= blockrange; x++) {
				for (int y = -blockrange; y <= blockrange; y++) {
					for (int z = -blockrange; z <= blockrange; z++) {
						BlockPos rangePos = new BlockPos(Mth.floor(blockPos.getX() + x), Mth.floor(blockPos.getY() + y),
								Mth.floor(blockPos.getZ() + z));
						BlockState rangeState = level.getBlockState(rangePos);
						Block rangeBlock = rangeState.getBlock();
						ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(rangeBlock);
						Item blockItem = BuiltInRegistries.ITEM.get(blockKey);
						drops.add(new ItemStack(blockItem));
						level.destroyBlock(rangePos, false);
					}
				}
			}
			if (!level.isClientSide()) {
				ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
						ItemMatterCluster.makeCluster(drops));
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
				stack.getOrCreateTag().putBoolean("hammer", !stack.getOrCreateTag().getBoolean("hammer"));
			}
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

}
