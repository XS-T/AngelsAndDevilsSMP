package net.crewco.AnglesAndDevils.commands.AdminCommands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import org.bukkit.entity.Player

class resetPlayerStats {
	@CommandMethod("resetPlayer <target>")
	@CommandDescription("Get the Custom Items")
	@CommandPermission("aag.system.admin.commands.reset-stats")
	fun onExecute(player:Player, @Argument("target") target:Player){
		val team = PStats.getPlayerTeam(target.uniqueId.toString())!!
		PStats.resetPlayerStats(target,team)
	}
}