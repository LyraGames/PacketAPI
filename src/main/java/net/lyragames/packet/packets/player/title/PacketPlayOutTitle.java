package net.lyragames.packet.packets.player.title;

import lombok.Getter;
import net.lyragames.packet.chat.ChatComponentText;
import net.lyragames.packet.packets.Packet;
import net.lyragames.packet.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

@Getter
public class PacketPlayOutTitle extends Packet {

    private final EnumTitleAction action;
    private final String title;

    /**
     * initialize title packet
     *
     * @param action action that will be executed
     * @param title text that will be shown
     */

    public PacketPlayOutTitle(EnumTitleAction action, String title) {
        this.action = action;
        this.title = title;
    }

    @Override
    public Object toPacket() {
        try {
            Class<?> clazz = ReflectionUtil.getNMSClass("PacketPlayOutTitle.EnumTitleAction");

            return ReflectionUtil.getNMSClass("PacketPlayOutTile").getConstructor(clazz, ReflectionUtil.getNMSClass("IChatBaseComponent"))
                    .newInstance(clazz.getEnumConstants()[action.ordinal()], ChatComponentText.fromText(title));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
