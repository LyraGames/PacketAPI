package net.lyragames.packet.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import net.lyragames.packet.PacketAPI;
import net.lyragames.packet.packets.Packet;
import net.lyragames.packet.player.difficulty.EnumDifficulty;
import net.lyragames.packet.player.gamemode.EnumGamemode;
import net.lyragames.packet.player.profile.GameProfile;
import net.lyragames.packet.player.profile.Property;
import net.lyragames.packet.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

@Getter
public class EntityPlayer {

    private final UUID uuid;
    private final String name;

    /**
     * initialize the entity player
     *
     * @param uuid uuid of player
     * @param name name of player
     */

    public EntityPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * gets the player entity player
     *
     * @return {@link Object}
     */

    @SneakyThrows
    public Object getEntityPlayer() {
        Player player = Bukkit.getPlayer(uuid);

        return player.getClass().getMethod("getHandle").invoke(player);
    }

    /**
     * gets the player's ping from the player connection
     *
     * @return {@link Integer}
     */

    @SneakyThrows
    public int getPing() {
        Player player = Bukkit.getPlayer(uuid);
        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

        return entityPlayer.getClass().getDeclaredField("ping").getInt(entityPlayer);
    }

    /**
     * send a packet
     *
     * @param packet packet
     */

    @SneakyThrows
    public void sendPacket(Packet packet) {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

        Field playerConnectionField = entityPlayer.getClass().getDeclaredField("playerConnection");
        playerConnectionField.setAccessible(true);

        Object playerConnection = playerConnectionField.get(entityPlayer);

        playerConnection.getClass().getMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"))
                .invoke(playerConnection, packet.toPacket());
    }

    /**
     * gets the player's game profile which stores the name, uuid and skin data
     *
     * @return {@link GameProfile}
     */

    @SneakyThrows
    public GameProfile getProfile() {
        GameProfile gameProfile = new GameProfile(uuid, name);

        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString() + "?unsigned=false");
        JsonObject textureProperty = new JsonParser().parse(new InputStreamReader(url.openStream())).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

        gameProfile.getProperties().put("textures", new Property("textures", textureProperty.get("value").getAsString(), textureProperty.get("signature").getAsString()));

        return gameProfile;
    }

    /**
     * sets the player's game profile
     *
     * @param profile profile
     */

    @SneakyThrows
    public void setProfile(GameProfile profile) {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Class<?> entityHuman = entityPlayer.getClass().getSuperclass();

        Field gameProfileField;

        if (PacketAPI.getInstance().getVersion().getMajorVersion() >= 1 && PacketAPI.getInstance().getVersion().getMinorVersion() <= 7) {
            gameProfileField = entityHuman.getDeclaredField("i");
        }else if (PacketAPI.getInstance().getVersion().getMajorVersion() >= 1 && PacketAPI.getInstance().getVersion().getMinorVersion() >= 9) {
            gameProfileField = entityHuman.getDeclaredField("bS");
        }else {
            gameProfileField = entityHuman.getDeclaredField("bH");
        }

        gameProfileField.setAccessible(true);

        Property textures = profile.getProperties().get("textures");

        if (PacketAPI.getInstance().getVersion().getMajorVersion() >= 1 && PacketAPI.getInstance().getVersion().getMinorVersion() <= 7) {
            net.minecraft.util.com.mojang.authlib.GameProfile gameProfile = new net.minecraft.util.com.mojang.authlib.GameProfile(profile.getUuid(), profile.getName());
            gameProfile.getProperties().clear();

            gameProfile.getProperties().put("textures", new net.minecraft.util.com.mojang.authlib.properties.Property("textures", textures.getValue(), textures.getSignature()));

            gameProfileField.set(entityPlayer, gameProfile);
        }else {
            com.mojang.authlib.GameProfile gameProfile = new com.mojang.authlib.GameProfile(profile.getUuid(), profile.getName());
            gameProfile.getProperties().clear();

            gameProfile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", textures.getValue(), textures.getSignature()));

            gameProfileField.set(entityPlayer, gameProfile);
        }

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.hidePlayer(player);
            player1.showPlayer(player);
        }
    }

    /**
     * get the player's current world nms difficulty
     *
     * @return {@link EnumDifficulty}
     */

    @SneakyThrows
    public EnumDifficulty getDifficulty() {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Field worldField = entityPlayer.getClass().getDeclaredField("world");

        worldField.setAccessible(true);

        Object world = worldField.get(entityPlayer);
        Object difficulty = world.getClass().getMethod("getDifficulty").invoke(world);

        int ordinal = difficulty.getClass().getDeclaredField("ordinal").getInt(difficulty);

        return EnumDifficulty.values()[ordinal];
    }

    /**
     * get the player's world nms dimension id
     *
     * @return {@link Integer}
     */

    @SneakyThrows
    public int getDimension() {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Field worldField = entityPlayer.getClass().getDeclaredField("world");

        worldField.setAccessible(true);

        Object world = worldField.get(entityPlayer);
        Field worldDataField = world.getClass().getDeclaredField("worldData");

        worldDataField.setAccessible(true);

        Object worldData = worldDataField.get(world);
        Field worldServerField = worldData.getClass().getDeclaredField("world");

        worldServerField.setAccessible(true);

        Object worldServer = worldServerField.get(worldData);
        Field dimensionField = worldServer.getClass().getDeclaredField("dimension");

        return dimensionField.getInt(worldServer);
    }

    /**
     * get the player's world type
     *
     * @return {@link Object}
     */

    @SneakyThrows
    public Object getWorldType() {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Field worldField = entityPlayer.getClass().getDeclaredField("world");

        worldField.setAccessible(true);

        Object world = worldField.get(entityPlayer);
        Field worldDataField = world.getClass().getDeclaredField("worldData");

        worldDataField.setAccessible(true);

        Object worldData = worldDataField.get(world);

        return worldData.getClass().getMethod("getType").invoke(worldData);
    }

    /**
     * get the player's world default nms gamemode
     *
     * @return {@link EnumGamemode}
     */

    @SneakyThrows
    public EnumGamemode getWorldGameMode() {
        Player player = Bukkit.getPlayer(uuid);

        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Field worldField = entityPlayer.getClass().getDeclaredField("world");

        worldField.setAccessible(true);

        Object world = worldField.get(entityPlayer);
        Field worldDataField = world.getClass().getDeclaredField("worldData");

        worldDataField.setAccessible(true);

        Object worldData = worldDataField.get(world);
        Object gameType = worldData.getClass().getMethod("getGameType").invoke(worldData);

        int ordinal = gameType.getClass().getDeclaredField("ordinal").getInt(gameType);

        return EnumGamemode.values()[ordinal];
    }

    /**
     * create the entityplayer instance from the bukkit player
     *
     * @param player player
     * @return {@link EntityPlayer}
     */

    public static EntityPlayer fromPlayer(Player player) {
        return new EntityPlayer(player.getUniqueId(), player.getName());
    }
}
