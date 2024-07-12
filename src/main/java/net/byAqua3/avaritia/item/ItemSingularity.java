package net.byAqua3.avaritia.item;

import java.awt.Color;

import net.minecraft.world.item.Item;

public class ItemSingularity extends Item {
	
	private Color color;
	private Color layerColor;

	public ItemSingularity(Properties properties, int color, int layerColor) {
		super(properties);
		this.color = new Color(color);
		this.layerColor = new Color(layerColor);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Color getLayerColor() {
		return this.layerColor;
	}
}
