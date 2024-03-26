package net.crewco.AnglesAndDevils.listeners.SystemListeners

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.angelTeam
import net.crewco.AnglesAndDevils.Startup.Companion.devilTeam
import net.crewco.AnglesAndDevils.Startup.Companion.mortalTeam
import net.crewco.AnglesAndDevils.Startup.Companion.plugin
import net.crewco.AnglesAndDevils.Startup.Companion.potionEffectHandler
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import java.io.File

class AssignTeams : Listener {
	private val customItems = C_Items()

	@EventHandler
	fun onJoin(e: PlayerJoinEvent) {
		val player = e.player

		// List
		val players = mutableListOf<Player>()
		val items = mutableListOf<ItemStack>()

		items.add(customItems.halo_item())
		items.add(customItems.devil_horns())
		// items.add(customItems.halo_horns())
		items.add(ItemStack(Material.AIR))

		if (!player.hasPlayedBefore()) {
			players.add(player)
			if (items.isEmpty()) {
				return
			}

			val randomItems = items.random()

			if (randomItems.itemMeta != null && randomItems.itemMeta.customModelData == 3 && randomItems.type == Material.GOLDEN_HOE) {
				player.inventory.helmet = randomItems
				player.sendMessage("$systemStr You have been assigned to the ${ChatColor.GOLD}Angles Team")
				PStats.addPlayerStats(player.uniqueId.toString(), "Angles")
				angelTeam[player.uniqueId] = "Angels"
			} else if (randomItems.itemMeta != null && randomItems.itemMeta.customModelData == 1 && randomItems.type == Material.GOLDEN_HOE) {
				player.inventory.helmet = randomItems
				player.sendMessage("$systemStr You have been assigned to the ${ChatColor.DARK_RED}Devil's Team")
				PStats.addPlayerStats(player.uniqueId.toString(), "Devils")
				devilTeam[player.uniqueId] = "Devils"
			} else {
				player.sendMessage("You have Assigned to the mortals team")
				PStats.addPlayerStats(player.uniqueId.toString(), "Mortals")
				mortalTeam[player.uniqueId] = "Mortals"
			}


		} else {
			val playerTeam = PStats.getPlayerTeam(player.uniqueId.toString())
			when (playerTeam) {
				"Angels" -> {
					angelTeam[player.uniqueId] = "Angels"
					if (player.inventory.helmet != customItems.halo_item()){
						player.inventory.helmet = customItems.halo_item()
					}
					if (!potionEffectHandler.hasPotionEffects(player)){
						potionEffectHandler.addPotionEffect(player)
					}
				}
				"Devils" -> {
					devilTeam[player.uniqueId] = "Devils"
					if (player.inventory.helmet != customItems.devil_horns()){
						player.inventory.helmet = customItems.devil_horns()
					}
					if (!potionEffectHandler.hasPotionEffects(player)){
						potionEffectHandler.addPotionEffect(player)
					}
				}
				"Mortals" -> {
					mortalTeam[player.uniqueId] = "Mortals"
				}
			}
		}
		//player.saveData()
	}
}