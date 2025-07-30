package com.bridgemod.chatbridge.command;

import com.bridgemod.chatbridge.ChatBridgeMod;
import com.bridgemod.chatbridge.gui.BridgeGui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public class BridgeCommand extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "bridge";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bridge - Открыть меню настроек чат бриджа";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (Minecraft.getMinecraft().theWorld != null) {
            Minecraft.getMinecraft().displayGuiScreen(new BridgeGui());
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}