package net.crewco.AnglesAndDevils.Utils.ChatSystem

import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.angelTeam
import net.crewco.AnglesAndDevils.Startup.Companion.defaultChat
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent

class ChatListener:Listener {
	@EventHandler
	fun onChat(e:PlayerChatEvent){
		val player = e.player
		val chat = defaultChat[player.uniqueId]
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		val message = e.message

		if (team == "Angels" && chat == "Angel"){
			val newMessage = ChatColor.translateAlternateColorCodes('&',"&f[&6Angels&f] ${player.displayName} $message")
			for (teamMate in Bukkit.getOnlinePlayers()){
				if (teamMate == player){
					e.isCancelled = true
					player.sendMessage(newMessage)
					continue
				}else{
					if (PStats.getPlayerTeam(teamMate.uniqueId.toString()) == "Angels"){
						teamMate.sendMessage("${ChatColor.GOLD}Angels ${ChatColor.WHITE} $message")
					}
				}
			}
		}else if (team == "Devils" && chat == "Devil"){
			val newMessage = ChatColor.translateAlternateColorCodes('&',"&f[&4Devils&f] ${player.displayName} $message")
			for (teamMate in Bukkit.getOnlinePlayers()){
				if (teamMate == player){
					e.isCancelled = true
					player.sendMessage(newMessage)
					continue
				}else{
					if (PStats.getPlayerTeam(teamMate.uniqueId.toString()) == "Devils"){
						teamMate.sendMessage("${ChatColor.DARK_RED}Devils ${ChatColor.WHITE} $message")
					}
				}
			}
		}else if (team == "Mortals" && chat == "Mortal"){
			val newMessage = ChatColor.translateAlternateColorCodes('&',"&f[&7Mortals&f] ${player.displayName} $message")
			for (teamMate in Bukkit.getOnlinePlayers()){
				if (teamMate == player){
					e.isCancelled = true
					player.sendMessage(newMessage)
					continue
				}else{
					if (PStats.getPlayerTeam(teamMate.uniqueId.toString()) == "Mortals"){
						teamMate.sendMessage("${ChatColor.DARK_RED}Mortals ${ChatColor.WHITE} $message")
					}
				}
			}
		}else if (chat == "global"){
			val newMessage = ChatColor.translateAlternateColorCodes('&',"&f[&aGlobal&f] ${player.displayName} $message")
			for (players in Bukkit.getOnlinePlayers()){
				if (defaultChat[players.uniqueId] == "global"){
					if (players == player){
						e.isCancelled = true
						player.sendMessage(newMessage)
					}else{
						players.sendMessage(newMessage)
					}
				}
			}
		}
	}
}