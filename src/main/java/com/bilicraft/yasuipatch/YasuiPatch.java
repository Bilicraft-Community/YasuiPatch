package com.bilicraft.yasuipatch;

import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class YasuiPatch extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity instanceof Mob) {
                patchMob((Mob) entity);
            }
        }));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void patchMob(Mob mob){
        if(!mob.isAware()) {
            mob.setAware(true);
            getLogger().info("Patched the mob at " + mob.getLocation() + ": " + mob.getName());
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk())
            return;
        Bukkit.getScheduler().runTask(this, () -> Arrays.stream(event.getChunk().getEntities()).forEach((entity -> {
            if (entity instanceof Mob) {
                patchMob((Mob) entity);
            }
        })));
    }
}
