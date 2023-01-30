package org.millida.dunespiceflight.utils;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class NMSManager {
    public static void setNBTTag(Entity entity, String name, boolean value) {
        net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        EntityLiving nmsEntityLiving = (EntityLiving) nmsEntity;

        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean(name, value);

        nmsEntity.a_(tag);
    }
}