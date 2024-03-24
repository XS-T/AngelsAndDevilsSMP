package net.crewco.AnglesAndDevils

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Utils.Managers.CombatLogManager
import net.crewco.AnglesAndDevils.Utils.Managers.CooldownManager
import net.crewco.AnglesAndDevils.Utils.Managers.DataManager
import net.crewco.AnglesAndDevils.Utils.Managers.EffectsManager
import net.crewco.AnglesAndDevils.Utils.Managers.PlayerStatsManager
import net.crewco.AnglesAndDevils.Utils.Managers.PortalManager
import net.crewco.AnglesAndDevils.Utils.Managers.WorldManager
import net.crewco.AnglesAndDevils.listeners.CombatSystem.ComBatLogPenalty
import net.crewco.AnglesAndDevils.listeners.CombatSystem.DamageListener
import net.crewco.AnglesAndDevils.listeners.CombatSystem.DeathListener
import net.crewco.AnglesAndDevils.listeners.SystemListeners.AssignTeams
import net.crewco.AnglesAndDevils.listeners.SystemListeners.InventoryCanceler
import net.crewco.AnglesAndDevils.listeners.SystemListeners.MobSpawnPreventionListener
import net.crewco.AnglesAndDevils.listeners.SystemListeners.SendResourcePack
import net.crewco.AnglesAndDevils.listeners.SystemListeners.VisibilityListener
import net.crewco.common.CrewCoPlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.UUID

class Startup : CrewCoPlugin() {
	companion object{
		lateinit var plugin:Startup
			private set
		lateinit var PStats: PlayerStatsManager
		lateinit var systemStr: String
		lateinit var customItems:C_Items
		lateinit var cooldownManager: CooldownManager
		lateinit var worldManager: WorldManager
		lateinit var portalManager: PortalManager
		lateinit var effectsManager: EffectsManager
		lateinit var dataManager: DataManager
		lateinit var combatLogManager: CombatLogManager
		lateinit var angelTeam:MutableMap<UUID,String>
		lateinit var devilTeam:MutableMap<UUID,String>
		lateinit var mediumTeam:MutableMap<UUID,String>
		lateinit var mortalTeam:MutableMap<UUID,String>
	}
	override suspend fun onEnableAsync() {
		super.onEnableAsync()

		//Inits
		plugin = this
		angelTeam = mutableMapOf()
		devilTeam = mutableMapOf()
		mediumTeam = mutableMapOf()
		mortalTeam = mutableMapOf()


		//Config
		plugin.config.options().copyDefaults()
		plugin.saveDefaultConfig()
		systemStr = "${ChatColor.GRAY}[${ChatColor.BLUE} System ${ChatColor.GRAY}]"

		//Events

		//System Events
		registerListeners(
			SendResourcePack::class,
			AssignTeams::class, InventoryCanceler::class, VisibilityListener::class, MobSpawnPreventionListener::class)

		//System Utils
		customItems = C_Items()
		cooldownManager = CooldownManager(this)
		worldManager = WorldManager(this)
		effectsManager = EffectsManager()
		dataManager = DataManager(this)
		combatLogManager = CombatLogManager(this)

		//System on Start Events

		//Create the Worlds
		worldManager.createAngelsWorld()
		worldManager.createDevilsWorld()

		//Initialize PortalManager here
		val angelsWorld = Bukkit.getWorld("angels_world")!!
		val devilsWorld = Bukkit.getWorld("devils_world")!!
		portalManager = PortalManager(angelsWorld,devilsWorld)

		// Combat System
		registerListeners(ComBatLogPenalty::class,DamageListener::class,DeathListener::class)



		//MySQL
		PStats = PlayerStatsManager(this)

	}

	override suspend fun onDisableAsync() {
		super.onDisableAsync()
	}
}