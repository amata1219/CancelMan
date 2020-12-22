package amata1219.cancel.man;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CancelMan extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        HandlerList.unregisterAll((JavaPlugin) this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().isOp() || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (e.getClickedBlock() == null) return;

        if (e.getClickedBlock().getType() == Material.ANVIL) e.setCancelled(true);
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;

        Player player = (Player)e.getPlayer();
        ItemStack item = player.getInventory().getItem(40);
        if (item == null || item.getType() == Material.AIR) return;

        if (player.getInventory().firstEmpty() == -1) player.getWorld().dropItem(player.getLocation(), item);
        else player.getInventory().addItem(item);

        player.getInventory().setItem(40, null);
    }

}