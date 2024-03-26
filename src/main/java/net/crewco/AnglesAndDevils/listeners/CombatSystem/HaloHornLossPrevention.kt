package net.crewco.AnglesAndDevils.listeners.CombatSystem

import com.google.inject.Inject
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class HaloHornLossPrevention @Inject constructor(private val plugin: Startup) : Listener {

	@EventHandler
	fun onDeath(e: PlayerDeathEvent) {
		val player = e.entity
		val drops = e.drops
		val helper = DeathHelper()


		try {
			for (item in drops) {
				if (helper.isCustomItem(item)) {
					e.itemsToKeep.add(item)
					e.drops.remove(item)
				}

				if (helper.isHalo(item)) {
					e.itemsToKeep.add(item)
					e.drops.remove(item)
				}

				if (helper.isGlasses(item)) {
					for (onlinePlayer in Bukkit.getOnlinePlayers()) {
						if (onlinePlayer != player &&
							(PStats.getPlayerTeam(onlinePlayer.uniqueId.toString()) == "Angels" ||
									PStats.getPlayerTeam(onlinePlayer.uniqueId.toString()) == "Devils")
						) {
							player.hidePlayer(plugin, onlinePlayer)
						}
					}
					player.sendMessage("$systemStr You can no longer see angels and Devils")
				}
			}
		}catch (_:ConcurrentModificationException){}
	}
}