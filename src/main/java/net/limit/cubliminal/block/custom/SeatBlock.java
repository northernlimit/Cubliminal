package net.limit.cubliminal.block.custom;

import com.google.common.base.Predicates;
import net.limit.cubliminal.entity.custom.SeatEntity;
import net.limit.cubliminal.init.CubliminalEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class SeatBlock extends Block {
    public SeatBlock(Settings settings) {
        super(settings);
    }

    public float setPassengerYaw(BlockState state, Entity entity) {
        return entity.getYaw();
    }

    public float seatHeight(BlockState state) {
        return 1;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        List<SeatEntity> list = world.getEntitiesByClass(SeatEntity.class, new Box(pos), Predicates.alwaysTrue());
        if (world.isClient()) {
            return ActionResult.CONSUME;
        } else if (list.isEmpty()) {
            SeatEntity seatEntity = CubliminalEntities.SEAT_ENTITY.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
            player.startRiding(seatEntity, true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}