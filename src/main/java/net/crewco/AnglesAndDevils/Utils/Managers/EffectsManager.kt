package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player

class EffectsManager {
	fun showAngelsPortalEffect(player: Player) {
		player.world.spawnParticle(
			Particle.REDSTONE,
			player.location.add(0.0, 1.0, 0.0),
			100,
			0.1,
			0.1,
			0.1,
			Particle.DustOptions(Color.RED, 1.0f)
		)
	}

	fun showDevilsPortalEffect(player: Player) {
		player.world.spawnParticle(
			Particle.REDSTONE,
			player.location.add(0.0, 1.0, 0.0),
			100,
			0.1,
			0.1,
			0.1,
			Particle.DustOptions(Color.fromRGB(255, 0, 255), 1.0f)
		)
	}
}