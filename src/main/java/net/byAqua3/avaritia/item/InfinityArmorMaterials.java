package net.byAqua3.avaritia.item;

import com.mojang.serialization.Codec;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("deprecation")
public enum InfinityArmorMaterials implements StringRepresentable, ArmorMaterial {

    INFINITY("infinity", 0, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266649_ -> {
        p_266649_.put(ArmorItem.Type.BOOTS, 6);
        p_266649_.put(ArmorItem.Type.LEGGINGS, 16);
        p_266649_.put(ArmorItem.Type.CHESTPLATE, 12);
        p_266649_.put(ArmorItem.Type.HELMET, 6);
    }), 0, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0F, 9999.0F, () -> Ingredient.EMPTY);

    public static final Codec<InfinityArmorMaterials> CODEC = StringRepresentable.fromEnum(InfinityArmorMaterials::values);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), p_266653_ -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;

	private final LazyLoadedValue<Ingredient> repairIngredient;

    private InfinityArmorMaterials(
        String pName,
        int pDurabilityMultiplier,
        EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType,
        int pEnchantmentValue,
        SoundEvent pSound,
        float pToughness,
        float pKnockbackResistance,
        Supplier<Ingredient> pRepairIngredient
    ) {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.protectionFunctionForType = pProtectionFunctionForType;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
