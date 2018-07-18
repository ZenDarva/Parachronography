package com.gmail.zendarva.parachronology.Configuration;

import com.gmail.zendarva.parachronology.Configuration.domain.DropData;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.HashMap;

/**
 * Created by James on 8/24/2015.
 */
public class ConfigurationHolder {

    private static ConfigurationHolder instance;
    private Configuration config;


    public static int playerDropChanceMultiplier = 5;

    private boolean generateEndPortal = true;
    public static  HashMap<String, Boolean> flags = new HashMap<>();

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
        LoadConfigs();
    }

    public void LoadConfigs() {
            loadOtherConfigs(config);
            config.save();

    }

    public void save() {
        config.save();
    }

    private void loadOtherConfigs(Configuration con) {
        Property prop = con.get("Defaults", "allowGenerateEndPortal", true);
        generateEndPortal = prop.getBoolean();

        prop = con.get("Defaults", "PlayerKillDropChanceMultiplyer", 5);
        playerDropChanceMultiplier = prop.getInt();

        prop = con.get("Defaults", "EnableEnderIOGrainsRecipe", true);
        flags.put("EnableEnderIOGrainsRecipe", prop.getBoolean());
        save();

    }






    public boolean isGenerateEndPortal() {
        return generateEndPortal;
    }


}
