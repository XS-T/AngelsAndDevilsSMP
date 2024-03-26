package net.crewco.AnglesAndDevils.Utils.Managers

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.dataManager
import org.bukkit.Bukkit
import org.bukkit.Location
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

	fun teleportTo(player:Player){
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		if (team == "Angels"){
			val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
			val mirrorLocation = Location(angelsWorld,playerLocation.x,playerLocation.y,playerLocation.z)
			player.teleport(mirrorLocation)
		}else if (team == "Devils"){
			val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
			val mirrorLocation = Location(devilsWorld,playerLocation.x,playerLocation.y,playerLocation.z)
			player.teleport(mirrorLocation)
		}
	}
}