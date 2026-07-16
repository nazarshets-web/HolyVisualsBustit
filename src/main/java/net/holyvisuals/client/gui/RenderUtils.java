package net.holyvisuals.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;

public class RenderUtils {
    public static void drawRoundedGlass(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);
        
        context.fill(x, y, x + radius, y + radius, color);
        context.fill(x + width - radius, y, x + width, y + radius, color);
        context.fill(x, y + height - radius, x + radius, y + height, color);
        context.fill(x + width - radius, y + height - radius, x + width, y + height, color);
        
        int borderColor = 0x80FFFFFF;
        context.drawHorizontalLine(x + radius, x + width - radius, y, borderColor);
        context.drawHorizontalLine(x + radius, x + width - radius, y + height - 1, borderColor);
        context.drawVerticalLine(x, y + radius, y + height - radius, borderColor);
        context.drawVerticalLine(x + width - 1, y + radius, y + height - radius, borderColor);
        
        RenderSystem.disableBlend();
    }
}

