package dk.prayser.pyrocore.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

public class IronDoorListener implements Listener {

    private final WorldGuardPlugin wg;

    public IronDoorListener() {
        this.wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    }

    @EventHandler
    public void onRightClickDoor(PlayerInteractEvent event) {
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || wg == null) return;
        if (!wg.canBuild(event.getPlayer(), clickedBlock)) return;
        if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null) {
            if (clickedBlock.getType() == Material.IRON_DOOR_BLOCK) {
                Block doorBaseBlock = clickedBlock;

                if ((doorBaseBlock.getData() & 0x8) == 0x8) {
                    doorBaseBlock = doorBaseBlock.getRelative(0, -1, 0);
                }

                BlockState blockState = doorBaseBlock.getState();
                Door door = (Door) blockState.getData();

                door.setOpen(!door.isOpen());
                blockState.setData(door);
                blockState.update();

                event.setCancelled(true);
            }
        }
    }
}
