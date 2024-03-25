package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class ComBatLogPenalty: Listener {
	@EventHandler
	fun onLeave(e:PlayerQuitEvent){
		val player = e.player
		if (combatLogManager.isInCombat(player)){
			val starter = combatLogManager.getWhoHit(player) as Player
			player.health = 0.0
			PStats.updatePlayerKills(starter.uniqueId.toString(),1)
			combatLogManager.logCombatEnd(player)
			combatLogManager.logCombatEnd(starter)
			starter.sendMessage("$systemStr You now have ${PStats.getPlayerKills(starter.uniqueId.toString())} Kill")
		}
	}
}