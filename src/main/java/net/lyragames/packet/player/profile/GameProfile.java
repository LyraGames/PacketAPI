package net.lyragames.packet.player.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @RequiredArgsConstructor
public class GameProfile {

    private final UUID uuid;
    private final String name;

    private Map<String, Property> properties = new HashMap<>();
}
