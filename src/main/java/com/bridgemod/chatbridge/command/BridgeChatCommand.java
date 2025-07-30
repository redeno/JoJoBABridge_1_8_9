package com.bridgemod.chatbridge.command;

import com.bridgemod.chatbridge.ChatBridgeMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class BridgeChatCommand extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "bc";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bc <сообщение> - Отправить сообщение в бридж чат";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled()) {
            sender.addChatMessage(new ChatComponentText(
                EnumChatFormatting.RED + "Бридж чат отключен! Используйте /bridge для включения."));
            return;
        }
        
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(
                EnumChatFormatting.RED + "Использование: /bc <сообщение>"));
            return;
        }
        
        String message = String.join(" ", args);
        ChatBridgeMod.instance.getNetworkHandler().sendBridgeMessage(message);
    }
    
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}