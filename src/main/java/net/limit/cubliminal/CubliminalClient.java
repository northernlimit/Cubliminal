package net.limit.cubliminal;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.limit.cubliminal.client.hud.NoClippingHudOverlay;
import net.limit.cubliminal.client.hud.SanityBarHudOverlay;
import net.limit.cubliminal.entity.client.BacteriaModel;
import net.limit.cubliminal.entity.client.BacteriaRenderer;
import net.limit.cubliminal.event.KeyInputHandler;
import net.limit.cubliminal.init.CubliminalBlocks;
import net.limit.cubliminal.init.CubliminalEntities;
import net.limit.cubliminal.init.CubliminalModelLayers;
import net.limit.cubliminal.init.CubliminalPackets;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class CubliminalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(CubliminalBlocks.THE_LOBBY_GATEWAY_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CubliminalBlocks.EMERGENCY_EXIT_DOOR_0, RenderLayer.getTranslucent());

		KeyInputHandler.register();

		EntityRendererRegistry
				.register(CubliminalEntities.BACTERIA, BacteriaRenderer::new);
		EntityModelLayerRegistry
				.registerModelLayer(CubliminalModelLayers.BACTERIA, BacteriaModel::getTexturedModelData);

		CubliminalPackets.registerS2CPackets();
		HudRenderCallback.EVENT.register(new NoClippingHudOverlay());
		HudRenderCallback.EVENT.register(new SanityBarHudOverlay());
	}
}