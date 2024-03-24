package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.plugin.java.JavaPlugin

class WorldManager(private val plugin: JavaPlugin) {
	fun createAngelsWorld() {
		val worldName = "angels_world"
		if (Bukkit.getWorld(worldName) != null) {
			return // Angels world already exists
		}
		val worldCreator = WorldCreator(worldName)
		worldCreator.type(WorldType.FLAT)
		worldCreator.environment(World.Environment.THE_END)
		// Set world properties (e.g., environment, generator)
		worldCreator.createWorld()?.setSpawnLocation(0, 100, 0)
	}

	fun createDevilsWorld() {
		val worldName = "devils_world"
		if (Bukkit.getWorld(worldName) != null) {
			return // Devils world already exists
		}
		val worldCreator = WorldCreator(worldName)
		worldCreator.type(WorldType.FLAT)
		worldCreator.environment(World.Environment.NETHER)
		// Set world properties (e.g., environment, generator)
		worldCreator.createWorld()?.setSpawnLocation(0, 100, 0)
	}
}