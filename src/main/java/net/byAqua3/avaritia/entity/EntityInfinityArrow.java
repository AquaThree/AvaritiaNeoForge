package net.byAqua3.avaritia.entity;

import net.byAqua3.avaritia.loader.AvaritiaEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityInfinityArrow extends Arrow {

	private boolean sub;
	private boolean impacted;

	public EntityInfinityArrow(EntityType<? extends EntityInfinityArrow> entityType, Level level) {
		super(entityType, level);
	}

	public EntityInfinityArrow(Level level, boolean isSub) {
		this(AvaritiaEntities.INFINITY_ARROW.get(), level);
		this.sub = isSub;
	}

	public boolean isSub() {
		return this.sub;
	}

	public void setSub(boolean sub) {
		this.sub = sub;
	}

	public boolean isImpacted() {
		return this.impacted;
	}

	public void setImpacted(boolean impacted) {
		this.impacted = impacted;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		this.setSub(tag.getBoolean("isSub"));
		this.setImpacted(tag.getBoolean("isImpacted"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putBoolean("isSub", this.isSub());
		tag.putBoolean("isImpacted", this.isImpacted());
	}

	@Override
	public void tick() {
		super.tick();
		if (!isSub()) {
			if (this.inGround) {
				if (!this.isImpacted()) {
					if (!this.level().isClientSide()) {
						for (int i = 0; i < 30; i++) {
							double angle = this.level().getRandom().nextDouble() * 2 * Math.PI;
							double dist = this.level().getRandom().nextGaussian() * 0.5;

							double x = Math.sin(angle) * dist + this.getX();
							double z = Math.cos(angle) * dist + this.getZ();
							double y = this.getY() + 25.0;

							double dangle = this.level().getRandom().nextDouble() * 2 * Math.PI;
							double ddist = this.level().getRandom().nextDouble() * 0.35;
							double dx = Math.sin(dangle) * ddist;
							double dz = Math.cos(dangle) * ddist;

							EntityInfinityArrow arrow = new EntityInfinityArrow(this.level(), true);
							Vec3 motion = arrow.getDeltaMovement();
							arrow.setPos(x, y, z);
							arrow.setDeltaMovement(motion.x + dx, motion.y - (this.random.nextDouble() * 1.85 + 0.15),
									motion.z + dz);
							arrow.setBaseDamage(this.getBaseDamage());
							arrow.setCritArrow(true);
							arrow.pickup = this.pickup;
							this.level().addFreshEntity(arrow);
						}
						this.setImpacted(true);
					}

					if (this.inGroundTime >= 100) {
						this.remove(RemovalReason.KILLED);
					}
				}
			}

		} else {
			if (this.inGround && this.inGroundTime >= 20) {
				this.remove(RemovalReason.KILLED);
			}
		}
	}

}
