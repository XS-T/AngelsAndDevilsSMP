package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class clronDeath: Listener {
	@EventHandler
	fun onDeath(e:PlayerDeathEvent){
		val player = e.player
		if (combatLogManager.isInCombat(player)){
			combatLogManager.logCombatEnd(player)
		}
	}
}