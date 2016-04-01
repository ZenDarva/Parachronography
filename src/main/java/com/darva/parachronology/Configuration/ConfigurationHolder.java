package com.darva.parachronology.Configuration;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.TransformListBuilder;
import com.darva.parachronology.handlers.DropData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.*;

/**
 * Created by James on 8/24/2015.
 */
public class ConfigurationHolder {

    private static ConfigurationHolder instance;
    private List<String> defaultTier1Wood;
    private List<String> defaultTier1Cobble;
    private List<String> defaultTier1IronBlock;
    private List<String> defaultTier1Sapling;
    private List<String> defaultTier2Dirt;
    private List<String> defaultTier2Cobble;
    private List<String> defaultTier2Stone;
    private List<String> defaultTier2Netherrack;
    private List<String> defaultTier2Sand;
    private List<String> defaultTier2GoldBlock;
    private List<String> defaultTier3Stone;
    private List<String> defaultTier3Diamond;
    private List<String> defaultTier3SoulSand;
    private List<String> captureBlacklist;
    private Configuration config;
    private boolean hasCopper = false;
    private boolean hasTin = false;
    private boolean hasLead = false;
    private boolean hasSilver = false;
    private boolean setDefaults = false;
    private List<ItemStack> startingInventory;

    public static int playerDropChanceMultiplier = 5;

    private boolean generateEndPortal = true;

    public static HashMap<String, DropData> mobDrops;

    private ConfigurationHolder() {
        mobDrops = new HashMap<>();
    }

    public static ConfigurationHolder getInstance() {
        if (instance == null) {
            instance = new ConfigurationHolder();
        }
        return instance;
    }

    public void setupConfigs(Configuration con) {
        config = con;
    }

    public void LoadConfigs() {

        if (checkNeedBuildDefaults(config)) {

            buildDefaults();
            setDisplacementsDefaults(config);
            setTransformDefaults(config);
            setOtherDefaults(config);
        }
        else
        {
            loadTransforms(config);
            loadDisplacements(config);
            loadOtherConfigs(config);
        }

        if (config.getCategory("MobDrops").keySet().isEmpty() == true)
        {
            setMobDrops(config);
        }
        else
        {
            loadMobDrops(config);
        }


//        for (ModContainer container : Loader.instance().getModList()) {
//            System.out.println(container.getModId());
//        }

        //for (Object block : Block.blockRegistry){System.out.println(((Block)block).getRegistryName()); }
    }

    public void save() {
        config.save();
    }


    private void setOtherDefaults(Configuration con)
    {
        Property prop = con.get("Defaults", "allowGenerateEndPortal", true);
        prop.set(true);

        prop = con.get("Defaults", "startingInventory", "minecraft:dye:64:15,minecraft:sapling:4");
        parseInventoryString(prop.getString());

        prop = con.get("Defaults", "PlayerKillDropChanceMultiplyer", 5);
        playerDropChanceMultiplier=5;

    }

    private void loadOtherConfigs(Configuration con)
    {
        Property prop = con.get("Defaults", "allowGenerateEndPortal", true);
        generateEndPortal = prop.getBoolean();

        prop = con.get("Defaults", "startingInventory", "minecraft:dye:64:15,minecraft:sapling:0:4");
        parseInventoryString(prop.getString());

        prop = con.get("Defaults", "PlayerKillDropChanceMultiplyer", 5);
        playerDropChanceMultiplier=prop.getInt();
    }

    private void parseInventoryString(String inv)
    {
        startingInventory = new LinkedList<ItemStack>();
        for(String item: inv.split(","))
        {
            String datum[] = item.split(":");
            int meta, count;
            if (datum.length <4)
            {
                meta = 0;
                count = Integer.parseInt(datum[2]);
            }
            else
            {
                count = Integer.parseInt(datum[2]);
                meta = Integer.parseInt(datum[3]);
            }

            Item targ = Item.getByNameOrId(datum[0] + ":" + datum[1]);
            ItemStack stack = new ItemStack(targ, count,meta);
            startingInventory.add(stack);
        }
    }

    private void setDisplacementsDefaults(Configuration con)
    {


        Property prop = con.get("Displacements.Tier1", "minecraft:log:-1", defaultTier1Wood.toArray(new String[defaultTier1Wood.size()]));
        prop.set(defaultTier1Wood.toArray(new String[defaultTier1Wood.size()]));
        DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("minecraft:log"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:cobblestone", defaultTier1Cobble.toArray(new String[defaultTier1Cobble.size()]));
        prop.set(defaultTier1Cobble.toArray(new String[defaultTier1Cobble.size()]));
        DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "parachronology:petrifiedwood", defaultTier1Cobble.toArray(new String[defaultTier1Cobble.size()]));
        prop.set(defaultTier1Cobble.toArray(new String[defaultTier1Cobble.size()]));

        DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("parachronology:petrifiedwood"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "minecraft:iron_block", defaultTier1IronBlock.toArray(new String[defaultTier1IronBlock.size()]));
        prop.set(defaultTier1IronBlock.toArray(new String[defaultTier1IronBlock.size()]));
        prop.set(defaultTier1IronBlock.toArray(new String[defaultTier1IronBlock.size()]));
        DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("minecraft:iron_block"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "minecraft:sapling:-1", defaultTier1Sapling.toArray(new String[defaultTier1Sapling.size()]));
        prop.set(defaultTier1Sapling.toArray(new String[defaultTier1Sapling.size()]));
        DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling:-1"), prop.getStringList());


        prop = con.get("Displacements.Tier2", "minecraft:dirt", defaultTier2Dirt.toArray(new String[defaultTier2Dirt.size()]));
        prop.set(defaultTier2Dirt.toArray(new String[defaultTier2Dirt.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:dirt"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:cobblestone", defaultTier2Cobble.toArray(new String[defaultTier2Cobble.size()]));
        prop.set(defaultTier2Cobble.toArray(new String[defaultTier2Cobble.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:stone", defaultTier2Stone.toArray(new String[defaultTier2Stone.size()]));
        prop.set(defaultTier2Stone.toArray(new String[defaultTier2Stone.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());

        prop = con.get("Displacements.Tier2", "minecraft:netherrack", defaultTier2Netherrack.toArray(new String[defaultTier2Netherrack.size()]));
        prop.set(defaultTier2Netherrack.toArray(new String[defaultTier2Netherrack.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:netherrack"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:sand", defaultTier2Sand.toArray(new String[defaultTier2Sand.size()]));
        prop.set(defaultTier2Sand.toArray(new String[defaultTier2Sand.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:sand"), prop.getStringList());

        prop = con.get("Displacements.Tier2", "minecraft:gold_block", defaultTier2Sand.toArray(new String[defaultTier2Sand.size()]));
        prop.set(defaultTier2GoldBlock.toArray(new String[defaultTier2GoldBlock.size()]));
        DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:gold_block"), prop.getStringList());


        prop = con.get("Displacements.Tier3", "minecraft:stone", defaultTier3Stone.toArray(new String[defaultTier3Stone.size()]));
        prop.set(defaultTier3Stone.toArray(new String[defaultTier3Stone.size()]));
        DisplaceListBuilder.Instance().addDisplacement(3, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:diamond_block", defaultTier3Diamond.toArray(new String[defaultTier3Diamond.size()]));
        prop.set(defaultTier3Diamond.toArray(new String[defaultTier3Diamond.size()]));
        DisplaceListBuilder.Instance().addDisplacement(3, BlockReference.readBlockFromString("minecraft:diamond_block"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:soul_sand", defaultTier3SoulSand.toArray(new String[defaultTier3SoulSand.size()]));
        prop.set(defaultTier3SoulSand.toArray(new String[defaultTier3SoulSand.size()]));
        DisplaceListBuilder.Instance().addDisplacement(3, BlockReference.readBlockFromString("minecraft:soul_sand"), prop.getStringList());

        if (Loader.isModLoaded("Thaumcraft")){
            List<String> defaultTier2LapisBlock = new ArrayList<String>(Arrays.asList("thaumcraft:crystal_aer", "thaumcraft:crystal_ignis", "thaumcraft:crystal_aqua","thaumcraft:crystal_terra", "thaumcraft:crystal_ordo", "thaumcraft:crystal_perditio", "thaumcraft:crystal_vitium"));
            prop = con.get("Displacements.Tier2", "minecraft:lapis_block", defaultTier2LapisBlock.toArray(new String[defaultTier2LapisBlock.size()]));
            prop.set(defaultTier2LapisBlock.toArray(new String[defaultTier2LapisBlock.size()]));
            DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("minecraft:lapis_block"), prop.getStringList());

            List<String> defaultTier2NitorBlock = new ArrayList<String>(Arrays.asList("thaumcraft:nitor:-1", "thaumcraft:sapling", "thaumcraft:sapling:1","thaumcraft:shimmerleaf", "thaumcraft:cinderpearl", "thaumcraft:vishroom", "thaumcraft:bloom"));
            prop = con.get("Displacements.Tier2", "thaumcraft:nitor:-1", defaultTier2NitorBlock.toArray(new String[defaultTier2NitorBlock.size()]));
            prop.set(defaultTier2NitorBlock.toArray(new String[defaultTier2NitorBlock.size()]));
            DisplaceListBuilder.Instance().addDisplacement(2, BlockReference.readBlockFromString("thaumcraft:nitor:-1"), prop.getStringList());

            List<String> defaultTier1Slab = new ArrayList<String>(Arrays.asList("minecraft:stone_slab:3", "thaumcraft:ore_amber", "thaumcraft:ore_cinnabar" ));
            prop = con.get("Displacements.Tier2", "minecraft:stone_slab:3", defaultTier1Slab.toArray(new String[defaultTier1Slab.size()]));
            prop.set(defaultTier1Slab.toArray(new String[defaultTier1Slab.size()]));
            DisplaceListBuilder.Instance().addDisplacement(1, BlockReference.readBlockFromString("minecraft:stone_slab:3"), prop.getStringList());

        }

    }


    private void loadDisplacements(Configuration con) {
        ConfigCategory transforms = con.getCategory("displacements");

            for(ConfigCategory category : transforms.getChildren())
                for (String key : category.keySet()) {
                    DisplaceListBuilder.Instance().addDisplacement(getTierFromString(category.getName()), BlockReference.readBlockFromString(key), category.get(key).getStringList());
                }

    }

    private void loadTransforms(Configuration con) {
        ConfigCategory transforms = con.getCategory("transforms");

            for(ConfigCategory category : transforms.getChildren())
                for (String key : category.keySet()) {
                    TransformListBuilder.Instance().addTransform(getTierFromString(category.getName()), key, category.get(key).getStringList());
                }

    }
    private void loadMobDrops(Configuration con)
    {
        ConfigCategory mobs = con.getCategory("MobDrops");

        for(String key : mobs.keySet())
        {
            mobDrops.put(key, new DropData(key, mobs.get(key).getIntList()[0], mobs.get(key).getIntList()[1], mobs.get(key).getIntList()[2]) );
        }
    }

    private void setMobDrops(Configuration con)
    {
        Property prop = con.get("MobDrops","zombie",new int[]{2,0,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","skeleton",new int[]{2,0,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","slime",new int[]{1,0,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","creeper",new int[]{2,0,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","witch",new int[]{3,0,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","enderman",new int[]{2,1,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","wither skeleton",new int []{0,3,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","blaze",new int[]{0,3,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","zombie pigman", new int[]{0,3,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","magma cube",new int[]{0,1,0});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","ghast",new int[]{0,4,1});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));

        prop = con.get("MobDrops","wither",new int[]{0,0,100});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));
        prop = con.get("MobDrops","ender dragon",new int[]{0,0,100});
        mobDrops.put(prop.getName(),new DropData(prop.getName(),prop.getIntList()[0],prop.getIntList()[1],prop.getIntList()[2]));





    }

    private void setTransformDefaults(Configuration con) {

        String[] defaultTeir1Zombie = {"Cow", "Chicken", "Pig", "Sheep"};
        String[] defaultTier1Pigman = {"Pig"};
        String[] defaultTier1Spider = {"Sheep", "Chicken"};
        String[] defaultTier1Slime = {"LavaSlime"};
        String[] defaultTier1Cube = {"Slime"};
        String[] defaultTier2Enderman = {"Villager"};
        String[] defaultTier2Squid = {"Guardian"};

        Property prop = con.get("Transforms.Tier1", "Zombie", defaultTeir1Zombie);
        prop.set(defaultTeir1Zombie);
        TransformListBuilder.Instance().addTransform(1, "Zombie", defaultTeir1Zombie);

        prop = con.get("Transforms.Tier1", "PigZombie", defaultTier1Pigman);
        prop.set(defaultTier1Pigman);
        TransformListBuilder.Instance().addTransform(1, "PigZombie", defaultTier1Pigman);

        prop = con.get("Transforms.Tier1", "Spider", defaultTier1Spider);
        prop.set(defaultTier1Spider);
        TransformListBuilder.Instance().addTransform(1, "Spider", defaultTier1Spider);

        prop = con.get("Transforms.Tier1", "Slime", defaultTier1Slime);
        prop.set(defaultTier1Slime);
        TransformListBuilder.Instance().addTransform(1, "Slime", defaultTier1Slime);

        prop = con.get("Transforms.Tier1", "LavaSlime", defaultTier1Cube);
        prop.set(defaultTier1Cube);
        TransformListBuilder.Instance().addTransform(1, "LavaSlime", defaultTier1Cube);

        prop = con.get("Transforms.Tier2", "Enderman", defaultTier2Enderman);
        prop.set(defaultTier2Enderman);
        TransformListBuilder.Instance().addTransform(2, "Enderman", defaultTier2Enderman);

        prop = con.get("Transforms.Tier2", "Squid", defaultTier2Squid);
        prop.set(defaultTier2Squid);
        TransformListBuilder.Instance().addTransform(2, "Squid", defaultTier2Squid);

    }


    boolean checkNeedBuildDefaults(Configuration con) {
        Property prop = con.get("Defaults", "BuildDefaultTransformsAndDisplacements", true);
        setDefaults = prop.getBoolean();

        if (setDefaults) {
            for (String name : config.getCategoryNames())
            {
                if (!"defaults".equals(name.toLowerCase()))
                    config.getCategory(name).values().clear();
            }
        }
        prop = con.get("Defaults", "BuildDefaultTransformsAndDisplacements", true);
        prop.set(false);
        return setDefaults;
    }


    private void buildDefaults() {

        defaultTier1Wood = new ArrayList<String>(Arrays.asList("minecraft:stone", "minecraft:cobblestone", "minecraft:gravel"));
        defaultTier1Cobble = new ArrayList<String>(Arrays.asList("minecraft:iron_ore", "minecraft:coal_ore", "minecraft:dirt"));
        defaultTier1IronBlock = new ArrayList<String>(Arrays.asList("minecraft:obsidian"));
        defaultTier1Sapling = new ArrayList<String>(Arrays.asList("minecraft:sapling:0","minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:5", " minecraft:reeds"));

        defaultTier2Dirt = new ArrayList<String>(Arrays.asList("minecraft:melon_block", "minecraft:mycelium", "minecraft:pumpkin", "minecraft:waterlily"));

        defaultTier2Cobble = new ArrayList<String>(Arrays.asList("minecraft:gold_ore"));
        defaultTier2Stone = new ArrayList<String>(Arrays.asList("minecraft:lapis_ore", "minecraft:emerald_ore", "minecraft:redstone_ore"));
        defaultTier2Netherrack = new ArrayList<String>(Arrays.asList("minecraft:sand", "minecraft:clay", "minecraft:sand:1"));
        defaultTier2Sand = new ArrayList<String>(Arrays.asList("minecraft:cactus", "minecraft:sandstone"));
        defaultTier2GoldBlock = new ArrayList<String>(Arrays.asList("minecraft:diamond_ore"));

        defaultTier3Stone = new ArrayList<String>(Arrays.asList("minecraft:diamond_ore"));
        defaultTier3Diamond = new ArrayList<String>(Arrays.asList("minecraft:mob_spawner"));
        defaultTier3SoulSand = new ArrayList<String>(Arrays.asList("minecraft:end_stone"));

        if (Loader.isModLoaded("ThermalFoundation")) {
            defaultTier2Cobble.add("ThermalFoundation:Ore:0"); //copper
            defaultTier2Cobble.add("ThermalFoundation:Ore:1"); //tin
            defaultTier2Cobble.add("ThermalFoundation:Ore:2"); //silver
            defaultTier2Cobble.add("ThermalFoundation:Ore:3"); //lead

            defaultTier3Stone.add("ThermalFoundation:Ore:4"); //Ferrous.
            hasCopper = true;
            hasTin = true;
            hasLead = true;
            hasSilver = true;
            System.out.println("Parachronology just Snarfed TE's ores.");

        }




    }

    private int getTierFromString(String tier)
    {
        return Integer.parseInt(tier.substring(tier.length()-1));
    }

    public boolean isGenerateEndPortal() {
        return generateEndPortal;
    }

    public List<ItemStack> getStartingInventory() {
        return startingInventory;
    }
}
