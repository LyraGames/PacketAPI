package net.lyragames.packet.player.difficulty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumDifficulty {

    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int id;
}
