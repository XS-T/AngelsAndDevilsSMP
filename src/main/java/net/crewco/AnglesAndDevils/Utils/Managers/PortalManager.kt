package net.crewco.AnglesAndDevils.Utils.Managers

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.dataManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player


class PortalManager(private val angelsWorld: World, private val devilsWorld: World) {

	fun teleportToAngels(player: Player) {
		if (player.world != angelsWorld) {
			dataManager.saveLocation(player.uniqueId.toString(), player.location)
			player.teleport(angelsWorld.spawnLocation)
		} else {
			val overWorld = Bukkit.getWorld("world")!!
			//player.teleport(overWorld.spawnLocation)
			if (dataManager.getLocation(player.uniqueId.toString())?.world != null) {
				if (dataManager.getLocation(player.uniqueId.toString())!!.world == overWorld) {
					val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
					val worldLocation =
						Location(playerLocation.world, playerLocation.x, playerLocation.y, playerLocation.z)
					player.teleport(worldLocation)
				}else{
					player.teleport(overWorld.spawnLocation)
				}
			}
		}
	}

	fun teleportToDevils(player: Player) {
		if (player.world != devilsWorld) {
			dataManager.saveLocation(player.uniqueId.toString(), player.location)
			player.teleport(devilsWorld.spawnLocation)
		} else {
			val overWorld = Bukkit.getWorld("world")!!
			//player.teleport(overWorld.spawnLocation)
			if (dataManager.getLocation(player.uniqueId.toString())?.world != null) {
				if (dataManager.getLocation(player.uniqueId.toString())!!.world == overWorld) {
					val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
					val worldLocation =
						Location(playerLocation.world, playerLocation.x, playerLocation.y, playerLocation.z)
					player.teleport(worldLocation)
				}else{
					player.teleport(overWorld.spawnLocation)
				}
			}
		}
	}

	fun teleportToMortals(player: Player) {
		val mortalWorld = Bukkit.getWorld("world")
		player.teleport(mortalWorld!!.spawnLocation)
	}

	fun teleportTo(player: Player) {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		if (team == "Angels") {
			val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
			val mirrorLocation = Location(angelsWorld, playerLocation.x, playerLocation.y, playerLocation.z)
			player.teleport(mirrorLocation)
		} else if (team == "Devils") {
			val playerLocation = dataManager.getLocation(player.uniqueId.toString())!!
			val mirrorLocation = Location(devilsWorld, playerLocation.x, playerLocation.y, playerLocation.z)
			player.teleport(mirrorLocation)
		}
	}
}