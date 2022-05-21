package net.lyragames.packet.packets.player;

import lombok.Getter;
import lombok.SneakyThrows;
import net.lyragames.packet.PacketAPI;
import net.lyragames.packet.packets.Packet;
import net.lyragames.packet.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Getter
public class PacketPlayOutPlayerListHeaderFooter extends Packet {

    private final String header;
    private final String footer;

    /**
     * initialize PlayerListHeaderFooter packet which is used to change the header & footer of the tablist
     *
     * @param header header
     * @param footer footer
     */

    public PacketPlayOutPlayerListHeaderFooter(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    @Override
    public Object toPacket() {
        try {
            Object packet = ReflectionUtil.getNMSClass("PacketPlayOutPlayerListHeaderFooter").newInstance();

            if (PacketAPI.getInstance().getVersion().getMinorVersion() >= 14) {
                packet.getClass().getField("header").set(packet, ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(header));
            }else {
                Field headerField = packet.getClass().getDeclaredField("a");
                headerField.setAccessible(true);
                headerField.set(packet, ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(header));
            }

            if (PacketAPI.getInstance().getVersion().getMinorVersion() >= 14) {
                packet.getClass().getField("footer").set(packet, ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(footer));
            }else {
                Field footerField = packet.getClass().getDeclaredField("b");
                footerField.setAccessible(true);
                footerField.set(packet, ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(footer));
            }

            return packet;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
