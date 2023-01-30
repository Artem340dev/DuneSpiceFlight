package org.millida.dunespiceflight.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;
import org.millida.dunespiceflight.objects.RocketPlayerData;
import org.millida.dunespiceflight.objects.RocketEntity;
import org.millida.dunespiceflight.threads.RocketAnimationThread;
import org.millida.dunespiceflight.utils.ChatUtil;
import org.millida.dunespiceflight.utils.RocketManager;
import org.millida.dunespiceflight.utils.TownyManager;
import org.spigotmc.event.entity.EntityDismountEvent;

import javax.swing.text.html.CSS;

public class WorldHandler implements Listener {
    @EventHandler
    public void onSitInRocket(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            RocketEntity rocketEntity = RocketEntity.getRocketEntity(armorStand);

            if (rocketEntity != null && !rocketEntity.getRocketArmorStand().equals(armorStand)) {
                if (armorStand.getPassengers().size() > 0) {
                    event.getPlayer().sendMessage(ChatUtil.parseColor("&cЭто место уже занято!"));
                } else {
                    if (rocketEntity.getCaptainArmorStand().equals(armorStand)) {
                        if (!TownyManager.isWilderness(armorStand.getLocation()) && !TownyManager.isResident(event.getPlayer(), armorStand.getLocation())) {
                            event.getPlayer().sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы не можете заспавнить корабль на территории чужого города!"));
                            return;
                        }
                    }

                    armorStand.addPassenger(event.getPlayer());
                    event.getPlayer().sendMessage(ChatUtil.parseColor("&aВы сели в космический корабль."));
                }
            }
        }
    }

    /*@EventHandler
    public void onRocketDamage(EntityDamageByEntityEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND) || !event.getDamager().getType().equals(EntityType.PLAYER))
            return;

        ArmorStand armorStand = (ArmorStand) event.getEntity();
        RocketEntity rocketEntity = RocketEntity.getRocketEntity(armorStand);

        if (!rocketEntity.getRocketArmorStand().equals(armorStand)) return;

        RocketAnimationThread thread = RocketAnimationThread.getThread(rocketEntity);
        if (thread == null) return;

        event.setCancelled(true);
    }*/

    @EventHandler
    public void onRocketDeath(EntityDeathEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND)) return;

        ArmorStand armorStand = (ArmorStand) event.getEntity();
        Player player = event.getEntity().getKiller();
        RocketEntity rocketEntity = RocketEntity.getRocketEntity(armorStand);

        if (rocketEntity == null) return;

        rocketEntity.remove();
        if (player != null) player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Вы сломали ракету!"));
    }

    @EventHandler
    public void onRocketPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem() || !event.getItem().equals(RocketManager.getRocketItem())) return;

        if (!TownyManager.isWilderness(event.getClickedBlock().getLocation()) && !TownyManager.isResident(event.getPlayer(), event.getClickedBlock().getLocation())) {
            event.getPlayer().sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы не можете заспавнить корабль на территории чужого города!"));
            return;
        }

        if (RocketPlayerData.getRocketDataOrNull(event.getPlayer()) == null) new RocketPlayerData(event.getPlayer());
        if (RocketEntity.getRocketEntityForCaptain(event.getPlayer()) != null) {
            event.getPlayer().sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы уже заспавнили корабль!"));
            return;
        }

        if (!event.getClickedBlock().getLocation().add(0, 1, 0).getBlock().isEmpty()) {
            event.getPlayer().sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! Некуда заспавнить корабль."));
            return;
        }

        event.setCancelled(true);

        RocketEntity entity = RocketManager.spawnRocket(event.getClickedBlock().getLocation().add(0, 1, 0));
        event.getPlayer().sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Космический корабль был заспавнен."));
        event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
    }
}