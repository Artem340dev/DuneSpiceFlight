package org.millida.dunespiceflight.threads;

import org.bukkit.Bukkit;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;

public abstract class AbstractRepeatingThread {
    private double interval;
    private int task;

    public AbstractRepeatingThread(double interval) {
        this.interval = interval;
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DuneSpiceFlightPlugin.getInstance(), () -> {
            this.execute();
        }, (int)(interval*20L), (int)(interval*20L));
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(task);
        this.onDone();
    }

    public double getInterval() {
        return interval;
    }

    public int getTask() {
        return task;
    }

    protected abstract void onDone();
    protected abstract void execute();
}