package net.limit.cubliminal.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.limit.cubliminal.client.hud.NoClippingHudOverlay;
import net.limit.cubliminal.init.CubliminalPackets;
import net.limit.cubliminal.util.NoClipEngine;
import net.minecraft.client.option.KeyBinding;

public class KeyInputHandler {

	public static int ticksColliding = 0;


	public static void registerKeyInputs() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			if (NoClipEngine.canNoCLip(client.player) && client.options.forwardKey.isPressed() && client.player.horizontalCollision && !client.player.isSneaking()) {
				if (ticksColliding > 40) {
					ClientPlayNetworking.send(new CubliminalPackets.NoClipC2SPayload(false));
				} else {
					++ticksColliding;
					NoClippingHudOverlay.INSTANCE.setClippingIntoWall(true);
					return;
				}
			}
			ticksColliding = 0;
			NoClippingHudOverlay.INSTANCE.setClippingIntoWall(false);

		});
	}

	public static void register() {
		registerKeyInputs();
	}
}
