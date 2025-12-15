package com.takenawa.minefantasia.block;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class MFInstrumentBlock extends Block {
    private final String instrumentId;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MFInstrumentBlock(String instrumentId) {
        super(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, instrumentId)))
                .strength(-1.0F, 3600000.0F)
                .explosionResistance(36000000.0F)
                .sound(SoundType.STONE)
                .noOcclusion()
                .dynamicShape()
                .isSuffocating((state, level, pos) -> true)
                .isViewBlocking((state, level, pos) -> true));
        this.instrumentId = instrumentId;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean canHarvestBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Player player) {
        return false;
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 0.0F;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public String getInstrumentId() {
        return instrumentId;
    }
}
