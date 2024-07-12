package net.byAqua3.avaritia.loader;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.entity.EntityEndestPearl;
import net.byAqua3.avaritia.entity.EntityGapingVoid;
import net.byAqua3.avaritia.entity.EntityInfinityArrow;
import net.byAqua3.avaritia.render.RenderGapingVoid;
import net.byAqua3.avaritia.render.RenderInfinityArrow;
import net.byAqua3.avaritia.render.layer.RenderInfinityArmor;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AvaritiaEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(BuiltInRegistries.ENTITY_TYPE, Avaritia.MODID);

	public static final DeferredHolder<EntityType<?>, EntityType<EntityInfinityArrow>> INFINITY_ARROW = ENTITY_TYPES.register("infinity_arrow", () -> EntityType.Builder.<EntityInfinityArrow>of(EntityInfinityArrow::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(20).sized(0.5F, 0.5F).build(new ResourceLocation(Avaritia.MODID, "infinity_arrow").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEndestPearl>> ENDEST_PEARL = ENTITY_TYPES.register("endest_pearl", () -> EntityType.Builder.<EntityEndestPearl>of(EntityEndestPearl::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(10).sized(0.25F, 0.25F).build(new ResourceLocation(Avaritia.MODID, "endest_pearl").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGapingVoid>> GAPING_VOID = ENTITY_TYPES.register("gaping_void", () -> EntityType.Builder.<EntityGapingVoid>of(EntityGapingVoid::new, MobCategory.MISC).clientTrackingRange(16).updateInterval(10).sized(0.1F, 0.1F).build(new ResourceLocation(Avaritia.MODID, "gaping_void").toString()));

	public static void registerEntities(IEventBus modEventBus) {
		ENTITY_TYPES.register(modEventBus);
		modEventBus.addListener(AvaritiaEntities::onRegisterRenderer);
		modEventBus.addListener(AvaritiaEntities::addPlayerLayer);
	}
	
	@SubscribeEvent
	public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(INFINITY_ARROW.get(), RenderInfinityArrow::new);
		event.registerEntityRenderer(ENDEST_PEARL.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(GAPING_VOID.get(), RenderGapingVoid::new);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SubscribeEvent
	public static void addPlayerLayer(EntityRenderersEvent.AddLayers event) {
		for (Model model : event.getSkins()) {
			LivingEntityRenderer entityRenderer = event.getSkin(model);
			entityRenderer.addLayer(new RenderInfinityArmor(entityRenderer, event.getEntityModels()));
		}
	}

}
