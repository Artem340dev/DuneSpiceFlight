package org.millida.dunespiceflight.utils;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;

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

    public static void setMetadata(Entity entity, String key, boolean value) {
        entity.setMetadata(key, new MetadataValue() {
            @Override
            public Object value() {
                return value;
            }

            @Override
            public int asInt() {
                return 0;
            }

            @Override
            public float asFloat() {
                return 0;
            }

            @Override
            public double asDouble() {
                return 0;
            }

            @Override
            public long asLong() {
                return 0;
            }

            @Override
            public short asShort() {
                return 0;
            }

            @Override
            public byte asByte() {
                return 0;
            }

            @Override
            public boolean asBoolean() {
                return value;
            }

            @Override
            public String asString() {
                return null;
            }

            @Override
            public Plugin getOwningPlugin() {
                return DuneSpiceFlightPlugin.getInstance();
            }

            @Override
            public void invalidate() {
            }
        });
    }
}