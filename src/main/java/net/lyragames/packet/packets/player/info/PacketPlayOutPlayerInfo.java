package net.lyragames.packet.packets.player.info;

import lombok.Getter;
import net.lyragames.packet.packets.Packet;
import net.lyragames.packet.player.EntityPlayer;
import net.lyragames.packet.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PacketPlayOutPlayerInfo extends Packet {

    private final EnumPlayerInfoAction action;
    private final List<EntityPlayer> entityPlayers;

    /**
     * initialize a player info update packet
     *
     * @param action update action
     * @param entityPlayers players that will be updates
     */

    public PacketPlayOutPlayerInfo(EnumPlayerInfoAction action, EntityPlayer... entityPlayers) {
        this.action = action;
        this.entityPlayers = Arrays.asList(entityPlayers);
    }

    @Override
    public Object toPacket() {
        try {

            return ReflectionUtil.getNMSClass("PacketPlayOutPlayerInfo").getConstructor(
                    ReflectionUtil.getNMSClass("PacketPlayOutPlayerInfo.EnumPlayerInfoAction"),
                    ReflectionUtil.getNMSClass("EntityPlayer")
            ).newInstance(ReflectionUtil.getNMSClass("PacketPlayOutPlayerInfo.EnumPlayerInfoAction").getEnumConstants()[action.ordinal()],
                    entityPlayers.stream().map(EntityPlayer::getEntityPlayer).collect(Collectors.toList()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
