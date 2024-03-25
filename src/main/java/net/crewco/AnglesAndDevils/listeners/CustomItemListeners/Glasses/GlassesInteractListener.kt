package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses

import com.google.inject.Inject
import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class GlassesInteractListener @Inject constructor(private val plugin: Startup): Listener {
	@EventHandler
	fun onInteract(e: PlayerInteractEvent) {
		val player = e.player
		val item = e.item
		val glassesutils = Glassesutils()
		if (item != null) {
			try {
				if (e.action == Action.RIGHT_CLICK_AIR && item.itemMeta.customModelData == 4 || e.action == Action.RIGHT_CLICK_BLOCK && item.itemMeta.customModelData == 4) {
					val helmet = player.inventory.helmet
					if (helmet != null && !glassesutils.hasGlassesOn(player)) {
						player.sendMessage("$systemStr You have to remove your helmet before equipping these")
					} else {
						val customItems = C_Items()
						player.inventory.helmet = customItems.glasses()
						player.inventory.removeItem(player.itemInHand)

						player.sendMessage("$systemStr You can now see Angels and Devils")

						for (angles_and_Devils in Bukkit.getOnlinePlayers()) {
							if (angles_and_Devils != player) {
								if (PStats.getPlayerTeam(angles_and_Devils.uniqueId.toString()) == "Angels" || PStats.getPlayerTeam(
										angles_and_Devils.uniqueId.toString()
									) == "Devils"
								) {
									player.showPlayer(plugin, angles_and_Devils)
								}
							} else {
								continue
							}
						}
					}
				}
			}catch (_:IllegalStateException){

			}
		}
	}
}