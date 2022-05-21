package net.lyragames.packet.util;

import net.lyragames.packet.PacketAPI;

public class ReflectionUtil {

    private static final String name = PacketAPI.getInstance().getPlugin().getServer().getClass().getPackage().getName();
    private static final String version = name.substring(name.lastIndexOf('.') + 1);

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
