package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.handlers.DrawBlockHandler;
import com.gmail.zendarva.parachronology.handlers.KeyPressHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * Created by James on 9/12/2015.
 */
public class ClientProxy extends CommonProxy {

	public static KeyBinding[] keyBindings;

	@Override
	@SideOnly(Side.CLIENT)
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new DrawBlockHandler());

		keyBindings = new KeyBinding[2];
		keyBindings[0]= new KeyBinding("key.wand.dec", Keyboard.KEY_LBRACKET,"key.parachronology.cat");
		keyBindings[1]= new KeyBinding("key.wand.inc", Keyboard.KEY_RBRACKET,"key.parachronology.cat");
		for (int i = 0; i < keyBindings.length;i++){
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
		MinecraftForge.EVENT_BUS.register(new KeyPressHandler());

	}
	@SideOnly(Side.CLIENT)
	public void registerRenderThings() {
		Parachronology.capturedMoment.registerModel();
		Parachronology.petrifiedWood.registerModel();
		Parachronology.displacer.registerModel();
		Parachronology.upgrade.registerModel();
		Parachronology.moment.registerModel();
		Parachronology.bias.registerModel();
		Parachronology.basicMoment.registerModel();
		Parachronology.enrichedDirt.registerModel();
		Parachronology.storage.registerModel();
		Parachronology.pickaxe.registerModel();
		Parachronology.wand.registerModel();
	}

}
