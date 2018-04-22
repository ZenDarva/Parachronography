package com.gmail.zendarva.parachronology.Configuration;

import com.gmail.zendarva.parachronology.Configuration.domain.*;
import com.gmail.zendarva.parachronology.Configuration.domain.serialize.BlockReferenceDeserializer;
import com.gmail.zendarva.parachronology.Configuration.domain.serialize.BlockReferenceSerializer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static HashMap<BaseBlockReference, List<BlockReference>> momentTransforms = new HashMap<>();

    public static void writeDisplacements() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class, new BlockReferenceSerializer());
        Gson gson = builder.create();

        for (DisplaceTier displaceTier : DisplaceManager.displacements) {
            File file = new File(configDir, String.format("tier%d.json",displaceTier.getTier()));
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            gson.toJson(displaceTier,osw);
            osw.close();
        }


    }

    public static void readConfigs(){
        //Files.list(configDir.toPath()).filter(f->f.toString().toLowerCase().startsWith("tier"))
        try {
            readMomentTransforms();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeConfigs(){
        try {
            writeMomentTransforms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readMomentTransforms() throws FileNotFoundException {
        File file = new File(configDir,"momentTransforms.json");
        if (file == null || file.exists()== false)
            return;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BlockReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(OreDictReference.class,new BlockReferenceDeserializer());
        builder.registerTypeAdapter(BaseBlockReference.class,new BlockReferenceDeserializer());
        Gson gson = builder.create();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        Type ListType = new TypeToken<HashMap<BaseBlockReference, List<BlockReference>>>(){}.getType();
        momentTransforms=gson.fromJson(reader,ListType);
    }

    private static void writeMomentTransforms() throws IOException {
        File file = new File(configDir,"momentTransforms.json");
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
        gson.toJson(momentTransforms,writer);
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

    public static void createMomentTransforms(){
        OreDictReference ref = new OreDictReference();
        ref.oreDictName="logWood";
        ref.register();

        momentTransforms.put(ref,new LinkedList<>());

        BlockReference target = new BlockReference();
        target.blockName="petrifiedwood";
        target.domain="parachronology";
        target.metadata=0;
        target.register();
        momentTransforms.get(ref).add(target);

    }

    public static List<BlockReference> getMomentTransforms(BaseBlockReference block){
        for (BaseBlockReference ref : momentTransforms.keySet()){
            if (ref.matches(block))
                    return momentTransforms.get(ref);
        }
        return new LinkedList<>();
    }
}
