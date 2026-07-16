package net.holyvisuals.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    private String currentTab = "Visuals";

    public ClickGuiScreen() {
        super(Text.of("HolyVisuals"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int menuWidth = 400;
        int menuHeight = 250;
        int x = (this.width - menuWidth) / 2;
        int y = (this.height - menuHeight) / 2;

        RenderUtils.drawRoundedGlass(context, x, y, menuWidth, menuHeight, 8, 0x40101010);
        context.drawText(this.textRenderer, "HolyVisuals Client", x + 15, y + 15, 0xFFFFFFFF, true);

        String[] tabs = {"Helper", "Visuals", "HUD"};
        int tabY = y + 50;
        
        for (String tab : tabs) {
            boolean isHovered = mouseX >= x + 15 && mouseX <= x + 100 && mouseY >= tabY && mouseY <= tabY + 20;
            int tabColor = tab.equals(currentTab) ? 0xFF55FF55 : (isHovered ? 0xFFAAAAAA : 0xFFFFFFFF);
            
            if (tab.equals(currentTab)) {
                RenderUtils.drawRoundedGlass(context, x + 10, tabY - 4, 85, 20, 4, 0x60FFFFFF);
            }
            
            context.drawText(this.textRenderer, tab, x + 20, tabY, tabColor, false);
            tabY += 25;
        }

        int contentX = x + 110;
        int contentY = y + 50;
        
        if (currentTab.equals("HUD")) {
            context.drawText(this.textRenderer, "[X] Cooldowns (E-Pearl, G-Apple)", contentX, contentY, 0xFFFFFFFF, false);
            context.drawText(this.textRenderer, "[X] Active Potions HUD", contentX, contentY + 20, 0xFFFFFFFF, false);
        } else {
            context.drawText(this.textRenderer, "Модулі вкладки " + currentTab + " в розробці...", contentX, contentY, 0xFFAAAAAA, false);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - 400) / 2;
        int y = (this.height - 250) / 2;

        String[] tabs = {"Helper", "Visuals", "HUD"};
        int tabY = y + 50;
        
        for (String tab : tabs) {
            if (mouseX >= x + 15 && mouseX <= x + 100 && mouseY >= tabY && mouseY <= tabY + 20) {
                this.currentTab = tab;
                return true;
            }
            tabY += 25;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

