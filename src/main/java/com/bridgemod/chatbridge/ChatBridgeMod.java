package com.bridgemod.chatbridge;

import com.bridgemod.chatbridge.command.BridgeCommand;
import com.bridgemod.chatbridge.command.BridgeChatCommand;
import com.bridgemod.chatbridge.config.ConfigManager;
import com.bridgemod.chatbridge.network.NetworkHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChatBridgeMod.MODID, version = ChatBridgeMod.VERSION, clientSideOnly = true)
public class ChatBridgeMod {
    public static final String MODID = "chatbridge";
    public static final String VERSION = "1.0.0";
    
    @Mod.Instance(MODID)
    public static ChatBridgeMod instance;
    
    private ConfigManager configManager;
    private NetworkHandler networkHandler;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configManager = new ConfigManager(event.getModConfigurationDirectory());
        networkHandler = new NetworkHandler();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new BridgeCommand());
        ClientCommandHandler.instance.registerCommand(new BridgeChatCommand());
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}