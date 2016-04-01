package com.darva.parachronology;

import com.darva.parachronology.Configuration.ConfigurationHolder;
import com.darva.parachronology.blocks.Displacer;
import com.darva.parachronology.blocks.PetrifiedWood;

import com.darva.parachronology.crafting.CraftingRecipes;
import com.darva.parachronology.entity.DisplacerEntity;
import com.darva.parachronology.generation.VoidWorld;
import com.darva.parachronology.generation.VoidWorldType;
import com.darva.parachronology.handlers.MobDrop;
import com.darva.parachronology.handlers.PlayerExtender;
import com.darva.parachronology.items.*;
import com.darva.parachronology.proxy.commonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.Hashtable;

/**
 * Created by James on 8/23/2015.
 */
@Mod(name = "Parachronology", modid = Parachronology.MODID, version = Parachronology.VERSION, dependencies="after:Thaumcraft")
public class Parachronology {


    @SidedProxy(clientSide = "com.darva.parachronology.proxy.clientProxy", serverSide = "com.darva.parachronology.proxy.commonProxy")
    public static commonProxy proxy;
    public static Moment moment;
    public static CapturedMoment capturedMoment;
    public static Displacer displacer;
    public static VoidWorldType worldType;
    public static Upgrade upgrade;
    public static PetrifiedWood petrifiedWood;
    public static Bias bias;
    public static BasicMoment basicMoment;
    //public static Storage storage;

    public static ConfigurationHolder config;

    public static boolean islandsLoaded = false;
    Hashtable<Integer, Class<? extends WorldProvider>> providers = ReflectionHelper.getPrivateValue(DimensionManager.class, null, "providers");


    @Mod.Instance("Parachronology")
    public static Parachronology instance;
    public static final String MODID = "parachronology";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MobDrop());

        if (Loader.isModLoaded("islandsinthesky"))
        {
            islandsLoaded = true;
        }
        else
        {
            worldType = new VoidWorldType();
        }
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);

        FMLInterModComms.sendMessage("Waila", "register", "com.darva.parachronology.waila.ParachronologyAddon.registerAddon");
    }

    @SubscribeEvent
    public void onWorldLoad(WorldTypeEvent event) {
        if (event.worldType == worldType && providers.get(0) != VoidWorld.class) {
            DimensionManager.unregisterProviderType(0);
            DimensionManager.registerProviderType(0, VoidWorld.class, true);
            providers.put(0, VoidWorld.class);
        }
 }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent event) {

        if (event.world.getWorldType() == worldType && event.world.provider.getDimensionId() == 0 && !(event.world.provider instanceof VoidWorld)) {
            long seed = event.world.getSeed();
            Field provider;
            try {
                provider = ReflectionHelper.findField(World.class, "provider");
            }
            catch (Exception dontcare)
            {
                provider = ReflectionHelper.findField(World.class, "field_73011_w");

            }
            try {
                provider.set(event.world, new VoidWorld(seed));
                event.world.provider.registerWorld(event.world);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event) {
        if (event.entity.worldObj.isRemote)
            return;
        EntityPlayer player;
        if (!(event.entity instanceof EntityPlayer))
            return;
        player = (EntityPlayer) event.entity;
        PlayerExtender.saveProxyData((EntityPlayer) event.entity);
    }

    @SubscribeEvent
    public void onConstructEntity(EntityEvent.EntityConstructing event) {
        EntityPlayer player;
        if (!(event.entity instanceof EntityPlayer))
            return;
        player = (EntityPlayer) event.entity;
        PlayerExtender.register(player);
    }

    @SubscribeEvent
    public void onEnterWorld(EntityJoinWorldEvent event) {
        if (event.world.isRemote)
            return;
        EntityPlayer player;
        if (!(event.entity instanceof EntityPlayer))
            return;
        player = (EntityPlayer) event.entity;

        PlayerExtender.loadProxyData(player);
        PlayerExtender prop = PlayerExtender.get(player);
        if (prop.firstConnection && !islandsLoaded) {
            prop.firstConnection = false;
//            player.inventory.addItemStackToInventory(new ItemStack(Items.dye, 64, 15));
//            player.inventory.addItemStackToInventory(new ItemStack(Blocks.sapling, 4));
            for(ItemStack stack : ConfigurationHolder.getInstance().getStartingInventory())
            {
                player.inventory.addItemStackToInventory(stack.copy());
            }
        }


    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        ConfigurationHolder.getInstance().setupConfigs(config);

        petrifiedWood = new PetrifiedWood(Material.rock);
        GameRegistry.registerBlock(petrifiedWood, "petrifiedwood");
        OreDictionary.registerOre("cobblestone", petrifiedWood);

        displacer = new Displacer(Material.rock);


        moment = new Moment();
        upgrade = new Upgrade();
        capturedMoment = new CapturedMoment();
        this.bias=new Bias();
        basicMoment = new BasicMoment();
        //storage = new Storage();

        ItemStack stack = new ItemStack(displacer, 1, 0);
        ItemStack momentStack = new ItemStack(moment, 1,0);
        GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(Blocks.cobblestone), 'b', new ItemStack(basicMoment));
        GameRegistry.addRecipe(new ShapedOreRecipe(stack, "aaa", "aba", "aaa", 'a', "cobblestone", 'b', new ItemStack(basicMoment)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(basicMoment), "treeSapling" ));

        stack = new ItemStack(upgrade);
        GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment), 'b', new ItemStack(moment, 1, 1));
        stack = new ItemStack(upgrade, 1, 1);
        GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment, 1, 1), 'b', new ItemStack(moment, 1, 2));

        GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket), new ItemStack(Items.bucket), new ItemStack(moment));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket), new ItemStack(Items.bucket), new ItemStack(moment, 1, 1));
        stack = new ItemStack(petrifiedWood,8);
        GameRegistry.addRecipe(new ShapedOreRecipe(stack, "aaa", "aba", "aaa", 'a', "logWood", 'b', new ItemStack(moment)));


        GameRegistry.registerTileEntity(DisplacerEntity.class, "tileEntityDisplacer");

        ConfigurationHolder.getInstance().LoadConfigs();

        ConfigurationHolder.getInstance().save();
        proxy.registerRenderThings();

        CraftingRecipes.buildBiases();

    }


}
