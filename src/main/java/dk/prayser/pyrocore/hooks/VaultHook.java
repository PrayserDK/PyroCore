package dk.prayser.pyrocore.hooks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.security.acl.Permission;

public class VaultHook {
    private static Economy economy;
    private static Chat chat;
    private static Permission permission;

    public static boolean init() {
        return VaultHook.setup();
    }

    private static boolean setup() {
        RegisteredServiceProvider permissionProvider;
        RegisteredServiceProvider chatProvider;
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("Kunne ikke loade vault ind");
            return false;
        }
        RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }
        if ((chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class)) != null) {
            chat = (Chat)chatProvider.getProvider();
        }
        if ((permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class)) != null) {
            permission = (Permission)permissionProvider.getProvider();
        }
        return economy != null || chat != null || permission != null;
    }

    public static boolean canAfford(OfflinePlayer offlinePlayer, double amount) {
        return !(economy.getBalance(offlinePlayer) >= amount);
    }

    public static double getBank(OfflinePlayer offlinePlayer) {
        return economy.getBalance(offlinePlayer);
    }

    public static void remove(OfflinePlayer offlinePlayer, double amount) {
        economy.withdrawPlayer(offlinePlayer, amount);
    }

    public static void add(OfflinePlayer offlinePlayer, double amount) {
        economy.depositPlayer(offlinePlayer, amount);
    }

    public static String getPrefix(Player player) {
        return chat.getPlayerPrefix(player) != null ? chat.getPlayerPrefix(player) : "";
    }

    public static String getSuffix(Player player) {
        return chat.getPlayerSuffix(player) != null ? chat.getPlayerSuffix(player) : "";
    }
}
