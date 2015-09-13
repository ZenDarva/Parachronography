package com.darva.parachronology.Configuration;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.TransformListBuilder;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * Created by James on 8/24/2015.
 */
public class ConfigurationHolder {

    private static ConfigurationHolder instance;

    private ConfigurationHolder() {

    }

    public static ConfigurationHolder getInstance() {
        if (instance == null) {
            instance = new ConfigurationHolder();
        }
        return instance;
    }

    public void LoadConfigs(Configuration con) {
        loadDisplacements(con);
        loadTransforms(con);


    }


    private void loadDisplacements(Configuration con) {
        String[] defaultTier1Wood = {"minecraft:stone", "minecraft:cobblestone", "minecraft:gravel"};
        String[] defaultTier1Cobble = {"minecraft:iron_ore", "minecraft:coal_ore", "minecraft:dirt"};
        String[] defaultTier1IronBlock = {"minecraft:obsidian"};
        String[] defaultTier1Sapling = {"minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:5",};

        Property prop = con.get("Displacements.Tier1", "minecraft:log", defaultTier1Wood);
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:log"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:cobblestone", defaultTier1Cobble);
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:iron_block", defaultTier1IronBlock);
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:iron_block"), prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:sapling", defaultTier1Sapling);
        DisplaceListBuilder.Instance().AddDisplacement(1, BlockReference.readBlockFromString("minecraft:sapling"), prop.getStringList());


        String[] defaultTier2Dirt = {"minecraft:melon_block", "minecraft:mycelium", "minecraft:pumpkin", "minecraft:waterlily"};
        String[] defaultTier2Cobble = {"minecraft:gold_ore"};
        String[] defaultTier2Stone = {"minecraft:lapis_ore", "minecraft:emerald_ore", "minecraft:redstone_ore"};
        String[] defaultTier2Netherrack = {"minecraft:sand", "minecraft:clay", "minecraft:sand:1"};
        String[] defaultTier2Sand = {"minecraft:cactus", "minecraft:sandstone"};

        prop = con.get("Displacements.Tier2", "minecraft:dirt", defaultTier2Dirt);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:dirt"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:cobble", defaultTier2Cobble);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:cobblestone"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:stone", defaultTier2Stone);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());

        prop = con.get("Displacements.Tier2", "minecraft:netherrack", defaultTier2Netherrack);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:netherrack"), prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:sand", defaultTier2Sand);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:sand"), prop.getStringList());


        String[] defaultTier3Stone = {"minecraft:diamond_ore"};
        String[] defaultTier3Diamond = {"minecraft:mob_spawner"};
        String[] defaultTier3SoulSand = {"minecraft:end_stone"};

        prop = con.get("Displacements.Tier3", "minecraft:stone", defaultTier3Stone);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:stone"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:diamond_block", defaultTier3Diamond);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:diamond_block"), prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:soul_sand", defaultTier3SoulSand);
        DisplaceListBuilder.Instance().AddDisplacement(2, BlockReference.readBlockFromString("minecraft:soul_sand"), prop.getStringList());
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


}
