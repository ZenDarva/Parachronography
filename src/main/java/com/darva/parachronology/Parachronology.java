package com.darva.parachronology;

import com.darva.parachronology.Configuration.ConfigurationHolder;
import com.darva.parachronology.blocks.Displacer;
import com.darva.parachronology.blocks.PetrifiedWood;
import com.darva.parachronology.entity.DisplacerEntity;
import com.darva.parachronology.generation.VoidWorld;
import com.darva.parachronology.generation.VoidWorldType;
import com.darva.parachronology.handlers.MobDrop;
import com.darva.parachronology.handlers.PlayerExtender;
import com.darva.parachronology.items.CapturedMoment;
import com.darva.parachronology.items.DiplacerItemBlock;
import com.darva.parachronology.items.Moment;
import com.darva.parachronology.items.Upgrade;
import com.darva.parachronology.proxy.commonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by James on 8/23/2015.
 */
@Mod(modid = Parachronology.MODID, version = Parachronology.VERSION)
public class Parachronology {


    @SidedProxy(clientSide = "com.darva.parachronology.proxy.clientProxy", serverSide = "com.darva.parachronology.proxy.commonProxy")
    public static commonProxy proxy;
    public static Item moment;
    public static CapturedMoment capturedMoment;
    public static Block displacer;
    public static VoidWorldType worldType;
    public static Item upgrade;
    public static Block petrifiedWood;

    public static int renderId = -1;

    @Mod.Instance("Parachronology")
    public static Parachronology instance;
    public static final String MODID = "Parachronology";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MobDrop());
        worldType = new VoidWorldType();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);


    }

    @SubscribeEvent
    public void onWorldLoad(WorldTypeEvent event) {

        if (event.worldType == worldType) {
            DimensionManager.unregisterProviderType(0);
            DimensionManager.registerProviderType(0, VoidWorld.class, true);
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
        if (prop.firstConnection) {
            prop.firstConnection = false;
            player.inventory.addItemStackToInventory(new ItemStack(Items.dye, 64, 15));
            player.inventory.addItemStackToInventory(new ItemStack(Blocks.sapling, 4));
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
        GameRegistry.registerBlock(displacer, DiplacerItemBlock.class, "displacer");

        moment = new Moment();
        upgrade = new Upgrade();
        capturedMoment = new CapturedMoment();

        ItemStack stack = new ItemStack(displacer, 1, 0);
        //GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(Blocks.cobblestone), 'b', new ItemStack(moment));
        GameRegistry.addRecipe(new ShapedOreRecipe(stack, "aaa", "aba", "aaa", 'a', "cobblestone", 'b', new ItemStack(moment)));
        stack = new ItemStack(upgrade);
        GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment), 'b', new ItemStack(moment, 1, 1));
        stack = new ItemStack(upgrade, 1, 1);
        GameRegistry.addRecipe(stack, "aaa", "aba", "aaa", 'a', new ItemStack(moment, 1, 1), 'b', new ItemStack(moment, 1, 2));

        GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket), new ItemStack(Items.bucket), new ItemStack(moment));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket), new ItemStack(Items.bucket), new ItemStack(moment, 1, 1));


        proxy.registerRenderThings();
        GameRegistry.registerTileEntity(DisplacerEntity.class, "tileEntityDisplacer");

        ConfigurationHolder.getInstance().LoadConfigs();

        ConfigurationHolder.getInstance().save();
    }


}
