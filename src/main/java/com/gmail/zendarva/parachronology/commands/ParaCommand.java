package com.gmail.zendarva.parachronology.commands;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by James on 4/13/2018.
 */
public class ParaCommand extends CommandBase implements IClientCommand {
    @Override
    public String getName() {
        return "para";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/para hand will copy the json for an itemstack to your clipboard.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayerSP) {
            EntityPlayer player = (EntityPlayer) sender;
            if (args[0].equals("hand")) {

                BlockReference reference = BlockReference.fromItemStack(player.getHeldItem(EnumHand.MAIN_HAND));

                System.out.println(ConfigManager.getJsonForBlockReference(reference));

                StringSelection selection = new StringSelection(ConfigManager.getJsonForBlockReference(reference));
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                player.sendMessage(new TextComponentString("The blockreference has been copied to your clipboard."));
                return;
            }
            if (args[0].equals("from")){
                BlockReference reference = ConfigManager.getBlockReferenceFromJson(args[1]);
                System.out.println("Whoop whoop");
            }
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
}
