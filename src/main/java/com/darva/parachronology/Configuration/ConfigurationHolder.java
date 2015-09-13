package com.darva.parachronology.Configuration;

import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.TransformListBuilder;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * Created by James on 8/24/2015.
 */
public class ConfigurationHolder {

    private static ConfigurationHolder instance;

    private ConfigurationHolder()
    {

    }

    public static ConfigurationHolder getInstance()
    {
        if (instance == null)
        {
            instance = new ConfigurationHolder();
        }
        return instance;
    }

    public void LoadConfigs(Configuration con)
    {
        loadDisplacements(con);
        loadTransforms(con);


    }


    private void loadDisplacements(Configuration con)
    {
        String[] defaultTier1Wood = {"minecraft:stone", "minecraft:cobblestone", "minecraft:gravel"};
        String[] defaultTier1Cobble ={"minecraft:iron_ore", "minecraft:coal_ore", "minecraft:dirt"};
        String[] defaultTier1IronBlock = {"minecraft:obsidian"};

        Property prop = con.get("Displacements.Tier1", "minecraft:log",defaultTier1Wood);
        DisplaceListBuilder.Instance().AddDisplacement(1,Blocks.log, prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:cobblestone",defaultTier1Cobble);
        DisplaceListBuilder.Instance().AddDisplacement(1,Blocks.cobblestone, prop.getStringList());
        prop = con.get("Displacements.Tier1", "minecraft:iron_block", defaultTier1IronBlock);
        DisplaceListBuilder.Instance().AddDisplacement(1, Blocks.iron_block, prop.getStringList());


        String[] defaultTier2Dirt = {"minecraft:melon_block", "minecraft:mycelium", "minecraft:pumpkin", "minecraft:waterlily" };
        String[] defaultTier2Cobble = {"minecraft:gold_ore"};
        String[] defaultTier2Stone = {"minecraft:lapis_ore", "minecraft:emerald_ore", "minecraft:redstone_ore"};
        String[] defaultTier2Netherrack = {"minecraft:sand", "minecraft:clay"};
        String[] defaultTier2Sand = {"minecraft:cactus", "minecraft:sandstone"};

        prop = con.get("Displacements.Tier2", "minecraft:dirt", defaultTier2Dirt);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.dirt, prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:cobble", defaultTier2Cobble);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.cobblestone, prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:stone", defaultTier2Stone);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.stone, prop.getStringList());

        prop = con.get("Displacements.Tier2", "minecraft:netherrack", defaultTier2Netherrack);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.netherrack, prop.getStringList());
        prop = con.get("Displacements.Tier2", "minecraft:sand", defaultTier2Sand);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.sand, prop.getStringList());


        String[] defaultTier3Stone = {"minecraft:diamond_ore"};
        String[] defaultTier3Diamond = {"minecraft:mob_spawner"};
        String[] defaultTier3SoulSand = {"minecraft:end_stone"};

        prop = con.get("Displacements.Tier3", "minecraft:stone", defaultTier3Stone);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.stone, prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:diamond_block", defaultTier3Diamond);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.diamond_block, prop.getStringList());
        prop = con.get("Displacements.Tier3", "minecraft:soul_sand", defaultTier3SoulSand);
        DisplaceListBuilder.Instance().AddDisplacement(2,Blocks.soul_sand, prop.getStringList());
    }

    private void loadTransforms(Configuration con)
    {

        String[] defaultTeir1Zombie = {"Cow","Chicken", "Pig", "Sheep"};
        String[] defaultTier1Pigman = {"Pig"};
        String[] defaultTier1Spider = {"Sheep", "Chicken"};
        String[] defaultTier1Slime = {"LavaSlime"};
        String[] defaultTier1Cube = {"Slime"};
        String[] defaultTier2Enderman = {"Villager"};

        Property prop = con.get("Transforms.Tier1", "Zombie", defaultTeir1Zombie);
        TransformListBuilder.Instance().addTransform(1,"Zombie", defaultTeir1Zombie);

        prop = con.get("Transforms.Tier1", "PigZombie", defaultTier1Pigman);
        TransformListBuilder.Instance().addTransform(1,"PigZombie", defaultTier1Pigman);

        prop = con.get("Transforms.Tier1", "Spider", defaultTier1Spider);
        TransformListBuilder.Instance().addTransform(1,"Spider", defaultTier1Spider);

        prop = con.get("Transforms.Tier1", "Slime", defaultTier1Slime);
        TransformListBuilder.Instance().addTransform(1, "Slime", defaultTier1Slime);

        prop = con.get("Transforms.Tier1", "LavaSlime", defaultTier1Cube);
        TransformListBuilder.Instance().addTransform(1, "LavaSlime", defaultTier1Cube);

        prop = con.get("Transforms.Tier2", "Enderman", defaultTier2Enderman);
        TransformListBuilder.Instance().addTransform(2,"Enderman",defaultTier2Enderman);

    }


}
