package net.holyvisuals.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.holyvisuals.client.gui.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;

public class HudOverlayHandler implements HudRenderCallback {

    public static void register() {
        HudRenderCallback.EVENT.register(new HudOverlayHandler());
    }

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        // --- БЛОК COOLDOWNS ---
        int cooldownY = client.getWindow().getScaledHeight() / 2 - 40;
        int cooldownX = 10;
        
        float pearlCooldown = client.player.getItemCooldownManager().getCooldownProgress(Items.ENDER_PEARL, tickDelta);
        float gapCooldown = client.player.getItemCooldownManager().getCooldownProgress(Items.GOLDEN_APPLE, tickDelta);
        float egapCooldown = client.player.getItemCooldownManager().getCooldownProgress(Items.ENCHANTED_GOLDEN_APPLE, tickDelta);

        if (pearlCooldown > 0 || gapCooldown > 0 || egapCooldown > 0) {
            RenderUtils.drawRoundedGlass(context, cooldownX, cooldownY, 130, 70, 6, 0x30000000);
            context.drawText(client.textRenderer, "✦ COOLDOWNS ✦", cooldownX + 10, cooldownY + 8, 0xFFFFAA00, false);
            
            int offset = 22;
            if (pearlCooldown > 0) {
                int timeLeft = (int) (pearlCooldown * 15);
                context.drawText(client.textRenderer, "E-Pearl: " + timeLeft + "s", cooldownX + 10, cooldownY + offset, 0xFFFFFFFF, false);
                offset += 15;
            }
            if (gapCooldown > 0) {
                int timeLeft = (int) (gapCooldown * 10);
                context.drawText(client.textRenderer, "G-Apple: " + timeLeft + "s", cooldownX + 10, cooldownY + offset, 0xFFFFFFFF, false);
                offset += 15;
            }
            if (egapCooldown > 0) {
                int timeLeft = (int) (egapCooldown * 300);
                context.drawText(client.textRenderer, "God Apple: " + timeLeft + "s", cooldownX + 10, cooldownY + offset, 0xFFFFFFFF, false);
            }
        }

        // --- БЛОК POTIONS ---
        if (!client.player.getStatusEffects().isEmpty()) {
            int potionX = client.getWindow().getScaledWidth() - 140;
            int potionY = 10;
            
            int bgHeight = 25 + (client.player.getStatusEffects().size() * 14);
            RenderUtils.drawRoundedGlass(context, potionX, potionY, 130, bgHeight, 6, 0x30000000);
            context.drawText(client.textRenderer, "✦ POTIONS ✦", potionX + 15, potionY + 8, 0xFF55FFFF, false);
            
            int effectOffset = 22;
            for (StatusEffectInstance effect : client.player.getStatusEffects()) {
                String name = effect.getEffectType().value().getName().getString();
                int duration = effect.getDuration() / 20;
                String timeStr = String.format("%02d:%02d", duration / 60, duration % 60);
                
                String fullText = name + " " + (effect.getAmplifier() + 1) + " (" + timeStr + ")";
                context.drawText(client.textRenderer, fullText, potionX + 10, potionY + effectOffset, 0xFFFFFFFF, false);
                effectOffset += 14;
            }
        }
    }
}

