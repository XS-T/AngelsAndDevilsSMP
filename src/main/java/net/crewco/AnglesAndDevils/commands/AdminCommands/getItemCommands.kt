package net.crewco.AnglesAndDevils.commands.AdminCommands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.crewco.AnglesAndDevils.CustomItems.C_Items
import org.bukkit.entity.Player

class getItemCommands {
	@CommandMethod("getItem <item>")
	@CommandDescription("Get the Custom Items")
	@CommandPermission("adp.command.getItem.use")
	fun trigger(player: Player,@Argument("item") item:String) {
		val customItems = C_Items()
		when (item) {
			"halo" -> {
				player.inventory.addItem(customItems.halo_item())
			}
			"horns" -> {
				player.inventory.addItem(customItems.devil_horns())
			}
			"glasses" -> {
				player.inventory.addItem(customItems.glasses())
			}
			"APortal" -> {
				player.inventory.addItem(customItems.AngelPortalItem())
			}
			"DPortal" -> {
				player.inventory.addItem(customItems.DevilPortalItem())
			}
		}
	}
}