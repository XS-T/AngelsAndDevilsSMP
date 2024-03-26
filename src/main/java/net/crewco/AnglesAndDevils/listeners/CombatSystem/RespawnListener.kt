package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.potionEffectHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class RespawnListener: Listener {
	@EventHandler
	fun onRespawn(e:PlayerRespawnEvent){
		val player = e.player
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		if (team == "Angels"){
			if (!potionEffectHandler.hasPotionEffects(player)){
				potionEffectHandler.addPotionEffect(player)
			}
		}else if (team == "Devils"){
			if (!potionEffectHandler.hasPotionEffects(player)){
				potionEffectHandler.addPotionEffect(player)
			}
		}
	}
}