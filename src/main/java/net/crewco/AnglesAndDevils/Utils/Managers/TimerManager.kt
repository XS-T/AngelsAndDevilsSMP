package net.crewco.AnglesAndDevils.Utils.Managers


import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit

class TimerManager(private val plugin: JavaPlugin) {
	private val timers: MutableMap<UUID, BukkitRunnable> = HashMap()

	fun startTimer(player: Player, durationSeconds: Int, action: () -> Unit) {
		val key = player.uniqueId
		timers[key]?.cancel() // Cancel existing timer for the player if any

		val timer = object : BukkitRunnable() {
			private var remainingTime = durationSeconds

			override fun run() {
				remainingTime--
				if (remainingTime <= 0) {
					action.invoke()
					cancel()
					timers.remove(key) // Remove the timer after execution
				} else {
					updateActionBar(player, remainingTime)
				}
			}
		}

		timer.runTaskTimer(plugin, 0L, 20L) // Update every second (20 ticks)
		timers[key] = timer
	}

	fun endTimer(player: Player) {
		timers[player.uniqueId]?.cancel()
		timers.remove(player.uniqueId)
	}

	private fun updateActionBar(player: Player, remainingTime: Int) {
		val hours = remainingTime / 3600
		val minutes = (remainingTime % 3600) / 60
		val seconds = remainingTime % 60

		val formattedTime = if (hours > 0) {
			String.format("%d:%02d:%02d", hours, minutes, seconds)
		} else {
			String.format("%d:%02d", minutes, seconds)
		}

		val actionBarMessage = "Time remaining: $formattedTime"
		player.sendActionBar(actionBarMessage)
	}
}