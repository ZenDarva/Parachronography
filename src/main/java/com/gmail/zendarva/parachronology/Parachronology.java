package com.gmail.zendarva.parachronology;

import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.block.Displacer;
import com.gmail.zendarva.parachronology.block.PetrifiedWood;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;
import com.gmail.zendarva.parachronology.handlers.MobDrop;
import com.gmail.zendarva.parachronology.item.BasicMoment;
import com.gmail.zendarva.parachronology.item.Bias;
import com.gmail.zendarva.parachronology.item.CapturedMoment;
import com.gmail.zendarva.parachronology.item.Moment;
import com.gmail.zendarva.parachronology.item.Upgrade;
import com.gmail.zendarva.parachronology.proxy.CommonProxy;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by James on 6/11/2017.
 */
@Mod(name = "Parachronology", modid = Parachronology.MODID, version = Parachronology.VERSION)
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
	public static final String VERSION = "1.5.0";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new MobDrop());

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);

		FMLInterModComms.sendMessage("Waila", "register",
				"com.darva.parachronology.waila.ParachronologyAddon.registerAddon");
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		ConfigurationHolder.getInstance().setupConfigs(config);

		petrifiedWood = new PetrifiedWood(Material.ROCK);

		OreDictionary.registerOre("cobblestone", new ItemStack(petrifiedWood, 1));

		displacer = new Displacer();

		moment = new Moment();
		upgrade = new Upgrade();
		capturedMoment = new CapturedMoment();
		this.bias = new Bias();
		basicMoment = new BasicMoment();
		//storage = new Storage();

		ItemStack stack = new ItemStack(displacer, 1, 0);
		ItemStack momentStack = new ItemStack(moment, 1, 0);
		GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(Blocks.COBBLESTONE), 'b',
				new ItemStack(basicMoment));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(stack, "aaa", "aba", "aaa", 'a', "cobblestone", 'b', new ItemStack(basicMoment)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(basicMoment), "treeSapling"));

		stack = new ItemStack(upgrade);
		GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment), 'b',
				new ItemStack(moment, 1, 1));
		stack = new ItemStack(upgrade, 1, 1);
		GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment, 1, 1), 'b',
				new ItemStack(moment, 1, 2));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.BUCKET),
				new ItemStack(moment));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.BUCKET),
				new ItemStack(moment, 1, 1));
		stack = new ItemStack(petrifiedWood, 8);
		GameRegistry
				.addRecipe(new ShapedOreRecipe(stack, "aaa", "aba", "aaa", 'a', "logWood", 'b', new ItemStack(moment)));

		GameRegistry.registerTileEntity(DisplacerEntity.class, "tileEntityDisplacer");

		ConfigurationHolder.getInstance().LoadConfigs();

		ConfigurationHolder.getInstance().save();
		proxy.registerRenderThings();

		CraftingRecipes.buildBiases();

	}

}
