package com.petproject.youtubeclone.utils;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class VideoIdGenerator {
    public static String generateVideoId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array());
    }
}
