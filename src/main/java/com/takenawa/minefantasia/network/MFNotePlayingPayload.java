package com.takenawa.minefantasia.network;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MFNotePlayingPayload(String instrumentId, String key) implements CustomPacketPayload {
    public static final Type<MFNotePlayingPayload> NOTE_PLAYING_DATA_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "note_played"));

    public static final StreamCodec<FriendlyByteBuf, MFNotePlayingPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MFNotePlayingPayload::instrumentId,
            ByteBufCodecs.STRING_UTF8,
            MFNotePlayingPayload::key,
            MFNotePlayingPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return NOTE_PLAYING_DATA_TYPE;
    }
}
