package net.byAqua3.avaritia.item;

import java.util.List;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.byAqua3.avaritia.loader.AvaritiaItems;
import net.byAqua3.avaritia.loader.AvaritiaTabs;
import net.byAqua3.avaritia.util.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemMatterCluster extends Item {

	private static final int INTERNAL_INV_SIZE = 512;

	public static final int CAPACITY = 4096;

	public ItemMatterCluster(Properties properties) {
		super(properties.stacksTo(1));
		AvaritiaTabs.BLACK_ITEMS.add(this);
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

	private static ListTag writeItemStacksToTag(NonNullList<ItemStack> items) {
		ListTag tagList = new ListTag();
		for (ItemStack item : items) {
			if (!item.isEmpty()) {
				tagList.add(item.save(new CompoundTag()));
			}
		}
		return tagList;
	}

	private static void readItemStacksFromTag(NonNullList<ItemStack> items, ListTag tagList) {
		for (int i = 0; i < tagList.size(); ++i) {
			items.set(i, ItemStack.of(tagList.getCompound(i)));
		}
	}

	private static void writeClusterInventory(ItemStack cluster, SimpleContainer clusterContents) {
		CompoundTag nbt = cluster.getOrCreateTag();
		nbt.put("items", writeItemStacksToTag(clusterContents.getItems()));
	}

	private static SimpleContainer readClusterInventory(ItemStack cluster) {
		SimpleContainer clusterInventory = new SimpleContainer(INTERNAL_INV_SIZE);
		if (cluster.hasTag()) {
			readItemStacksFromTag(clusterInventory.getItems(),
					cluster.getOrCreateTag().getList("items", Tag.TAG_COMPOUND));
		}
		return clusterInventory;
	}

	public static ItemStack makeCluster(List<ItemStack> input) {
		SimpleContainer clusterInventory = new SimpleContainer(INTERNAL_INV_SIZE);
		int count = 0;
		for (ItemStack stack : input) {
			if (count < CAPACITY) {
				if (clusterInventory.canAddItem(stack)) {
					clusterInventory.addItem(stack.copy());
					count += stack.getCount();
					stack.setCount(0);
				}
			}
		}
		if (count > 0) {
			ItemStack cluster = new ItemStack(AvaritiaItems.MATTER_CLUSTER.get());
			writeClusterInventory(cluster, clusterInventory);
			return cluster;
		}
		return ItemStack.EMPTY;
	}

	public static List<ItemStack> getClusterItems(ItemStack cluster) {
		SimpleContainer clusterInventory = readClusterInventory(cluster);
		return clusterInventory.getItems();
	}

	public static int getClusterCount(List<ItemStack> itemStacks) {
		int itemCount = 0;

		for (ItemStack itemStack : itemStacks) {
			if (!itemStack.isEmpty()) {
				itemCount += itemStack.getCount();
			}
		}
		return itemCount;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		if (!stack.hasTag() || !stack.getOrCreateTag().contains("items", Tag.TAG_LIST)) {
			return;
		}

		List<ItemStack> itemStacks = getClusterItems(stack);

		if (getClusterCount(itemStacks) > 0) {
			tooltip.add(Component.translatable("tooltip.matter_cluster.counter", getClusterCount(itemStacks), CAPACITY));
			tooltip.add(TextComponent.getText(""));
		}

		if (Screen.hasShiftDown()) {
			Object2IntMap<Item> object2IntMap = new Object2IntOpenHashMap<>();
			for (ItemStack itemStack : itemStacks) {
				if (!itemStack.isEmpty()) {
					object2IntMap.put(itemStack.getItem(),
							itemStack.getCount() + object2IntMap.getOrDefault(itemStack.getItem(), 0));
				}
			}
			object2IntMap.forEach((item, count) -> {
				tooltip.add(TextComponent
						.getText(item.getDescription().getString() + ChatFormatting.GRAY.toString() + " × " + count));
			});
		} else {
			tooltip.add(Component.translatable("tooltip.matter_cluster.desc").withStyle(ChatFormatting.DARK_GRAY));
			tooltip.add(Component.translatable("tooltip.matter_cluster.desc2").withStyle(ChatFormatting.ITALIC)
					.withStyle(ChatFormatting.DARK_GRAY));
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		List<ItemStack> itemStacks = getClusterItems(stack);

		if (stack.hasTag() && stack.getOrCreateTag().contains("items", Tag.TAG_LIST) && !itemStacks.isEmpty()) {
			if (!level.isClientSide()) {
				for (ItemStack itemStack : itemStacks) {
					ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(),
							itemStack);
					itemEntity.setDefaultPickUpDelay();
					level.addFreshEntity(itemEntity);
				}
			}
			player.setItemInHand(hand, ItemStack.EMPTY);
		}

		return InteractionResultHolder.success(stack);
	}

}
