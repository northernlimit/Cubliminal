package net.limit.cubliminal.entity.custom;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import net.limit.cubliminal.block.custom.SeatBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class SeatEntity extends Entity {

    public SeatEntity(EntityType<? extends SeatEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) return;
        if (!(this.getBlockStateAtPos().getBlock() instanceof SeatBlock)
                || !this.getWorld().getEntitiesByClass(SeatEntity.class, new Box(this.getBlockPos())
                , Predicate.not(Predicates.alwaysTrue())).isEmpty()
                || this.getPassengerList().isEmpty()) this.discard();
        if (this.isSubmergedInWater()) this.removeAllPassengers();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = this.getBlockPos().toCenterPos();
        double offset = 0;
        for (EntityPose entityPose : passenger.getPoses()) {
            passenger.setPose(entityPose);
            BlockState state = this.getBlockStateAtPos();
            if (state.getBlock() instanceof SeatBlock seatBlock) offset = seatBlock.seatHeight(state);
        }

        return vec3d.add(0, offset - 0.5, 0);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockStateAtPos();
        double seatHeight = 0;
        if (state.getBlock() instanceof SeatBlock seatBlock) seatHeight = seatBlock.seatHeight(state);
        return pos.toCenterPos().add(0, seatHeight - 0.5, 0);
    }

    @Override
    public boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().isEmpty();
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        BlockState state = this.getBlockStateAtPos();
        if (state.getBlock() instanceof SeatBlock seatBlock) passenger.setYaw(seatBlock.setPassengerYaw(state, passenger));
        super.addPassenger(passenger);
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return true;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }
}