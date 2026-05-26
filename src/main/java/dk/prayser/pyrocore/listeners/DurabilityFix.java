package dk.prayser.pyrocore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DurabilityFix implements Listener {

    @EventHandler
    public void onDamage(PlayerItemDamageEvent e) {
        if (e.getItem() == null || e.getItem().getType().equals(Material.AIR)) return;
        short current = e.getItem().getDurability();
        short max = e.getItem().getType().getMaxDurability();
        if (current >= max) {
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);
        short newDurability = (short) (current + 1);
        if (newDurability > max) newDurability = max;
        e.getItem().setDurability(newDurability);
    }
}
