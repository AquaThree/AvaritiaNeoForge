package net.byAqua3.avaritia.damage;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class InfinityDamageSource extends DamageSource {

	public InfinityDamageSource(Holder<DamageType> damageType, Entity entity, Entity attacker) {
		super(damageType, entity);
	}

	@Override
	public Component getLocalizedDeathMessage(LivingEntity attacked) {
		LivingEntity entity = attacked.getKillCredit();
		String key = "death.attack.infinity." + attacked.level().getRandom().nextInt(1, 5);
		return Component.translatable(key, attacked.getDisplayName(), entity.getDisplayName());
	}

}
