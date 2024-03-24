package net.crewco.AnglesAndDevils.listeners.SystemListeners

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

class MobSpawnPreventionListener: Listener {
	@EventHandler
	fun onSpawn(e:CreatureSpawnEvent){
		val worlds = Bukkit.getWorlds()
		val angleworld = Bukkit.getWorld("angels_world")
		val devilworld = Bukkit.getWorld("devils_world")
		if (worlds.contains(angleworld) || worlds.contains(devilworld)){
			if (e.spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM){
				e.isCancelled = true
			}
		}
	}
}