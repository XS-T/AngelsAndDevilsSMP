package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

class WorldManager(private val plugin: JavaPlugin) {
	fun createAngelsWorld() {
		val worldName = "angels_world"
		if (Bukkit.getWorld(worldName) != null) {
			return // Angels world already exists
		}

		val worldCreator = WorldCreator(worldName)
		worldCreator.type(WorldType.AMPLIFIED) // Use NORMAL type for a normal world
		worldCreator.environment(World.Environment.NORMAL)
		worldCreator.generateStructures(false)

		val world: World? = worldCreator.createWorld()
		world?.setSpawnFlags(false,false)
		world?.setSpawnLocation(0, 85, 0)
	}


	fun createDevilsWorld() {
		val worldName = "devils_world"
		if (Bukkit.getWorld(worldName) != null) {
			return // Devils world already exists
		}

		val worldCreator = WorldCreator(worldName)
		worldCreator.type(WorldType.AMPLIFIED) // Use NORMAL type for a normal world
		worldCreator.environment(World.Environment.NETHER)
		worldCreator.generateStructures(false)



		val world: World? = worldCreator.createWorld()
		world?.setSpawnFlags(false,false)
		world?.setSpawnLocation(0, 85, 0)
	}
}