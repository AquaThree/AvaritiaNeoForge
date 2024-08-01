package net.byAqua3.avaritia.item;

import java.awt.Color;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class ItemSingularity extends ItemHalo {
	
	private Color color;
	private Color layerColor;

	public ItemSingularity(Properties properties, int color, int layerColor) {
		super(properties, 2);
		this.color = new Color(color);
		this.layerColor = new Color(layerColor);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Color getLayerColor() {
		return this.layerColor;
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
}
