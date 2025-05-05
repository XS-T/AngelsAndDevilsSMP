package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player

class EffectsManager {
	fun showAngelsPortalEffect(player: Player) {
		player.world.spawnParticle(
			Particle.POOF,
			player.location.add(0.0, 1.0, 0.0),
			100,
			0.1,
			0.1,
			0.1
		)
	}

	fun showDevilsPortalEffect(player: Player) {
		player.world.spawnParticle(
			Particle.FLAME,
			player.location.add(0.0, 1.0, 0.0),
			100,
			0.1,
			0.1,
			0.1
		)
	}
}