package net.crewco.AnglesAndDevils.commands.SystemCommands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.defaultChat
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.entity.Player

class changeChatCommand {
	@CommandMethod("chat <arg>")
	@CommandDescription("Template Command")
	@CommandPermission("aag.command.chat.change")
	fun onexecute(player: Player,@Argument("arg") arg:String) {
		val getChat = defaultChat[player.uniqueId]
		if (getChat != "global"){
			defaultChat.replace(player.uniqueId,"global")
			player.sendMessage("$systemStr Your Chat has been changed to Global")
		}

		if (defaultChat.getValue(player.uniqueId) == "global"){
			val team =  PStats.getPlayerTeam(player.uniqueId.toString())
			when (team) {
				"Angels" -> {
					defaultChat.replace(player.uniqueId,"Angel")
					player.sendMessage("$systemStr Your Chat has been changed to Angel")
				}
				"Devils" -> {
					defaultChat.replace(player.uniqueId,"Devil")
					player.sendMessage("$systemStr Your Chat has been changed to Devil")
				}
				"Mortals" -> {
					defaultChat.replace(player.uniqueId,"Mortal")
					player.sendMessage("$systemStr Your Chat has been changed to Mortal")
				}
			}
		}
	}
}