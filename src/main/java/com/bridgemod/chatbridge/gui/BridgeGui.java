package com.bridgemod.chatbridge.gui;

import com.bridgemod.chatbridge.ChatBridgeMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class BridgeGui extends GuiScreen {
    private GuiSlider bridgeToggleSlider;
    private GuiButton doneButton;
    
    @Override
    public void initGui() {
        super.initGui();
        
        this.buttonList.clear();
        
        // Создаем ползунок для включения/выключения чат бриджа
        boolean currentState = ChatBridgeMod.instance.getConfigManager().isBridgeChatEnabled();
        bridgeToggleSlider = new BridgeToggleSlider(1, 
            this.width / 2 - 75, 
            this.height / 2 - 10, 
            150, 20, 
            "Чат Бридж: ", 
            currentState ? 1.0F : 0.0F);
        
        this.buttonList.add(bridgeToggleSlider);
        
        // Кнопка "Готово"
        doneButton = new GuiButton(2, this.width / 2 - 50, this.height / 2 + 30, 100, 20, "Готово");
        this.buttonList.add(doneButton);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 2) { // Кнопка "Готово"
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        
        // Заголовок
        this.drawCenteredString(this.fontRendererObj, "Настройки Чат Бриджа", 
            this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        
        // Описание
        String description = "Включите чат бридж для общения с другими игроками, у которых установлен мод";
        this.drawCenteredString(this.fontRendererObj, description, 
            this.width / 2, this.height / 2 - 35, 0xAAAAAA);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    // Кастомный слайдер для переключения чат бриджа
    private class BridgeToggleSlider extends GuiSlider {
        public BridgeToggleSlider(int id, int x, int y, int width, int height, String prefix, float value) {
            super(id, x, y, width, height, prefix, "", 0.0F, 1.0F, value, false, true);
            updateDisplayString();
        }
        
        @Override
        public void updateSlider() {
            boolean enabled = this.sliderValue >= 0.5F;
            ChatBridgeMod.instance.getConfigManager().setBridgeChatEnabled(enabled);
            updateDisplayString();
        }
        
        private void updateDisplayString() {
            boolean enabled = this.sliderValue >= 0.5F;
            this.displayString = "Чат Бридж: " + (enabled ? "ВКЛ" : "ВЫКЛ");
        }
    }
}