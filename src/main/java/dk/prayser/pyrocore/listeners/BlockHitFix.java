package dk.prayser.pyrocore.listeners;

import dk.prayser.pyrocore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class BlockHitFix implements Listener {
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        int current = player.getInventory().getHeldItemSlot();

        if (player.getItemInHand().getType() == Material.IRON_SWORD || player.getItemInHand().getType() == Material.DIAMOND_SWORD || player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.WOOD_SWORD) {
            int newSlot = current == 0 ? 1 : current - 1;
            player.getInventory().setHeldItemSlot(newSlot);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                player.getInventory().setHeldItemSlot(current);
            }, 5L);
        }
    }
}
