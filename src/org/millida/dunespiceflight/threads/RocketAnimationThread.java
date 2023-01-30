package org.millida.dunespiceflight.threads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;
import org.millida.dunespiceflight.objects.RocketEntity;
import org.millida.dunespiceflight.utils.ChatUtil;
import org.millida.dunespiceflight.utils.RocketManager;

import java.util.HashMap;
import java.util.List;

public class RocketAnimationThread extends AbstractRepeatingThread {
    private static HashMap<RocketEntity, RocketAnimationThread> threads = new HashMap<>();

    private int framesCount;
    private Player captain;
    private Location to;
    private RocketEntity entity;

    public RocketAnimationThread(Player captain, Location to, RocketEntity entity) {
        super(0.5);

        this.captain = captain;
        this.to = to;
        this.entity = entity;

        this.framesCount = 0;

        threads.put(entity, this);
    }

    @Override
    protected void onDone() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(DuneSpiceFlightPlugin.getInstance(), () -> {
            List<Player> passengers = entity.getPassengers();
            RocketEntity rocketEntity = RocketManager.spawnRocket(to);

            captain.eject();
            captain.teleport(to);
            captain.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "Вы успешно прибыли к месту назначения!"));

            passengers.forEach(passenger -> {
                passenger.eject();
                passenger.teleport(to);
                passenger.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "Вы успешно прибыли к месту назначения!"));
            });
        }, 20L);

        entity.remove();
    }

    @Override
    protected void execute() {
        if (framesCount > 10) this.stop();

        entity.getRocketArmorStand().getWorld().spawnParticle(Particle.SMOKE_LARGE, entity.getRocketArmorStand().getLocation(), 300);
        entity.getRocketArmorStand().getWorld().spawnParticle(Particle.LAVA, entity.getRocketArmorStand().getLocation(), 200);

        entity.getRocketArmorStand().teleport(entity.getRocketArmorStand().getLocation().add(0, 1, 0));

        entity.getCaptainArmorStand().removePassenger(captain);
        entity.getCaptainArmorStand().teleport(entity.getCaptainArmorStand().getLocation().add(0, 1, 0));
        entity.getCaptainArmorStand().addPassenger(captain);

        entity.getPassengersArmorStands().forEach(passengerArmorStand -> {
            Player passenger = (Player) passengerArmorStand.getPassenger();

            if (passenger != null) {
                passengerArmorStand.removePassenger(passenger);
                passengerArmorStand.teleport(passengerArmorStand.getLocation().add(0, 1, 0));
                passengerArmorStand.addPassenger(passenger);
            } else {
                passengerArmorStand.teleport(passengerArmorStand.getLocation().add(0, 1, 0));
            }
        });

        framesCount++;
    }

    public static RocketAnimationThread getThread(RocketEntity entity) {
        return threads.get(entity);
    }
}