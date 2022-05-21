package net.lyragames.packet.version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class Version {

    private final int majorVersion, minorVersion;
}
