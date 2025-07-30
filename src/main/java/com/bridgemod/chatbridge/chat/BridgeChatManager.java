package com.bridgemod.chatbridge.chat;

import com.bridgemod.chatbridge.ChatBridgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class BridgeChatManager {
    private static final String BRIDGE_PREFIX = "[BRIDGE]";
    private List<String> bridgeMessages;
    
    public BridgeChatManager() {
        this.bridgeMessages = new ArrayList<>();
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        
        // Проверяем, является ли это сообщением бриджа
        if (message.startsWith(BRIDGE_PREFIX)) {
            if (!ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled()) {
                // Если бридж выключен, скрываем сообщение
                event.setCanceled(true);
            } else {
                // Изменяем форматирование сообщения бриджа
                String bridgeMessage = message.substring(BRIDGE_PREFIX.length()).trim();
                ChatComponentText newMessage = new ChatComponentText(
                    EnumChatFormatting.AQUA + BRIDGE_PREFIX + " " + 
                    EnumChatFormatting.WHITE + bridgeMessage
                );
                event.message = newMessage;
            }
        }
    }
    
    public void sendBridgeMessage(String message) {
        if (!ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled()) {
            return;
        }
        
        String playerName = Minecraft.getMinecraft().thePlayer.getName();
        String formattedMessage = BRIDGE_PREFIX + " <" + playerName + "> " + message;
        
        // Добавляем в локальный список
        bridgeMessages.add(formattedMessage);
        
        // Отправляем в чат (в реальной реализации это бы шло через сервер)
        Minecraft.getMinecraft().thePlayer.sendChatMessage(formattedMessage);
        
        // Показываем сообщение локально с особым форматированием
        displayBridgeMessage(playerName, message, true);
    }
    
    public void receiveBridgeMessage(String playerName, String message) {
        if (ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled()) {
            displayBridgeMessage(playerName, message, false);
        }
    }
    
    private void displayBridgeMessage(String playerName, String message, boolean isOwnMessage) {
        ChatComponentText chatMessage = new ChatComponentText("");
        
        // Префикс бриджа
        ChatComponentText prefix = new ChatComponentText(BRIDGE_PREFIX + " ");
        prefix.getChatStyle().setColor(EnumChatFormatting.AQUA);
        chatMessage.appendSibling(prefix);
        
        // Имя игрока
        ChatComponentText nameComponent = new ChatComponentText("<" + playerName + "> ");
        nameComponent.getChatStyle().setColor(isOwnMessage ? EnumChatFormatting.GREEN : EnumChatFormatting.YELLOW);
        chatMessage.appendSibling(nameComponent);
        
        // Сообщение
        ChatComponentText messageComponent = new ChatComponentText(message);
        messageComponent.getChatStyle().setColor(EnumChatFormatting.WHITE);
        chatMessage.appendSibling(messageComponent);
        
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatMessage);
    }
    
    public List<String> getBridgeMessages() {
        return new ArrayList<>(bridgeMessages);
    }
    
    public void clearBridgeMessages() {
        bridgeMessages.clear();
    }
}