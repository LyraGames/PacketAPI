package net.lyragames.packet;

import lombok.Getter;
import net.lyragames.packet.version.Version;
import org.bukkit.plugin.Plugin;

@Getter
public class PacketAPI {

    @Getter private static PacketAPI instance;

    private final Plugin plugin;
    private final Version version;

    /**
     * initialize PacketAPI in your onEnable
     *
     * @param plugin plugin instance
     */

    public PacketAPI(Plugin plugin) {
        instance = this;

        this.plugin = plugin;
        this.version = new Version(Integer.parseInt(plugin.getServer().getClass().getPackage().getName().split("\\.")[3].replaceAll("(v|R[0-9]+)", "").split("_")[0]),
                Integer.parseInt(plugin.getServer().getClass().getPackage().getName().split("\\.")[3].replaceAll("(v|R[0-9]+)", "").split("_")[1]));
    }
}
