package net.crewco.AnglesAndDevils.Utils.Managers

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class DataManager(private val plugin: JavaPlugin) {
	private val dataFile: File = File(plugin.dataFolder, "data.yml")
	private val dataConfig: YamlConfiguration = YamlConfiguration.loadConfiguration(dataFile)

	fun saveLocation(key: String, location: Location) {
		val path = "locations.$key"
		dataConfig.set("$path.world", location.world?.name)
		dataConfig.set("$path.x", location.x)
		dataConfig.set("$path.y", location.y)
		dataConfig.set("$path.z", location.z)
		dataConfig.set("$path.yaw", location.yaw)
		dataConfig.set("$path.pitch", location.pitch)

		saveDataConfig()
	}

	fun saveCustomData(key: String, customData: Any) {
		val path = "custom_data.$key"
		dataConfig.set(path, customData)

		saveDataConfig()
	}

	fun getLocation(key: String): Location? {
		val path = "locations.$key"
		val worldName = dataConfig.getString("$path.world") ?: return null
		val x = dataConfig.getDouble("$path.x")
		val y = dataConfig.getDouble("$path.y")
		val z = dataConfig.getDouble("$path.z")
		val yaw = dataConfig.getDouble("$path.yaw").toFloat()
		val pitch = dataConfig.getDouble("$path.pitch").toFloat()

		return Location(plugin.server.getWorld(worldName), x, y, z, yaw, pitch)
	}

	fun getCustomData(key: String): Any? {
		val path = "custom_data.$key"
		return dataConfig.get(path)
	}

	private fun saveDataConfig() {
		try {
			dataConfig.save(dataFile)
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
}
