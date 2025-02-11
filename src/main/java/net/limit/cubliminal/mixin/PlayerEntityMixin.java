package net.limit.cubliminal.mixin;

import net.limit.cubliminal.init.CubliminalRegistrar;
import net.limit.cubliminal.util.NoClipEngine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class, priority = 1500)
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Unique
	private static boolean INVIS_PLAYERS_LVL_0 = false;

	@Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;noClip:Z", shift = At.Shift.AFTER))
	private void onTickAfterNoClip(CallbackInfo ci) {
		if (NoClipEngine.isNoClipping((PlayerEntity) (Object) this)) {
			this.noClip = true;
			this.fallDistance = 0;
			this.setOnGround(false);
			this.setFireTicks(0);
		}
	}

	@Inject(method = "updatePose", at = @At("HEAD"), cancellable = true)
	private void onUpdatePose(CallbackInfo ci) {
		if (NoClipEngine.isNoClipping((PlayerEntity) (Object) this)) {
			this.setPose(EntityPose.STANDING);
			ci.cancel();
		}
	}

	@Inject(method = "onSwimmingStart", at = @At("HEAD"), cancellable = true)
	private void onOnSwimmingStart(CallbackInfo ci) {
		if (NoClipEngine.isNoClipping((PlayerEntity) (Object) this)) ci.cancel();
	}

	@Inject(method = "shouldSwimInFluids", at = @At("HEAD"), cancellable = true)
	private void canSwimInFluids(CallbackInfoReturnable<Boolean> cir) {
		if (NoClipEngine.isNoClipping((PlayerEntity) (Object) this)) cir.setReturnValue(false);
	}

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void cancelAttack(Entity target, CallbackInfo ci) {
		if (INVIS_PLAYERS_LVL_0 && target.isPlayer() && this.getWorld().getRegistryKey()
				.equals(CubliminalRegistrar.THE_LOBBY_KEY) && this.getBlockY() < 8) ci.cancel();
	}
	//cancel player interactions below level 0 gabbro ceiling
}
