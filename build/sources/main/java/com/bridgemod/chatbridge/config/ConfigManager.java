package com.bridgemod.chatbridge.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigManager {
    private Configuration config;
    private boolean bridgeChatEnabled;
    
    public ConfigManager(File configDir) {
        File configFile = new File(configDir, "chatbridge.cfg");
        config = new Configuration(configFile);
        loadConfig();
    }
    
    private void loadConfig() {
        config.load();
        
        bridgeChatEnabled = config.getBoolean("bridgeChatEnabled", "general", false, 
            "Включить/выключить чат бридж");
        
        if (config.hasChanged()) {
            config.save();
        }
    }
    
    public void saveConfig() {
        config.get("general", "bridgeChatEnabled", false).set(bridgeChatEnabled);
        config.save();
    }
    
    public boolean isBridgeChatEnabled() {
        return bridgeChatEnabled;
    }
    
    public void setBridgeChatEnabled(boolean enabled) {
        this.bridgeChatEnabled = enabled;
        saveConfig();
    }
}