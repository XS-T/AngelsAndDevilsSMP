package net.crewco.AnglesAndDevils.Utils.Managers

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Halo
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Horns
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PotionEffectsManager {

	private val angelPotionEffects = setOf(
		PotionEffectType.ABSORPTION,
		PotionEffectType.DOLPHINS_GRACE,
		PotionEffectType.JUMP,
		PotionEffectType.REGENERATION,
		PotionEffectType.HERO_OF_THE_VILLAGE,
		PotionEffectType.DAMAGE_RESISTANCE,
		PotionEffectType.INCREASE_DAMAGE
	)

	private val devilPotionEffects = setOf(
		PotionEffectType.ABSORPTION,
		PotionEffectType.REGENERATION,
		PotionEffectType.BAD_OMEN,
		PotionEffectType.FAST_DIGGING,
		PotionEffectType.JUMP,
		PotionEffectType.DAMAGE_RESISTANCE,
		PotionEffectType.INCREASE_DAMAGE,
		PotionEffectType.FIRE_RESISTANCE
	)

	fun applyPotionEffects(player: Player) {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		val halo = Halo()
		val horns = Horns()

		val effectsToAdd = when (team) {
			"Angels" -> if (halo.isWearingHalo(player)) angelPotionEffects else emptySet()
			"Devils" -> if (horns.isWearingHorns(player)) devilPotionEffects else emptySet()
			else -> emptySet()
		}

		effectsToAdd.forEach { effect ->
			player.addPotionEffect(PotionEffect(effect, Int.MAX_VALUE, 0, true, false))
		}
	}

	fun removePotionEffects(player: Player) {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		val effectsToRemove = when (team) {
			"Angels" -> angelPotionEffects
			"Devils" -> devilPotionEffects
			else -> emptySet()
		}

		effectsToRemove.forEach { effect ->
			player.removePotionEffect(effect)
		}
	}

	fun hasPotionEffects(player: Player): Boolean {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		val activeEffects = player.activePotionEffects.map { it.type }

		return when (team) {
			"Angels" -> activeEffects.any { it in angelPotionEffects }
			"Devils" -> activeEffects.any { it in devilPotionEffects }
			else -> false
		}
	}
}