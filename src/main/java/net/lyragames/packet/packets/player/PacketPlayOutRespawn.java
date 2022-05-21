package net.lyragames.packet.packets.player;

import lombok.Getter;
import lombok.SneakyThrows;
import net.lyragames.packet.packets.Packet;
import net.lyragames.packet.player.EntityPlayer;
import net.lyragames.packet.player.difficulty.EnumDifficulty;
import net.lyragames.packet.player.gamemode.EnumGamemode;
import net.lyragames.packet.util.ReflectionUtil;

@Getter
public class PacketPlayOutRespawn extends Packet {

    private final int dimension;
    private final EnumDifficulty enumDifficulty;
    private final EnumGamemode enumGamemode;
    private final Object worldType;

    /**
     * initialize player respawn packet
     *
     * @param entityPlayer player that will be respawned
     */

    public PacketPlayOutRespawn(EntityPlayer entityPlayer) {
        this.dimension = entityPlayer.getDimension();
        this.enumDifficulty = entityPlayer.getDifficulty();
        this.enumGamemode = entityPlayer.getWorldGameMode();
        this.worldType = entityPlayer.getWorldType();
    }

    @SneakyThrows
    @Override
    public Object toPacket() {
        return ReflectionUtil.getNMSClass("PacketPlayOutRespawn").getConstructor(
                int.class, ReflectionUtil.getNMSClass("EnumDifficulty"),
                ReflectionUtil.getNMSClass("WorldType"),
                ReflectionUtil.getNMSClass("EnumGamemode")
        ).newInstance(
                dimension,
                ReflectionUtil.getNMSClass("EnumDifficulty").getEnumConstants()[enumDifficulty.ordinal()],
                worldType,
                ReflectionUtil.getNMSClass("EnumGamemode").getEnumConstants()[enumGamemode.ordinal()]
                );
    }
}
