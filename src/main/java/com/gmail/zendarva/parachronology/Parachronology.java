package com.gmail.zendarva.parachronology;

import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.block.Displacer;
import com.gmail.zendarva.parachronology.block.PetrifiedWood;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;
import com.gmail.zendarva.parachronology.handlers.MobDrop;
import com.gmail.zendarva.parachronology.handlers.Registration;
import com.gmail.zendarva.parachronology.item.BasicMoment;
import com.gmail.zendarva.parachronology.item.Bias;
import com.gmail.zendarva.parachronology.item.CapturedMoment;
import com.gmail.zendarva.parachronology.item.Moment;
import com.gmail.zendarva.parachronology.item.Upgrade;
import com.gmail.zendarva.parachronology.proxy.CommonProxy;

import com.gmail.zendarva.parachronology.recipe.CraftingRecipes;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 6/11/2017.
 */
@Mod(modid = Parachronology.MODID, name = Parachronology.MODNAME, version = Parachronology.VERSION, dependencies="after:thermalfoundation,rftools,ic2,bigreactors,actuallyadditions,immersiveengineering ")
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
	//public static Storage storage;

	public static ConfigurationHolder config;
	
	@Mod.Instance("Parachronology")
	public static Parachronology instance;
	public static final String MODID = "parachronology";
	public static final String MODNAME = "Parachronology";
	public static final String VERSION = "1.5.0";
	
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
		Loader.instance().getModList().stream().forEach(f->System.out.println(f.getModId()));
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Registration());
		MinecraftForge.EVENT_BUS.register(new CraftingRecipes());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);

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

		ItemStack stack = new ItemStack(displacer, 1, 0);
		ItemStack momentStack = new ItemStack(moment, 1, 0);
		GameRegistry.registerTileEntity(DisplacerEntity.class, "tileEntityDisplacer");
		ConfigurationHolder.getInstance().LoadConfigs();
		ConfigurationHolder.getInstance().save();

	}
	@SubscribeEvent
	public void ModelRegistry(ModelRegistryEvent event) {
		proxy.registerRenderThings();
	}


}
