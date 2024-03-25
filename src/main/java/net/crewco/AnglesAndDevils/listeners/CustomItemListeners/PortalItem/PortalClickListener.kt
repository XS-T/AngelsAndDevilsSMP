package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.PortalItem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.dataManager
import net.crewco.AnglesAndDevils.Startup.Companion.effectsManager
import net.crewco.AnglesAndDevils.Startup.Companion.portalManager
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PortalClickListener:Listener {
	@EventHandler
	fun onPortal(e:PlayerInteractEvent) {
		val player = e.player
		val item = e.item
		val team = PStats.getPlayerTeam(player.uniqueId.toString())

		if (item != null) {
			try {
				if (e.action == Action.RIGHT_CLICK_AIR && item.itemMeta.customModelData == 5 || e.action == Action.RIGHT_CLICK_BLOCK && item.itemMeta.customModelData == 5) {
					val portalItem = player.inventory.itemInMainHand
					if (item == portalItem && team == "Angels"){
						effectsManager.showAngelsPortalEffect(player)
						portalManager.teleportToAngels(player)
					}else{
						player.sendMessage("$systemStr You are not allowed to use this item")
					}

				}else if (e.action == Action.RIGHT_CLICK_AIR && item.itemMeta.customModelData == 6 || e.action == Action.RIGHT_CLICK_BLOCK && item.itemMeta.customModelData == 6) {
					val portalItem = player.inventory.itemInMainHand
					if (item == portalItem && team == "Devils"){
						effectsManager.showDevilsPortalEffect(player)
						portalManager.teleportToDevils(player)
					}else{
						player.sendMessage("$systemStr You are not allowed to use this item")
					}
				}
			}catch (_:IllegalStateException){

			}
		}
	}
}