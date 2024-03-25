package net.crewco.AnglesAndDevils.listeners.SystemListeners

import com.google.inject.Inject
import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses.Glassesutils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryCanceler @Inject constructor(private val plugin: Startup) :Listener {
	@EventHandler
	fun onCancelClick(e: InventoryClickEvent) {
		val player = e.whoClicked as Player
		val slot = e.slot
		val item = e.currentItem
		val glassesutils = Glassesutils()
		val customItems = C_Items()
		val glasses = customItems.glasses()

		//Prevents the Player from taking off the Halo/Horns or the Mixed one
		if (player.inventory == e.clickedInventory) {
			if (item == customItems.halo_item() || item == customItems.devil_horns()) {
				e.isCancelled = true
				player.sendMessage("$systemStr You can not take this off")
			} else if (glassesutils.hasGlassesOn(player)) {
				if (combatLogManager.isInCombat(player)) {
					e.isCancelled = true
					player.sendMessage("$systemStr You are in Combat and cannot take these off")
				}else{
					if (slot == 39) {
						e.isCancelled = false
						player.sendMessage("$systemStr You can no longer see angels and Devils")

						for (angel_and_devil in Bukkit.getOnlinePlayers()) {
							if (angel_and_devil != player) {
								if (PStats.getPlayerTeam(angel_and_devil.uniqueId.toString()) == "Angels" || PStats.getPlayerTeam(
										angel_and_devil.uniqueId.toString()
									) == "Devils"
								) {
									player.hidePlayer(plugin, angel_and_devil)
								}
							} else {
								continue
							}
						}
					}
				}
			}
		}
	}
}