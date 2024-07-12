package net.byAqua3.avaritia.item;

import java.util.List;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.event.AvaritiaClientEvent;
import net.byAqua3.avaritia.event.AvaritiaEvent;
import net.byAqua3.avaritia.util.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemInfinityArmor extends ArmorItem {

	private final MobEffect[] badEffects = new MobEffect[] { MobEffects.HARM, MobEffects.CONFUSION,
			MobEffects.BLINDNESS, MobEffects.HUNGER, MobEffects.WEAKNESS, MobEffects.POISON, MobEffects.WITHER,
			MobEffects.LEVITATION, MobEffects.UNLUCK, MobEffects.BAD_OMEN, MobEffects.DARKNESS };

	public ItemInfinityArmor(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
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
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		if (this.getEquipmentSlot() == EquipmentSlot.FEET) {
			tooltip.add(TextComponent.getText(""));
			tooltip.add(TextComponent.getText(ChatFormatting.BLUE.toString() + "+"
					+ AvaritiaClientEvent.makeSANIC("SANIC").getString() + ChatFormatting.BLUE.toString() + "% Speed"));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (this.getEquipmentSlot() == EquipmentSlot.HEAD) {
			player.setAirSupply(300);
			player.getFoodData().setFoodLevel(20);
			player.getFoodData().setSaturation(20.0F);
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false));
		} else if (this.getEquipmentSlot() == EquipmentSlot.CHEST) {
			player.setArrowCount(0);
			player.getAbilities().mayfly = true;
			player.getAbilities().setFlyingSpeed(0.05F * 2);
			AvaritiaEvent.setFly = true;
			for (MobEffect effect : badEffects) {
				if (player.hasEffect(effect)) {
					player.removeEffect(effect);
				}
			}
		} else if (this.getEquipmentSlot() == EquipmentSlot.LEGS) {
			player.clearFire();
		} else if (this.getEquipmentSlot() == EquipmentSlot.FEET) {
			player.setMaxUpStep(1.0625F);
			AvaritiaEvent.setMove = true;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return slot != EquipmentSlot.LEGS
				? new ResourceLocation(Avaritia.MODID, "textures/models/armor/infinity_layer_1.png").toString()
				: new ResourceLocation(Avaritia.MODID, "textures/models/armor/infinity_layer_2.png").toString();
	}

}
