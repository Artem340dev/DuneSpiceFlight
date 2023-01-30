package org.millida.dunespiceflight.utils;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class EntityUtil {
    public static ArmorStand spawnArmorStand(Location at, boolean canVisible, boolean isInvulnerable, boolean hasGravity, boolean canPickupItems, boolean isCollidable, double maxHealth) {
        ArmorStand armorStand = (ArmorStand) at.getWorld().spawnEntity(at, EntityType.ARMOR_STAND);

        armorStand.setInvulnerable(isInvulnerable);
        armorStand.setVisible(canVisible);
        armorStand.setGravity(hasGravity);
        armorStand.setCanPickupItems(canPickupItems);
        armorStand.setCollidable(isCollidable);

        armorStand.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        armorStand.setHealth(maxHealth);

        return armorStand;
    }
}