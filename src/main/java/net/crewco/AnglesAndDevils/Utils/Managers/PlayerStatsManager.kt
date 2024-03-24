package net.crewco.AnglesAndDevils.Utils.Managers
import net.crewco.AnglesAndDevils.Startup
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class PlayerStatsManager(private val plugin: Startup) {
	private var connection: Connection? = null

	init {
		connect()
		createTable()
	}

	private fun connect() {
		try {
			Class.forName("org.sqlite.JDBC")
			connection = DriverManager.getConnection("jdbc:sqlite:${plugin.dataFolder.path}/player_stats.db")
		} catch (e: ClassNotFoundException) {
			e.printStackTrace()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	private fun disconnect() {
		try {
			connection?.close()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	private fun createTable() {
		val query = """
            CREATE TABLE IF NOT EXISTS player_stats (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid TEXT NOT NULL,
                team TEXT NOT NULL,
                kills INTEGER NOT NULL DEFAULT 0,
                deaths INTEGER NOT NULL DEFAULT 0,
                level INTEGER NOT NULL DEFAULT 1,
                UNIQUE(uuid)
            );
        """.trimIndent()

		try {
			val statement = connection?.createStatement()
			statement?.executeUpdate(query)
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun addPlayerStats(uuid: String, team: String) {
		val query = "INSERT INTO player_stats (uuid, team) VALUES (?, ?)"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setString(1, uuid)
			statement?.setString(2, team)
			statement?.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun updatePlayerKills(uuid: String, kills: Int) {
		val query = "UPDATE player_stats SET kills = ? WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setInt(1, kills)
			statement?.setString(2, uuid)
			statement?.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun updatePlayerLevel(uuid: String, level: Int) {
		val query = "UPDATE player_stats SET level = ? WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setInt(1, level)
			statement?.setString(2, uuid)
			statement?.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun getPlayerKills(uuid: String): Int {
		val query = "SELECT kills FROM player_stats WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setString(1, uuid)
			val resultSet = statement?.executeQuery()
			if (resultSet != null && resultSet.next()) {
				return resultSet.getInt("kills")
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
		return 0
	}

	fun getPlayerLevel(uuid: String): Int {
		val query = "SELECT level FROM player_stats WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setString(1, uuid)
			val resultSet = statement?.executeQuery()
			if (resultSet != null && resultSet.next()) {
				return resultSet.getInt("level")
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
		return 1
	}

	fun getPlayerTeam(uuid: String): String? {
		val query = "SELECT team FROM player_stats WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setString(1, uuid)
			val resultSet = statement?.executeQuery()
			if (resultSet != null && resultSet.next()) {
				return resultSet.getString("team")
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
		return null
	}

	fun resetPlayerStats(player: Player, team: String) {
		val query = "UPDATE player_stats SET kills = ?, deaths = ?, level = ?, team = ? WHERE uuid = ?"

		try {
			val statement = connection!!.prepareStatement(query)
			statement.setInt(1, 0) // Reset kills to 0
			statement.setInt(2, 0) // Reset deaths to 0
			statement.setInt(3, 1) // Reset level to 1
			statement.setString(4, team) // Set the team
			statement.setString(5, player.uniqueId.toString())
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun changeTeam(player: Player, newTeam: String) {
		val query = "UPDATE player_stats SET team = ? WHERE uuid = ?"

		try {
			val statement = connection!!.prepareStatement(query)
			statement.setString(1, newTeam)
			statement.setString(2, player.uniqueId.toString())
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun incrementDeaths(player: Player) {
		val query = "UPDATE player_stats SET deaths = deaths + 1 WHERE uuid = ?"

		try {
			val statement = connection!!.prepareStatement(query)
			statement.setString(1, player.uniqueId.toString())
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun getPlayerDeaths(uuid: String): Int {
		val query = "SELECT deaths FROM player_stats WHERE uuid = ?"

		try {
			val statement = connection?.prepareStatement(query)
			statement?.setString(1, uuid)
			val resultSet = statement?.executeQuery()
			if (resultSet != null && resultSet.next()) {
				return resultSet.getInt("deaths")
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
		return 0
	}

	fun close() {
		try {
			connection?.close()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}
}

