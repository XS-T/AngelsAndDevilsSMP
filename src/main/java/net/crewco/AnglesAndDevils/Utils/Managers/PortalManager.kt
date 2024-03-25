package net.crewco.AnglesAndDevils.Utils.Managers

import net.crewco.AnglesAndDevils.Startup.Companion.dataManager
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player


class PortalManager(private val angelsWorld: World, private val devilsWorld: World) {

	fun teleportToAngels(player: Player) {
		if (player.world != angelsWorld){
			player.teleport(angelsWorld.spawnLocation)
			dataManager.saveLocation(player.uniqueId.toString(),player.location)
		}else{
			val overWorld = Bukkit.getWorld("world")!!
			player.teleport(overWorld.spawnLocation)
		}
	}

	fun teleportToDevils(player: Player) {
		if (player.world != devilsWorld){
			player.teleport(devilsWorld.spawnLocation)
			dataManager.saveLocation(player.uniqueId.toString(),player.location)
		}else{
			val overWorld = Bukkit.getWorld("world")!!
			player.teleport(overWorld.spawnLocation)
		}
	}
}