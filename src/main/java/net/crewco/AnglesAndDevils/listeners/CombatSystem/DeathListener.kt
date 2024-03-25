package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathListener : Listener {

	@EventHandler
	fun onPlayerDeath(event: PlayerDeathEvent) {
		val victim = event.entity
		val deathMessage = event.deathMessage ?: return

		if (" was slain by " in deathMessage) {
			val parts = deathMessage.split(" was slain by ")
			if (parts.size > 1) {
				val killerName = parts[1].trim()
				// You can now use the killer's name to perform actions or store data
				// For example, you could update statistics or send a message to the killer
				val killer = Bukkit.getPlayer(killerName)
				if (killer is Player){
					if (killer.isOnline){
						PStats.updatePlayerKills(killer.uniqueId.toString(),1)
						killer.sendMessage("$systemStr +1 kill")
						combatLogManager.logCombatEnd(killer)
						combatLogManager.logCombatEnd(victim)
						val killerTeam = PStats.getPlayerTeam(killer.uniqueId.toString())
						val victumTeam = PStats.getPlayerTeam(victim.uniqueId.toString())

						if(killerTeam == "Angels" && victumTeam == "Devils"){
							PStats.updatePlayerEnlightenment(victim.uniqueId.toString(),1)
							victim.sendMessage("$systemStr You have gained ${PStats.getEnlightenment(victim.uniqueId.toString())}/100 Enlightenment")
							val enlightenment = PStats.getEnlightenment(victim.uniqueId.toString())
							if (enlightenment == 10){
								victim.sendMessage("$systemStr ${ChatColor.RED} Warning, if your Enlightenment gets too high you will be eliminated")
							}
						}

						if (killerTeam == "Devils" && victumTeam == "Angels"){
							PStats.updatePlayerCorruption(victim.uniqueId.toString(),1)
							victim.sendMessage("$systemStr You have gained ${PStats.getCorruption(victim.uniqueId.toString())}/100 Corruption")

							val corruption = PStats.getCorruption(victim.uniqueId.toString())
							if (corruption == 10){
								victim.sendMessage("$systemStr ${ChatColor.RED} Warning, if your Corruption gets too high you will be eliminated")
							}
						}

						if (killerTeam == "Mortals" && victumTeam == "Angels"){
							if (PStats.getCorruption(killer.uniqueId.toString()) >= 50){
								victim.sendMessage("$systemStr You have gained ${PStats.getCorruption(victim.uniqueId.toString())}/100 Corruption")
								PStats.updatePlayerCorruption(victim.uniqueId.toString(),1)
								killer.sendMessage("$systemStr You have gained ${PStats.getCorruption(killer.uniqueId.toString())}/100 Corruption")

							}
						}

						if (killerTeam == "Mortals" && victumTeam == "Devils"){
							if (PStats.getEnlightenment(killer.uniqueId.toString()) >= 50){
								victim.sendMessage("$systemStr You have gained ${PStats.getEnlightenment(victim.uniqueId.toString())}/100 Enlightenment")
								PStats.updatePlayerEnlightenment(victim.uniqueId.toString(),1)
								killer.sendMessage("$systemStr You have gained ${PStats.getCorruption(killer.uniqueId.toString())}/100 Enlightenment")
							}
						}
						if (killerTeam == "Angels" && victumTeam == "Mortals"){
							if (PStats.getCorruption(victim.uniqueId.toString()) != 0){
								PStats.updatePlayerCorruption(victim.uniqueId.toString(),1)
								PStats.subtractCorruption(killer.uniqueId.toString(),1)
								victim.sendMessage("$systemStr You have lost -1 Corruption")
								killer.sendMessage("$systemStr You have lost -1 Corruption")
							}
						}

						if (killerTeam == "Devils" && victumTeam == "Mortals"){
							if (PStats.getEnlightenment(victim.uniqueId.toString()) != 0){
								PStats.subtractEnlightenment(victim.uniqueId.toString(),1)
								PStats.subtractEnlightenment(killer.uniqueId.toString(),1)
								victim.sendMessage("$systemStr You have lost -1 Enlightenment")
								killer.sendMessage("$systemStr You have lost -1 Enlightenment")
							}
						}
					}
				}
			}
		} else {
			// Death message doesn't contain information about the killer
		}
	}
}