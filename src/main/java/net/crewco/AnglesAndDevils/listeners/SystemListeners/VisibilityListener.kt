package net.crewco.AnglesAndDevils.listeners.SystemListeners

import com.google.inject.Inject
import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses.Glassesutils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class VisibilityListener @Inject constructor(private val plugin: Startup) : Listener {

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		val player = event.player
		val glassesUtils = Glassesutils()

		val targetTeam = PStats.getPlayerTeam(player.uniqueId.toString())
		val glasses = C_Items().glasses()

		for (onlinePlayer in Bukkit.getOnlinePlayers()) {
			val onlineTeam = PStats.getPlayerTeam(onlinePlayer.uniqueId.toString())
			if (targetTeam == "Mortals"){
				player.showPlayer(plugin,player)
				if (glassesUtils.hasGlassesOn(player)){
					if (onlineTeam == "Angels" || onlineTeam == "Devils"){
						player.showPlayer(plugin,onlinePlayer)
					}
				}else{
					if (onlineTeam == "Angels" || onlineTeam == "Devils"){
						player.hidePlayer(plugin,onlinePlayer)
					}
				}

			}else if (targetTeam == "Angels" || targetTeam == "Devils"){
				player.hidePlayer(plugin,player)

				if (onlineTeam == "Mortals"){
					onlinePlayer.hidePlayer(plugin,player)
					if (glassesUtils.hasGlassesOn(onlinePlayer)){
						player.showPlayer(plugin,onlinePlayer)
						onlinePlayer.showPlayer(plugin,player)
					}
				}
			}
		}
	}
}