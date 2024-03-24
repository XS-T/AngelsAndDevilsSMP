package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class PortalManager(private val angelsWorld: World, private val devilsWorld: World) {

	fun teleportToAngels(player: Player) {
		teleportPlayer(player, angelsWorld.spawnLocation)
	}

	fun teleportToDevils(player: Player) {
		teleportPlayer(player, devilsWorld.spawnLocation)
	}

	private fun teleportPlayer(player: Player, location: Location) {
		player.teleport(location)
	}
}