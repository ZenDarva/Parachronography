package com.gmail.zendarva.parachronology;

import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.block.Displacer;
import com.gmail.zendarva.parachronology.block.EnrichedDirt;
import com.gmail.zendarva.parachronology.block.PetrifiedWood;
import com.gmail.zendarva.parachronology.block.Storage;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.capability.TimelessStorage;
import com.gmail.zendarva.parachronology.handlers.CapabilityHandler;
import com.gmail.zendarva.parachronology.handlers.LoadingCallback;
import com.gmail.zendarva.parachronology.handlers.MobDrop;
import com.gmail.zendarva.parachronology.handlers.Registration;
import com.gmail.zendarva.parachronology.item.*;
import com.gmail.zendarva.parachronology.proxy.CommonProxy;
import com.gmail.zendarva.parachronology.recipe.CraftingRecipes;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 6/11/2017.
 */
@Mod(modid = Parachronology.MODID, name = Parachronology.MODNAME, version = Parachronology.VERSION,acceptedMinecraftVersions = "[1.12,1.13)")
public class Parachronology {

	@SidedProxy(clientSide = "com.gmail.zendarva.parachronology.proxy.ClientProxy", serverSide = "com.gmail.zendarva.parachronology.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static Moment moment;
	public static CapturedMoment capturedMoment;
	public static Displacer displacer;
	public static Upgrade upgrade;
	public static PetrifiedWood petrifiedWood;
	public static Bias bias;
	public static BasicMoment basicMoment;
	public static EnrichedDirt enrichedDirt;
	public static Storage storage;
	public static TimelessPickaxe pickaxe;
	public static TimelessWand wand;


	public static ConfigurationHolder config;
	
	@Mod.Instance("parachronology")
	public static Parachronology instance;
	public static final String MODID = "parachronology";
	public static final String MODNAME = "Parachronology";
	public static final String VERSION = "1.5.6";
	
	public static final CreativeTabs TAB = new CreativeTabs(Parachronology.MODID){

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Parachronology.basicMoment);
		}


	};

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new MobDrop());
		OreDictionary.registerOre("cobblestone", petrifiedWood);
//		FMLInterModComms.sendMessage("Waila", "register",
//				"com.darva.parachronology.waila.ParachronologyAddon.registerAddon");
		proxy.init();
		ForgeChunkManager.setForcedChunkLoadingCallback(this,new LoadingCallback());
		if (Loader.isModLoaded("actuallyadditions")) {
			MinecraftForge.addGrassSeed(new ItemStack(Item.getByNameOrId("actuallyadditions:item_canola_seed")),2);
		}

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Registration());
		MinecraftForge.EVENT_BUS.register(new CraftingRecipes());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());


		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		ConfigurationHolder.getInstance().setupConfigs(config);

		petrifiedWood = new PetrifiedWood(Material.ROCK);

		displacer = new Displacer();

		moment = new Moment();
		upgrade = new Upgrade();
		capturedMoment = new CapturedMoment();
		this.bias = new Bias();
		basicMoment = new BasicMoment();
		enrichedDirt = new EnrichedDirt();
		storage = new Storage();
		pickaxe= new TimelessPickaxe();
		wand = new TimelessWand();
		ConfigurationHolder.getInstance().LoadConfigs();
		ConfigurationHolder.getInstance().save();

		proxy.preInit();

		CapabilityManager.INSTANCE.register(ITimeless.class,new TimelessStorage(),Timeless.class);

	}
	@SubscribeEvent
	public void ModelRegistry(ModelRegistryEvent event) {
		proxy.registerRenderThings();
	}


}
