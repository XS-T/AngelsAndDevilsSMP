package net.crewco.AnglesAndDevils.listeners.SystemListeners

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.dataManager
import net.crewco.AnglesAndDevils.Startup.Companion.portalManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class HomeWorldListener:Listener {
	@EventHandler
	fun onTeleport(e:PlayerJoinEvent){
		val player = e.player
		val team = PStats.getPlayerTeam(player.uniqueId.toString())

		if (team == "Angels"){
			if (dataManager.getLocation(player.uniqueId.toString()) == null) {
				portalManager.teleportToAngels(player)
			}else{
				portalManager.teleportTo(player)
				player.bedSpawnLocation = Bukkit.getWorld("angels_world")!!.spawnLocation
			}
		}else if (team == "Devils"){
			if (dataManager.getLocation(player.uniqueId.toString()) == null) {
				portalManager.teleportToDevils(player)
			}else{
				portalManager.teleportTo(player)
				player.bedSpawnLocation = Bukkit.getWorld("devils_world")!!.spawnLocation
			}
		}
	}

	@EventHandler
	fun onQuit(e:PlayerQuitEvent){
		val player = e.player
		dataManager.saveLocation(player.uniqueId.toString(),player.location)
	}
}