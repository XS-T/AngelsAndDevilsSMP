package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class CooldownManager(private val plugin: JavaPlugin) {
	private val cooldowns: MutableMap<UUID, Long> = HashMap()

	fun setCooldown(player: Player, seconds: Int) {
		cooldowns[player.uniqueId] = System.currentTimeMillis() + (seconds * 1000L)
	}

	fun hasCooldown(player: Player): Boolean {
		return cooldowns.containsKey(player.uniqueId) && cooldowns[player.uniqueId]!! > System.currentTimeMillis()
	}

	fun getRemainingCooldown(player: Player): String {
		if (hasCooldown(player)) {
			val remainingMillis = cooldowns[player.uniqueId]!! - System.currentTimeMillis()
			val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60
			val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60
			val hours = TimeUnit.MILLISECONDS.toHours(remainingMillis)

			return if (hours > 0) {
				String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds)
			} else if (minutes > 0) {
				String.format("%d minutes, %d seconds", minutes, seconds)
			} else {
				String.format("%d seconds", seconds)
			}
		}
		return "0 seconds" // No cooldown
	}


	fun hasEnded(player: Player): Boolean {
		val cooldown = getRemainingCooldown(player)
		return cooldown.contains("0")
	}

	fun removeCooldown(player: Player) {
		cooldowns.remove(player.uniqueId)
	}
}