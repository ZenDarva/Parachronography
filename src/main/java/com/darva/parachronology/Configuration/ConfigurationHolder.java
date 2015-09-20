package com.darva.parachronology.Configuration;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.TransformListBuilder;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by James on 8/24/2015.
 */
public class ConfigurationHolder {

    private static ConfigurationHolder instance;
    private List<String> defaultTier1Wood;
    private List<String> defaultTier1Cobble;
    private List<String> defaultTier1IronBlock;
    private List<String> defaultTier1Sapling1;
    private List<String> defaultTier1Sapling2;
    private List<String> defaultTier1Sapling3;
    private List<String> defaultTier1Sapling4;
    private List<String> defaultTier2Dirt;
    private List<String> defaultTier2Cobble;
    private List<String> defaultTier2Stone;
    private List<String> defaultTier2Netherrack;
    private List<String> defaultTier2Sand;
    private List<String> defaultTier3Stone;
    private List<String> defaultTier3Diamond;
    private List<String> defaultTier3SoulSand;
    private Configuration config;
    private boolean hasCopper = false;
    private boolean hasTin = false;
    private boolean hasLead = false;
    private boolean hasSilver = false;

    private ConfigurationHolder() {

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

        buildDefaults();
        loadDisplacements(config);
        loadTransforms(config);

        for (ModContainer container : Loader.instance().getModList()) {
            System.out.println(container.getModId());
        }
    }

    public void save() {
        config.save();
    }


    private void loadDisplacements(Configuration con) {


        Property prop = con.get("Displacements.Tier1", "minecraft:log", defaultTier1Wood.toArray(new String[defaultTier1Wood.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:log"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:cobblestone", defaultTier1Cobble.toArray(new String[defaultTier1Cobble.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("Parachronolgy:petrifiedwood"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "minecraft:iron_block", defaultTier1IronBlock.toArray(new String[defaultTier1IronBlock.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:iron_block"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "minecraft:sapling:1", defaultTier1Sapling1.toArray(new String[defaultTier1Sapling1.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling:0"), prop.getStringList());

        prop = con.get("Displacements.Tier1", "minecraft:sapling:2", defaultTier1Sapling2.toArray(new String[defaultTier1Sapling2.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling:1"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:sapling:3", defaultTier1Sapling3.toArray(new String[defaultTier1Sapling3.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling:2"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:sapling:4", defaultTier1Sapling3.toArray(new String[defaultTier1Sapling4.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling:3"), prop.getStringList());


        prop = con.get("Displacements.Tier2", "minecraft:dirt", defaultTier2Dirt.toArray(new String[defaultTier2Dirt.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:dirt"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:cobblestone", defaultTier2Cobble.toArray(new String[defaultTier2Cobble.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:stone", defaultTier2Stone.toArray(new String[defaultTier2Stone.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());

        prop = con.get("Displacements.Tier2", "minecraft:netherrack", defaultTier2Netherrack.toArray(new String[defaultTier2Netherrack.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:netherrack"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:sand", defaultTier2Sand.toArray(new String[defaultTier2Sand.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:sand"), prop.getStringList());


        prop = con.get("Displacements.Tier3", "minecraft:stone", defaultTier3Stone.toArray(new String[defaultTier3Stone.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(3, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:diamond_block", defaultTier3Diamond.toArray(new String[defaultTier3Diamond.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(3, BlockReference.readBlockFromString("minecraft:diamond_block"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:soul_sand", defaultTier3SoulSand.toArray(new String[defaultTier3SoulSand.size()]));
        DisplaceListBuilder.Instance().AddDisplacement(3, BlockReference.readBlockFromString("minecraft:soul_sand"), prop.getStringList());
    }

    private void loadTransforms(Configuration con) {

        String[] defaultTeir1Zombie = {"Cow", "Chicken", "Pig", "Sheep"};
        String[] defaultTier1Pigman = {"Pig"};
        String[] defaultTier1Spider = {"Sheep", "Chicken"};
        String[] defaultTier1Slime = {"LavaSlime"};
        String[] defaultTier1Cube = {"Slime"};
        String[] defaultTier2Enderman = {"Villager"};

        Property prop = con.get("Transforms.Tier1", "Zombie", defaultTeir1Zombie);
        TransformListBuilder.Instance().addTransform(1, "Zombie", defaultTeir1Zombie);

        prop = con.get("Transforms.Tier1", "PigZombie", defaultTier1Pigman);
        TransformListBuilder.Instance().addTransform(1, "PigZombie", defaultTier1Pigman);

        prop = con.get("Transforms.Tier1", "Spider", defaultTier1Spider);
        TransformListBuilder.Instance().addTransform(1, "Spider", defaultTier1Spider);

        prop = con.get("Transforms.Tier1", "Slime", defaultTier1Slime);
        TransformListBuilder.Instance().addTransform(1, "Slime", defaultTier1Slime);

        prop = con.get("Transforms.Tier1", "LavaSlime", defaultTier1Cube);
        TransformListBuilder.Instance().addTransform(1, "LavaSlime", defaultTier1Cube);

        prop = con.get("Transforms.Tier2", "Enderman", defaultTier2Enderman);
        TransformListBuilder.Instance().addTransform(2, "Enderman", defaultTier2Enderman);

    }


    private void buildDefaults() {

        defaultTier1Wood = new ArrayList<String>(Arrays.asList("minecraft:stone", "minecraft:cobblestone", "minecraft:gravel"));
        defaultTier1Cobble = new ArrayList<String>(Arrays.asList("minecraft:iron_ore", "minecraft:coal_ore", "minecraft:dirt"));
        defaultTier1IronBlock = new ArrayList<String>(Arrays.asList("minecraft:obsidian"));
        defaultTier1Sapling1 = new ArrayList<String>(Arrays.asList("minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:3", "minecraft:sapling:4"));
        defaultTier1Sapling2 = new ArrayList<String>(Arrays.asList("minecraft:sapling:0", "minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:1"));
        defaultTier1Sapling3 = new ArrayList<String>(Arrays.asList("minecraft:sapling:0", "minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:4"));
        defaultTier1Sapling4 = new ArrayList<String>(Arrays.asList("minecraft:sapling:0", "minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:3"));

        defaultTier2Dirt = new ArrayList<String>(Arrays.asList("minecraft:melon_block", "minecraft:mycelium", "minecraft:pumpkin", "minecraft:waterlily"));

        defaultTier2Cobble = new ArrayList<String>(Arrays.asList("minecraft:gold_ore"));
        defaultTier2Stone = new ArrayList<String>(Arrays.asList("minecraft:lapis_ore", "minecraft:emerald_ore", "minecraft:redstone_ore"));
        defaultTier2Netherrack = new ArrayList<String>(Arrays.asList("minecraft:sand", "minecraft:clay", "minecraft:sand:1"));
        defaultTier2Sand = new ArrayList<String>(Arrays.asList("minecraft:cactus", "minecraft:sandstone"));

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


}
