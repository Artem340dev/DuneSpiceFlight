package org.millida.dunespiceflight.utils;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class NMSManager {
    public static void setNBTTag(Entity entity, String key, int value) {
        net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInt(key, value);

        nmsEntity.a_(nbtTagCompound);
    }

    public static void setNBTTags(Entity entity, Map<String, Integer> tags) {
        net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tags.forEach((key, value) -> nbtTagCompound.setInt(key, value));

        nmsEntity.a_(nbtTagCompound);
    }
}