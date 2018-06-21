package com.gmail.zendarva.parachronology.Configuration;

import com.gmail.zendarva.parachronology.Configuration.domain.*;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.serialize.BlockReferenceDeserializer;
import com.gmail.zendarva.parachronology.Configuration.domain.serialize.BlockReferenceSerializer;
import com.gmail.zendarva.parachronology.handlers.MobDrop;
import com.gmail.zendarva.parachronology.interop.jei.ParachronologyPlugin;
import com.gmail.zendarva.parachronology.interop.jei.displace.DisplaceRecipe;
import com.gmail.zendarva.parachronology.interop.jei.dislocate.DislocateRecipe;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 4/12/2018.
 */
public class ConfigManager {
    public static File configDir;

    public static HashMap<BaseBlockReference, List<BlockReference>> dislocates = new HashMap<>();
    public static HashMap<String, Transform> mobTransforms = new HashMap<>();
    public static HashMap<String, DropData> mobDrops = new HashMap<>();
    private static List<DisplaceTier> displaceTiers = new LinkedList<>();

    private static DisplaceTier tier1 = new DisplaceTier(1);
    private static DisplaceTier tier2 = new DisplaceTier(2);
    private static DisplaceTier tier3 = new DisplaceTier(3);



    public static void readConfigs(){
        try {
            readDisplacements();
            readDislocates();
            readTransforms();
            readMobDrops();
            buildRecipes();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void buildRecipes() {
        for (DisplaceTier displaceTier : displaceTiers) {
            for (BaseBlockReference blockReference : displaceTier.displacements.keySet()) {
                DisplaceRecipe dr = new DisplaceRecipe(blockReference,displaceTier.displacements.get(blockReference),displaceTier.getTier());
                ParachronologyPlugin.displaceRecipes.add(dr);
            }
        }
        for (BaseBlockReference blockReference : dislocates.keySet()) {
            DislocateRecipe tr = new DislocateRecipe(blockReference, dislocates.get(blockReference));
            ParachronologyPlugin.dislocateRecipes.add(tr);
        }
    }

    public static void writeConfigs(){
        try {
            writeDislocates();
            writeDisplacements();
            buildRecipes();
            writeTransforms();
            writeMobDrops();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMobDrops() throws IOException {
        File file = new File(configDir,"mobdrops.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        Gson gson = builder.create();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        gson.toJson(mobDrops,writer);
        writer.flush();
        writer.close();
    }
    private static void readMobDrops() throws FileNotFoundException {
        File file = new File(configDir,"mobdrops.json");
        if (file == null || file.exists()== false)
            return;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        Type ListType = new TypeToken<HashMap<String, DropData>>(){}.getType();
        mobDrops =gson.fromJson(reader,ListType);
    }
    public static void createMobDrops(){
        mobDrops.put("minecraft:blaze",new DropData("minecraft:blaze",0,3,0));
        mobDrops.put("minecraft:creeper",new DropData("minecraft:creeper",4,0,0));
        mobDrops.put("minecraft:ender_dragon",new DropData("minecraft:enderdragon",0,0,100));
        mobDrops.put("minecraft:enderman",new DropData("minecraft:enderman",3,2,0));
        mobDrops.put("minecraft:ghast",new DropData("minecraft:ghast",0,0,0));
        mobDrops.put("minecraft:magma_cube",new DropData("minecraft:lavaslime",0,1,0));
        mobDrops.put("minecraft:skeleton",new DropData("minecraft:skeleton",4,0,0));
        mobDrops.put("minecraft:slime",new DropData("minecraft:slime",1,0,0));
        mobDrops.put("minecraft:witch",new DropData("minecraft:witch",5,0,0));
        mobDrops.put("minecraft:wither_skeleton",new DropData("minecraft:witherskeleton",0,3,0));
        mobDrops.put("minecraft:zombie",new DropData("minecraft:zombie",3,0,0));
        mobDrops.put("minecraft:pigzombie",new DropData("minecraft:pigzombie",0,3,0));
        mobDrops.put("minecraft:wither",new DropData("minecraft:wither",0,0,100));

    }

    private static void readTransforms() throws FileNotFoundException {
        File file = new File(configDir,"transforms.json");
        if (file == null || file.exists()== false)
            return;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        Type ListType = new TypeToken<HashMap<String, Transform>>(){}.getType();
        mobTransforms =gson.fromJson(reader,ListType);

    }

    private static void writeTransforms() throws IOException {
        File file = new File(configDir,"transforms.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        Gson gson = builder.create();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        gson.toJson(mobTransforms,writer);
        writer.flush();
        writer.close();
    }
    public static void createTransforms(){
        Transform transform = new Transform(1);
        mobTransforms.put("minecraft:magma_cube", transform);
        transform.addTransform("minecraft:slime",1);

        transform = new Transform(1);
        mobTransforms.put("minecraft:slime", transform);
        transform.addTransform("minecraft:lavaslime",1);

        transform = new Transform(1);
        mobTransforms.put("minecraft:pigzombie", transform);
        transform.addTransform("minecraft:pig",1);

        transform = new Transform(1);
        mobTransforms.put("minecraft:spider", transform);
        transform.addTransform("minecraft:sheep",1);
        transform.addTransform("minecraft:chicken",1);

        transform = new Transform(1);
        mobTransforms.put("minecraft:zombie", transform);
        transform.addTransform("minecraft:pig",1);
        transform.addTransform("minecraft:chicken",1);
        transform.addTransform("minecraft:cow",2);
        transform.addTransform("minecraft:sheep",1);

        transform = new Transform(2);
        mobTransforms.put("minecraft:enderman", transform);
        transform.addTransform("minecraft:villager",1);

        transform = new Transform(2);
        mobTransforms.put("minecraft:squid", transform);
        transform.addTransform("minecraft:guardian",1);
    }

    private static void readDislocates() throws FileNotFoundException {
        File file = new File(configDir,"dislocates.json");
        if (file == null || file.exists()== false)
            return;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(BaseBlockReference.class,new BlockReferenceDeserializer());
        Gson gson = builder.create();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        Type ListType = new TypeToken<HashMap<BaseBlockReference, List<BlockReference>>>(){}.getType();
        dislocates =gson.fromJson(reader,ListType);
    }

    private static void writeDislocates() throws IOException {
        File file = new File(configDir,"dislocates.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceSerializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceSerializer());
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        Gson gson = builder.create();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        gson.toJson(dislocates,writer);
        writer.flush();
        writer.close();

    }
    private static void readDisplacements() throws FileNotFoundException {
        File file = new File(configDir,"displacements.json");
        if (file == null || file.exists()== false)
            return;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(BaseBlockReference.class,new BlockReferenceDeserializer());
        Gson gson = builder.create();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        Type ListType = new TypeToken<List<DisplaceTier>>(){}.getType();
        displaceTiers=gson.fromJson(reader,ListType);
    }

    private static void writeDisplacements() throws IOException{
        File file = new File(configDir, "displacements.json");
        if (!file.exists()){
            file.createNewFile();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceSerializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceSerializer());
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        Gson gson = builder.create();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        gson.toJson(displaceTiers,writer);
        writer.flush();
        writer.close();

    }

    public static String getJsonForBlockReference(BlockReference ref){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceSerializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceSerializer());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(ref);
    }
    public static BlockReference getBlockReferenceFromJson(String json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(json, BlockReference.class);
    }

    public static void createDislocations(){
        OreDictReference ref = (OreDictReference) BaseBlockReference.getReference("logWood");
        dislocates.put(ref,new LinkedList<>());
        BlockReference target = (BlockReference) BaseBlockReference.getReference(0,"petrifiedwood","parachronology",null);
        dislocates.get(ref).add(target);

        BlockReference whiteWool = (BlockReference) BaseBlockReference.getReference(0,"wool","minecraft");
        dislocates.put(whiteWool,new LinkedList<>());
        for (int x = 1; x<16;x++)
            dislocates.get(whiteWool).add((BlockReference) BaseBlockReference.getReference(x,"wool","minecraft"));

    }

    public static List<BlockReference> getDislocates(BaseBlockReference block){
        for (BaseBlockReference ref : dislocates.keySet()){
            if (ref.matches(block))
                    return dislocates.get(ref);
        }
        return new LinkedList<>();
    }

    public static void createDisplacements(){
        //Add ores all easy to initilization


        displaceTiers.add(tier1);
        displaceTiers.add(tier2);
        displaceTiers.add(tier3);
        //Tier 1 wood.:
        BaseBlockReference woodOre = BlockReference.getReference("logWood");
        BaseBlockReference cobbleOre = BlockReference.getReference("cobblestone");
        BaseBlockReference treeSapling = BlockReference.getReference("treeSapling");
        BaseBlockReference enrichedDirt = BlockReference.getReference(0,"enricheddirt","parachronology");

        DisplaceResult result;

        BlockReference cobble = (BlockReference) BaseBlockReference.getReference(0,"cobblestone","minecraft");
        result = new DisplaceResult(cobble,2);
        tier1.addDisplacement(woodOre, result);

        BlockReference stone = (BlockReference) BaseBlockReference.getReference(0,"stone","minecraft");
        result = new DisplaceResult(stone,1);
        tier1.addDisplacement(woodOre, result);

        BlockReference gravel = (BlockReference) BaseBlockReference.getReference(0,"gravel","minecraft");
        result = new DisplaceResult(gravel,2);
        tier1.addDisplacement(woodOre, result);



        BlockReference coal = (BlockReference) BlockReference.getReference(0,"coal_ore","minecraft");
        result = new DisplaceResult(coal,2);
        tier1.addDisplacement(cobbleOre,result);

        BlockReference dirt = (BlockReference) BaseBlockReference.getReference(0,"dirt","minecraft");
        result = new DisplaceResult(dirt,1);
        tier1.addDisplacement(cobbleOre, result);

        BlockReference iron_ore = (BlockReference) BaseBlockReference.getReference(0,"iron_ore","minecraft");
        result = new DisplaceResult(iron_ore,2);
        tier1.addDisplacement(cobbleOre, result);

        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(4,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(5,"sapling","minecraft"),1));
        tier1.addDisplacement(treeSapling, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"reeds","minecraft"),1));

        tier1.addDisplacement(enrichedDirt, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"obsidian","minecraft"),1));

        tier2.addDisplacement(cobbleOre, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"gold_ore","minecraft"),2));

        tier2.addDisplacement(dirt,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"melon_block","minecraft"),2));
        tier2.addDisplacement(dirt,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"mycelium","minecraft"),1));
        tier2.addDisplacement(dirt,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"pumpkin","minecraft"),2));
        tier2.addDisplacement(dirt,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"waterlily","minecraft"),1));
        tier2.addDisplacement(dirt,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"grass","minecraft"),2));

        BlockReference goldBlock = (BlockReference) BaseBlockReference.getReference(0,"gold_block","minecraft");
        tier2.addDisplacement(goldBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"diamond_ore","minecraft"),1));

        OreDictReference oreNetherrack = (OreDictReference) BaseBlockReference.getReference("netherrack");

        tier2.addDisplacement(oreNetherrack,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sand","minecraft"),3));
        tier2.addDisplacement(oreNetherrack,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"clay","minecraft"),3));
        tier2.addDisplacement(oreNetherrack,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"sand","minecraft"),1));

        OreDictReference oreSand = (OreDictReference) BaseBlockReference.getReference("sand");

        tier2.addDisplacement(oreSand,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"cactus","minecraft"),1));
        tier2.addDisplacement(oreSand,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sandstone","minecraft"),1));

        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");

        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"lapis_ore","minecraft"),3));
        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"emerald_ore","minecraft"),1));
        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"redstone_ore","minecraft"),3));

        tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"diamond_ore","minecraft"),1) );

        BaseBlockReference soulSand = BaseBlockReference.getReference(0,"soulsand","minecraft");
        tier3.addDisplacement(soulSand,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"end_stone","minecraft"),1) );

        BaseBlockReference diamondBlock = BaseBlockReference.getReference(0,"diamond_block","minecraft");
        tier3.addDisplacement(diamondBlock, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"mob_spawner","minecraft","{\"MaxNearbyEntities\":6,\"RequiredPlayerRange\":16,\"SpawnCount\":4,\"SpawnData\":{\"id\":\"minecraft:skeleton\"},\"MaxSpawnDelay\":800,\"Delay\":0,\"id\":\"minecraft:mob_spawner\",\"SpawnRange\":4,\"MinSpawnDelay\":200,\"SpawnPotentials\":[{\"Entity\":{\"id\":\"minecraft:skeleton\"},\"Weight\":1}]}"),3));
        tier3.addDisplacement(diamondBlock, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"mob_spawner","minecraft","{\"MaxNearbyEntities\":6,\"RequiredPlayerRange\":16,\"SpawnCount\":4,\"SpawnData\":{\"id\":\"minecraft:zombie\"},\"MaxSpawnDelay\":800,\"Delay\":0,\"id\":\"minecraft:mob_spawner\",\"SpawnRange\":4,\"MinSpawnDelay\":200,\"SpawnPotentials\":[{\"Entity\":{\"id\":\"minecraft:zombie\"},\"Weight\":1}]}"),3));
        tier3.addDisplacement(diamondBlock, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"mob_spawner","minecraft","{\"MaxNearbyEntities\":6,\"RequiredPlayerRange\":16,\"SpawnCount\":4,\"SpawnData\":{\"id\":\"minecraft:spider\"},\"MaxSpawnDelay\":800,\"Delay\":0,\"id\":\"minecraft:mob_spawner\",\"SpawnRange\":4,\"MinSpawnDelay\":200,\"SpawnPotentials\":[{\"Entity\":{\"id\":\"minecraft:spider\"},\"Weight\":1}]}"),3));
        tier3.addDisplacement(diamondBlock, new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"mob_spawner","minecraft","{\"MaxNearbyEntities\":6,\"RequiredPlayerRange\":16,\"SpawnCount\":4,\"SpawnData\":{\"id\":\"minecraft:enderman\"},\"MaxSpawnDelay\":800,\"Delay\":0,\"id\":\"minecraft:mob_spawner\",\"SpawnRange\":4,\"MinSpawnDelay\":200,\"SpawnPotentials\":[{\"Entity\":{\"id\":\"minecraft:enderman\"},\"Weight\":1}]}"),2));



        createModSupportDisplacements();
    }

    private static void createModSupportDisplacements() {
        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");
        BaseBlockReference treeSapling = BlockReference.getReference("treeSapling");

        if (Loader.isModLoaded("thermalfoundation")){
            addTEOres();
        }

        if (Loader.isModLoaded("immersiveengineering")){
            addIEOres();
        }
        if (Loader.isModLoaded("ic2")) {
            addIC2Ores();
        }

        if (Loader.isModLoaded("actuallyadditions")) {
            tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"block_misc","actuallyadditions"),2));
            MinecraftForge.addGrassSeed(new ItemStack(Item.getByNameOrId("actuallyadditions:item_canola_seed")),10);
        }
        if (Loader.isModLoaded("bigreactors")) {
            if (!ConfiguredOres.uranium) {
                tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"brore","bigreactors"),2));
                ConfiguredOres.uranium = true;
            }
        }
        if (Loader.isModLoaded("rftools")) {
            tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"dimensional_shard_ore","rftools"),2));
        }

        if (Loader.isModLoaded("integrateddynamics")){
            tier1.addDisplacement(treeSapling,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"menril_sapling","integrateddynamics"),1));
        }

        if (Loader.isModLoaded("mekanism")){
            tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"oreblock","mekanism"),2));
        }
        if (Loader.isModLoaded("deepresonance")){
            tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"resonating_ore","deepresonance"),1));
        }
        if (Loader.isModLoaded("forestry")) {
            addForestryStuff();
        }
        if (Loader.isModLoaded("extrabees")) {
            addExtraBeesStuff();
        }
        if (Loader.isModLoaded("fp")) {
            addFPStuff();
        }
        if (Loader.isModLoaded("thaumcraft")){
            addThaumcraftStuff();
        }

    }

    private static void addThaumcraftStuff(){
        BlockReference quartzBlock = (BlockReference) BaseBlockReference.getReference(0,"quartz_block","minecraft");
        BaseBlockReference treeSapling = BlockReference.getReference("treeSapling");
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_aer","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_ignis","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_aqua","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_terra","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_ordo","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_perditio","thaumcraft"),3));
        tier2.addDisplacement(quartzBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"crystal_vitium","thaumcraft"),1));



        BlockReference arcaneStone = (BlockReference) BaseBlockReference.getReference(0,"stone_arcane","thaumcraft");
        tier2.addDisplacement(arcaneStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"ore_amber","thaumcraft"),1));
        tier2.addDisplacement(arcaneStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"ore_quartz","thaumcraft"),1));
        tier2.addDisplacement(arcaneStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"ore_cinnabar","thaumcraft"),1));

        tier2.addDisplacement(treeSapling,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sapling_greatwood","thaumcraft"),3) );
        tier2.addDisplacement(treeSapling,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sapling_silverwood","thaumcraft"),3) );

    }

    private static void addExtraBeesStuff(){
        BlockReference beeHouse = (BlockReference) BaseBlockReference.getReference(0,"bee_house","forestry");
        tier2.addDisplacement(beeHouse,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"hive","extrabees"),1));
        tier2.addDisplacement(beeHouse,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"hive","extrabees"),1));
        tier2.addDisplacement(beeHouse,new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"hive","extrabees"),1));
        tier2.addDisplacement(beeHouse,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"hive","extrabees"),1));
    }
    private static void addForestryStuff(){
        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");
        BlockReference blockHay = (BlockReference) BaseBlockReference.getReference(0,"hay_block","minecraft");
        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"resources","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(4,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(5,"beehives","forestry"),1));
        tier2.addDisplacement(blockHay,new DisplaceResult((BlockReference) BaseBlockReference.getReference(6,"beehives","forestry"),1));
    }

    private static void addFPStuff(){
        OreDictReference oreSand = (OreDictReference) BaseBlockReference.getReference("sand");
        tier2.addDisplacement(oreSand,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"neon_sand","fp"),1));
        tier2.addDisplacement(oreSand,new DisplaceResult((BlockReference) BaseBlockReference.getReference(4,"neon_sand","fp"),1));

        BlockReference ironBlock = (BlockReference) BaseBlockReference.getReference(0,"iron_block","minecraft");

        tier2.addDisplacement(ironBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"color_iron","fp"),1));
        tier2.addDisplacement(ironBlock,new DisplaceResult((BlockReference) BaseBlockReference.getReference(8,"color_iron","fp"),1));

    }

    private static void addTEOres() {
        BaseBlockReference woodOre = BlockReference.getReference("logWood");
        BaseBlockReference cobbleOre = BlockReference.getReference("cobblestone");
        BaseBlockReference treeSapling = BlockReference.getReference("treeSapling");
        BaseBlockReference enrichedDirt = BlockReference.getReference(0,"enricheddirt","parachronology");
        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");
        OreDictReference oreSand = (OreDictReference) BaseBlockReference.getReference("sand");
        OreDictReference oreNetherrack = (OreDictReference) BaseBlockReference.getReference("netherrack");

        tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"ore","thermalfoundation"),2));
        tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"ore","thermalfoundation"),2));
        tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"ore","thermalfoundation"),1));
        tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"ore","thermalfoundation"),1));

        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(5,"ore","thermalfoundation"),2));
        tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(6,"ore","thermalfoundation"),1));

        ConfiguredOres.copper = true;
        ConfiguredOres.tin = true;
        ConfiguredOres.silver = true;
        ConfiguredOres.lead = true;
        ConfiguredOres.nickle = true;

    }

    private static void addIEOres(){
        BaseBlockReference cobbleOre = BlockReference.getReference("cobblestone");
        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");

        if (!ConfiguredOres.copper){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"ore","immersiveengineering"),2));
            ConfiguredOres.copper=true;
        }
        if (!ConfiguredOres.aluminum){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"ore","immersiveengineering"),2));
            ConfiguredOres.aluminum=true;
        }
        if (!ConfiguredOres.lead){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"ore","immersiveengineering"),1));
            ConfiguredOres.lead=true;
        }
        if (!ConfiguredOres.silver){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"ore","immersiveengineering"),1));
            ConfiguredOres.silver=true;
        }
        if (!ConfiguredOres.nickle){
            tier2.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(4,"ore","immersiveengineering"),1));
            ConfiguredOres.nickle=true;
        }
        if (!ConfiguredOres.uranium){
            tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(5,"ore","immersiveengineering"),2));
            ConfiguredOres.uranium=true;
        }

    }

    private static void addIC2Ores(){
        BaseBlockReference cobbleOre = BlockReference.getReference("cobblestone");
        BaseBlockReference treeSapling = BlockReference.getReference("treeSapling");
        OreDictReference oreStone = (OreDictReference) BaseBlockReference.getReference("stone");

        if (!ConfiguredOres.copper){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(1,"resource","ic2"),2));
            ConfiguredOres.copper=true;
        }
        if (!ConfiguredOres.lead){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(2,"resource","ic2"),1));
            ConfiguredOres.lead=true;
        }
        if (!ConfiguredOres.tin){
            tier2.addDisplacement(cobbleOre,new DisplaceResult((BlockReference) BaseBlockReference.getReference(3,"resource","ic2"),2));
            ConfiguredOres.tin=true;
        }
        if (!ConfiguredOres.uranium){
            tier3.addDisplacement(oreStone,new DisplaceResult((BlockReference) BaseBlockReference.getReference(4,"resource","ic2"),1));
            ConfiguredOres.uranium=true;
        }
        tier1.addDisplacement(treeSapling,new DisplaceResult((BlockReference) BaseBlockReference.getReference(0,"sapling","ic2"),1));
    }


    public static List<BlockReference> getDisplacements(BaseBlockReference block, int tier){
        List<BlockReference> results = new ArrayList<>();

        for (DisplaceTier displaceTier : displaceTiers) {
            if (displaceTier.getTier() <= tier) {
                results.addAll(displaceTier.getDisplacements(block));
            }
        }
        return results;
    }

    public static EntityLiving getTransformResult(EntityLivingBase entity, int tier ){

        ResourceLocation mobName = EntityList.getKey(entity);
        String rawMobName = mobName.toString();
        for (String s : mobTransforms.keySet()) {
            if (s.equals(rawMobName) && mobTransforms.get(s).getTier()<=tier){
                return mobTransforms.get(s).getResult(entity.world);
            }
        }
        return null;
    }


}
