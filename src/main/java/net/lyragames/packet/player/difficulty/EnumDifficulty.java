package net.lyragames.packet.player.difficulty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumDifficulty {

    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int id;
}
