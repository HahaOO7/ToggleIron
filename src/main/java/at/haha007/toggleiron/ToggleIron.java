package at.haha007.toggleiron;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToggleIron extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        Material type = block.getType();
        if (!(type == Material.IRON_TRAPDOOR || type == Material.IRON_DOOR))
            return;
        Player player = event.getPlayer();
        if (player.isSneaking())
            return;

        event.setCancelled(true);
        Openable blockData = (Openable) block.getBlockData();
        boolean open = blockData.isOpen();
        blockData.setOpen(!open);
        block.setBlockData(blockData);
        Sound sound = open ? Sound.BLOCK_IRON_DOOR_CLOSE : Sound.BLOCK_IRON_DOOR_OPEN;
        Location point = event.getInteractionPoint();
        if (point == null) return;
        event.getPlayer().playSound(point, sound, SoundCategory.BLOCKS, 1, 1);
    }

}
