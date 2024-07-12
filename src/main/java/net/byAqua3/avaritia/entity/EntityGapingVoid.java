package net.byAqua3.avaritia.entity;

import java.util.List;

import net.byAqua3.avaritia.loader.AvaritiaEntities;
import net.byAqua3.avaritia.loader.AvaritiaSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityGapingVoid extends Entity {

	private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(EntityGapingVoid.class,
			EntityDataSerializers.INT);

	public static final int MAX_LIFETIME = 186;

	public static final double COLLAPSE = 0.95D;

	private static final int SUCK_RANGE = 20;
	
	public EntityGapingVoid(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	public EntityGapingVoid(Level level) {
		this(AvaritiaEntities.GAPING_VOID.get(), level);
	}

	public int getAge() {
		return this.entityData.get(AGE).intValue();
	}

	public void setAge(int age) {
		this.entityData.set(AGE, Integer.valueOf(age));
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(AGE, Integer.valueOf(0));
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		this.setAge(tag.getInt("age"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("age", this.getAge());
	}

	private static double ease(double in) {
		double t = in - 1.0D;
		return Math.sqrt(1.0D - t * t);
	}

	public static double getVoidScale(double age) {
		double curve, life = age / 186.0D;
		if (life < 0.95D) {
			curve = 0.005D + ease(1.0D - (0.95D - life) / 0.95D) * 0.995D;
		} else {
			curve = ease(1.0D - (life - 0.95D) / 0.050000000000000044D);
		}
		return 10.0D * curve;
	}

	private boolean shouldSuck(Entity entity) {
		if (entity instanceof EntityEndestPearl) {
			return false;
		}
		if (entity instanceof EntityGapingVoid) {
			return false;
		}
		if (entity instanceof Player) {
			Player player = (Player) entity;
			return (!player.isCreative() || !player.getAbilities().flying);
		}
		return true;
	}

	private boolean shouldAttack(Entity entity) {
		if (!(entity instanceof LivingEntity)) {
			return false;
		}
		if (entity instanceof Player) {
			Player player = (Player) entity;
			return !player.isCreative();
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		super.tick();

		int age = this.getAge();

		if (age == 0) {
			this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), AvaritiaSounds.GAPING_VOID.get(),
					SoundSource.HOSTILE, 8.0F, 1.0F, true);
		}

		this.setAge(age + 1);

		if (!this.level().isClientSide()) {
			Vec3 pos = this.position();
			double voidScale = getVoidScale(age);

			for (int i = 0; i < 50; i++) {
				this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(),
						this.random.nextGaussian() * 3.0D, this.random.nextGaussian() * 3.0D,
						this.random.nextGaussian() * 3.0D);
			}

			double radius = voidScale * 0.5D;

			AABB aabb = AABB.encapsulatingFullBlocks(this.blockPosition().offset(new Vec3i(-SUCK_RANGE, -SUCK_RANGE, -SUCK_RANGE)),
					this.blockPosition().offset(new Vec3i(SUCK_RANGE, SUCK_RANGE, SUCK_RANGE)));
			List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, aabb, this::shouldSuck);

			for (Entity entity : entities) {
				if (entity != this) {
					double posX = pos.x - entity.getX();
					double posY = pos.y - entity.getY();
					double posZ = pos.z - entity.getZ();
					double len = Math.sqrt(posX * posX + posY * posY + posZ * posZ);
					double lenn = len / SUCK_RANGE;
					if (len <= SUCK_RANGE) {
						double strength = (1 - lenn) * (1 - lenn);
	                    double power = 0.075 * radius;

	                    Vec3 motion = entity.getDeltaMovement();
	                    
	                    double motionX = motion.x() + (posX / len) * strength * power;
	                    double motionY = motion.y() + (posY / len) * strength * power;
	                    double motionZ = motion.z() + (posZ / len) * strength * power;
	                    
	                    entity.setDeltaMovement(motionX, motionY, motionZ);
					}
				}
			}

			int attackRange = (int) (radius * 0.95D);

			aabb = AABB.encapsulatingFullBlocks(
					this.blockPosition().offset(new Vec3i(-attackRange, -attackRange, -attackRange)),
					this.blockPosition().offset(new Vec3i(attackRange, attackRange, attackRange)));
			List<LivingEntity> entitiesToAttack = this.level().getEntitiesOfClass(LivingEntity.class, aabb,
					this::shouldAttack);
			for (Entity toAttack : entitiesToAttack) {
				if (toAttack != this) {
					double posX = pos.x - toAttack.getX();
					double posY = pos.y - toAttack.getY();
					double posZ = pos.z - toAttack.getZ();
					double len = Math.sqrt(posX * posX + posY * posY + posZ * posZ);
					if (len <= attackRange) {
						toAttack.hurt(toAttack.damageSources().fellOutOfWorld(), 3.0F);
					}
				}
			}
			attackRange += 2.0D;

			if (age % 10 == 0) {
				Vec3 pos1 = pos;
				int blockrange = (int) Math.round(attackRange);

				for (int y = -blockrange; y <= blockrange; y++) {
					for (int z = -blockrange; z <= blockrange; z++) {
						for (int x = -blockrange; x <= blockrange; x++) {
							Vec3 pos2 = new Vec3(x, y, z);
							Vec3 rPos = pos1.add(pos2);
							BlockPos blockPos = new BlockPos(Mth.floor(rPos.x), Mth.floor(rPos.y), Mth.floor(rPos.z));
							BlockState blockState = this.level().getBlockState(blockPos);
							double dist = Math.sqrt(pos2.x * pos2.x + pos2.y * pos2.y + pos2.z * pos2.z);
							if (dist <= attackRange && !blockState.isAir()) {
								Explosion explosion = new Explosion(this.level(), this, this.getX(), this.getY(),
										this.getZ(), 10.0F, false, Explosion.BlockInteraction.DESTROY,
										List.of(blockPos));
								float resist = blockState.getBlock().getExplosionResistance();
								if (resist <= 10.0F) {
									if (blockState.canDropFromExplosion(this.level(), blockPos, explosion)) {
										this.level().destroyBlock(blockPos, true);
									} else {
										this.level().destroyBlock(blockPos, false);
									}
								}
							}
						}
					}
				}
			}
		}

		if (age >= MAX_LIFETIME) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0F, false,
					Level.ExplosionInteraction.BLOCK);
			this.setAge(0);
			this.remove(RemovalReason.KILLED);
		}
	}

}
