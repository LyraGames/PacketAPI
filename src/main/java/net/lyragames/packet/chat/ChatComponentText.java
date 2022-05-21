package net.lyragames.packet.chat;

import lombok.SneakyThrows;
import net.lyragames.packet.util.ReflectionUtil;

public class ChatComponentText {

    @SneakyThrows
    public static Object fromText(String text) {
        return ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(text);
    }
}
