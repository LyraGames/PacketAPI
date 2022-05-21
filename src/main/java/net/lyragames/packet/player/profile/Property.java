package net.lyragames.packet.player.profile;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Data
public class Property {

    private final String name, value, signature;
}
