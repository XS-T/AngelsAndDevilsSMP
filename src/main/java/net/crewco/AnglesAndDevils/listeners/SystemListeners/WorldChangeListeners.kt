package net.crewco.AnglesAndDevils.listeners.SystemListeners

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

class WorldChangeListeners:Listener {
	@EventHandler
	fun onWorldChange(e:PlayerChangedWorldEvent){
		val player = e.player
		val world = e.from
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		if (world.name == "angels_world" && team == "Angels"){
			//world.strikeLightning(world.spawnLocation)
			player.allowFlight = false
			player.isFlying = false
		}else if (world.name == "devils_world" && team == "Devils"){
			//world.strikeLightning(world.spawnLocation)
			player.allowFlight = false
			player.isFlying = false
		}else if (world.name == "world" && team == "Angels" || team == "Devils"){
			player.isInvulnerable = true
			//world.strikeLightning(world.spawnLocation)
			player.isInvulnerable = false
		}
	}
}