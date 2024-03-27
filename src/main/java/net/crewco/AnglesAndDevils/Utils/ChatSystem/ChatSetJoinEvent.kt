package net.crewco.AnglesAndDevils.Utils.ChatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.defaultChat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class ChatSetJoinEvent:Listener {
	@EventHandler
	fun onChatSet(e:PlayerJoinEvent){
		val player = e.player
		val team = PStats.getPlayerTeam(player.uniqueId.toString())

		when (team) {
			"Angels" -> {
				defaultChat[player.uniqueId] = "Angel"
			}
			"Devils" -> {
				defaultChat[player.uniqueId] = "Devil"
			}
			"Mortals" -> {
				defaultChat[player.uniqueId] = "Mortal"
			}
		}

	}
}