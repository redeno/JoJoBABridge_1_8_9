package com.bridgemod.chatbridge.network;

import com.bridgemod.chatbridge.ChatBridgeMod;
import com.bridgemod.chatbridge.chat.BridgeChatManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NetworkHandler {
    private Set<UUID> playersWithMod;
    private BridgeChatManager chatManager;
    
    public NetworkHandler() {
        this.playersWithMod = new HashSet<>();
        this.chatManager = new BridgeChatManager();
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        // Отправляем сигнал другим игрокам что у нас есть мод
        broadcastModPresence();
    }
    
    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        playersWithMod.clear();
    }
    
    public void broadcastModPresence() {
        // В реальной реализации здесь бы была отправка пакета через сервер
        // Для демонстрации используем простую систему
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player != null) {
            playersWithMod.add(player.getUniqueID());
        }
    }
    
    public void sendBridgeMessage(String message) {
        if (ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled()) {
            chatManager.sendBridgeMessage(message);
        }
    }
    
    public boolean hasPlayerMod(UUID playerId) {
        return playersWithMod.contains(playerId);
    }
    
    public Set<UUID> getPlayersWithMod() {
        return new HashSet<>(playersWithMod);
    }
    
    public void addPlayerWithMod(UUID playerId) {
        playersWithMod.add(playerId);
    }
    
    public void removePlayerWithMod(UUID playerId) {
        playersWithMod.remove(playerId);
    }
    
    public BridgeChatManager getChatManager() {
        return chatManager;
    }
}