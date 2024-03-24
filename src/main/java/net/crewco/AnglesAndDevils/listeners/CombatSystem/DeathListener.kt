package net.crewco.AnglesAndDevils.listeners.CombatSystem

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathListener: Listener {
	@EventHandler
	fun onDeath(e:PlayerDeathEvent){
		val player = e.entity
		val killer = e.player

		player.sendMessage("You dided to ${killer.displayName}")
		player.sendMessage("You Killed ${player.displayName}")
	}
}