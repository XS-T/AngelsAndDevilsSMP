package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class CombatLogManager(private val plugin: JavaPlugin) {
	private val combatLogs: MutableMap<UUID, Long> = HashMap()
	private val combatDuration: Long = 60000 // Combat duration in milliseconds (e.g., 60 seconds)
	private val taskIds: MutableMap<UUID, Int> = HashMap() // Store task IDs for each player
	private val whoHit: MutableMap<UUID, UUID> = HashMap() // Store player who initiated combat for each player

	fun logCombatStart(player: Player, starter: Player) {
		if (isInCombat(player)) {
			resetCombatLog(player)
		} else {
			combatLogs[player.uniqueId] = System.currentTimeMillis()
			whoHit[player.uniqueId] = starter.uniqueId
			startCombatTask(player)
		}
	}

	fun logCombatEnd(player: Player) {
		combatLogs.remove(player.uniqueId)
		whoHit.remove(player.uniqueId)
		cancelCombatTask(player)
		clearActionBar(player)
	}

	fun isInCombat(player: Player): Boolean {
		return combatLogs.containsKey(player.uniqueId) && System.currentTimeMillis() - combatLogs[player.uniqueId]!! < combatDuration
	}

	fun getCombatDuration(player: Player): String {
		if (isInCombat(player)) {
			val durationMillis = System.currentTimeMillis() - combatLogs[player.uniqueId]!!
			val seconds = durationMillis / 1000 % 60
			val minutes = durationMillis / (1000 * 60) % 60
			val hours = durationMillis / (1000 * 60 * 60)

			return if (hours > 0) {
				String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds)
			} else if (minutes > 0) {
				String.format("%d minutes, %d seconds", minutes, seconds)
			} else {
				String.format("%d seconds", seconds)
			}
		}
		return "Not in combat" // If the player is not in combat
	}

	fun getWhoHit(player: Player): Player? {
		val starterId = whoHit[player.uniqueId]
		return Bukkit.getPlayer(starterId!!)
	}

	private fun startCombatTask(player: Player) {
		val taskId = object : BukkitRunnable() {
			override fun run() {
				if (!isInCombat(player)) {
					//player.sendMessage("${ChatColor.RED}Your combat log has expired.")
					sendActionBar(player,"${ChatColor.RED}Your combat log has expired.")
					cancel()
					cancelCombatTask(player)
				} else {
					updateActionBar(player)
				}
			}
		}.runTaskTimer(plugin, 0L, 20L) // Check every second

		taskIds[player.uniqueId] = taskId.taskId
	}

	private fun cancelCombatTask(player: Player) {
		val taskId = taskIds[player.uniqueId]
		if (taskId != null) {
			Bukkit.getScheduler().cancelTask(taskId)
			taskIds.remove(player.uniqueId)
		}
	}

	private fun resetCombatLog(player: Player) {
		combatLogs[player.uniqueId] = System.currentTimeMillis()
		val taskId = taskIds[player.uniqueId]
		if (taskId != null) {
			Bukkit.getScheduler().cancelTask(taskId)
		}
		startCombatTask(player)
	}

	private fun updateActionBar(player: Player) {
		val remainingTime = (combatLogs[player.uniqueId]!! + combatDuration - System.currentTimeMillis()) / 1000
		val formattedTime = formatTime(remainingTime)
		sendActionBar(player, ChatColor.GREEN.toString() + "Combat Log: " + formattedTime)
	}

	private fun formatTime(seconds: Long): String {
		val minutes = seconds / 60
		val remainingSeconds = seconds % 60
		return String.format("%02d:%02d", minutes, remainingSeconds)
	}

	private fun sendActionBar(player: Player, message: String) {
		player.sendActionBar(message)
	}

	private fun clearActionBar(player: Player) {
		player.sendActionBar("")
	}
}