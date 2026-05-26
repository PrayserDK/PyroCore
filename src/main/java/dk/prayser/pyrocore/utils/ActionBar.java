package dk.prayser.pyrocore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ActionBar {
    public static void sendActionbar(Player player, String message) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = handle.getClass().getField("playerConnection").get(handle);
            Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + getVersion() + ".IChatBaseComponent$ChatSerializer");
            Class<?> packetClass = Class.forName("net.minecraft.server." + getVersion() + ".PacketPlayOutChat");
            Object chatComponent = chatSerializerClass.getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
            Constructor<?> packetConstructor = packetClass.getConstructor(chatSerializerClass.getDeclaringClass(), byte.class);
            Object packet = packetConstructor.newInstance(chatComponent, (byte) 2);
            connection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + getVersion() + ".Packet")).invoke(connection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}
