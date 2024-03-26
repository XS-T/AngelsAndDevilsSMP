package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.PortalItem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.Startup.Companion.cooldownManager
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
						if (combatLogManager.isInCombat(player)){
							player.sendMessage("$systemStr You are not allowed to use this item while in combat")
						}else{
							if (!cooldownManager.hasCooldown(player)) {
								cooldownManager.setCooldown(player, 5)
								effectsManager.showAngelsPortalEffect(player)
								portalManager.teleportToAngels(player)
							}else{
								if (cooldownManager.hasEnded(player)){
									cooldownManager.removeCooldown(player)
								}else{
									player.sendMessage("$systemStr You can not use this right now you have ${cooldownManager.getRemainingCooldown(player)}")

								}
							}
						}
					}else{
						player.sendMessage("$systemStr You are not allowed to use this item")
					}
				}else if (e.action == Action.RIGHT_CLICK_AIR && item.itemMeta.customModelData == 6 || e.action == Action.RIGHT_CLICK_BLOCK && item.itemMeta.customModelData == 6) {
					val portalItem = player.inventory.itemInMainHand
					if (item == portalItem && team == "Devils"){
						if (combatLogManager.isInCombat(player)){
							player.sendMessage("$systemStr you can not use this while in combat")
						}else{
							if (!cooldownManager.hasCooldown(player)) {
								cooldownManager.setCooldown(player, 5)
								effectsManager.showDevilsPortalEffect(player)
								portalManager.teleportToDevils(player)
							}else{
								if (cooldownManager.hasEnded(player)){
									cooldownManager.removeCooldown(player)
								}else{
									player.sendMessage("$systemStr You can not use this right now you have ${cooldownManager.getRemainingCooldown(player)}")

								}
							}
						}
					}else{
						player.sendMessage("$systemStr You are not allowed to use this item")
					}
				}
			}catch (_:IllegalStateException){

			}
		}
	}
}