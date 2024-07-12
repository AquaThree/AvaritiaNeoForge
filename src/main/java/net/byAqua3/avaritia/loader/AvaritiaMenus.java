package net.byAqua3.avaritia.loader;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.gui.GuiExtremeCraftingTable;
import net.byAqua3.avaritia.gui.GuiNeutronCollector;
import net.byAqua3.avaritia.gui.GuiNeutroniumCompressor;
import net.byAqua3.avaritia.inventory.MenuExtremeCrafting;
import net.byAqua3.avaritia.inventory.MenuNeutronCollector;
import net.byAqua3.avaritia.inventory.MenuNeutroniumCompressor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AvaritiaMenus {
	
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, Avaritia.MODID);
	
	public static final DeferredHolder<MenuType<?>,MenuType<MenuExtremeCrafting>> EXTREME_CRAFTING = MENUS.register("extreme_crafting_table", () -> new MenuType<>((IContainerFactory<MenuExtremeCrafting>) MenuExtremeCrafting::new, FeatureFlagSet.of()));
	public static final DeferredHolder<MenuType<?>,MenuType<MenuNeutroniumCompressor>> COMPRESSOR = MENUS.register("compressor", () -> new MenuType<>((IContainerFactory<MenuNeutroniumCompressor>) MenuNeutroniumCompressor::new, FeatureFlagSet.of()));
	public static final DeferredHolder<MenuType<?>,MenuType<MenuNeutronCollector>> NEUTRON_COLLECTOR = MENUS.register("neutron_collector", () -> new MenuType<>((IContainerFactory<MenuNeutronCollector>) MenuNeutronCollector::new, FeatureFlagSet.of()));
	
	public static void registerMenus(IEventBus modEventBus) {
		MENUS.register(modEventBus);
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void registerScreens() {
		MenuScreens.register(EXTREME_CRAFTING.get(), GuiExtremeCraftingTable::new);
		MenuScreens.register(COMPRESSOR.get(), GuiNeutroniumCompressor::new);
		MenuScreens.register(NEUTRON_COLLECTOR.get(), GuiNeutronCollector::new);
	}

}
