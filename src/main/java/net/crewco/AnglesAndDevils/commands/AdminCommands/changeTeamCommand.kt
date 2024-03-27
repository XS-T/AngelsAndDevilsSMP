package net.crewco.AnglesAndDevils.commands.AdminCommands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import com.google.inject.Inject
import net.crewco.AnglesAndDevils.CustomItems.C_Items
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.angelTeam
import net.crewco.AnglesAndDevils.Startup.Companion.potionEffectsManager
import net.crewco.AnglesAndDevils.Startup.Companion.defaultChat
import net.crewco.AnglesAndDevils.Startup.Companion.devilTeam
import net.crewco.AnglesAndDevils.Startup.Companion.mortalTeam
import net.crewco.AnglesAndDevils.Startup.Companion.portalManager
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses.Glassesutils
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Halo
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Horns
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class changeTeamCommand @Inject constructor(private val plugin: Startup) {
	@CommandMethod("changeTeam <target> <team>")
	@CommandDescription("Template Command")
	@CommandPermission("aag.system.admin.commands.changeteam")
	fun changeTeam(player: Player, @Argument("target") target: Player, @Argument("team") targetTeam: String) {
		val team = PStats.getPlayerTeam(target.uniqueId.toString()) ?: return

		if (team == targetTeam) {
			player.sendMessage("$systemStr You cannot change this player's team to the same one, please pick another one")
			return
		}

		if (targetTeam !in listOf("Devils", "Angels", "Mortals")) {
			player.sendMessage("$systemStr The team $targetTeam is not a valid team")
			return
		}

		Bukkit.getScheduler().runTask(plugin, Runnable {
			// Your code to run synchronously in the main Bukkit thread

			if (potionEffectsManager.hasPotionEffects(target)){
				potionEffectsManager.removePotionEffects(target)
			}

			PStats.resetPlayerStats(target, team)
			PStats.changeTeam(target, targetTeam)

			if (targetTeam == "Angels" || targetTeam == "Devils") {
				if (Halo().isWearingHalo(target)){
					target.inventory.helmet = C_Items().devil_horns()
					defaultChat.replace(target.uniqueId,"Devil")
					potionEffectsManager.applyPotionEffects(target)
					portalManager.teleportToDevils(target)

					if (angelTeam.containsKey(target.uniqueId)){
						angelTeam.remove(target.uniqueId)
						devilTeam[target.uniqueId] = "Devils"
					}

				}else if (Horns().isWearingHorns(target)){
					target.inventory.helmet = C_Items().halo_item()
					defaultChat.replace(target.uniqueId,"Angel")
					potionEffectsManager.applyPotionEffects(target)
					portalManager.teleportToAngels(target)

					if (devilTeam.containsKey(target.uniqueId)){
						devilTeam.remove(target.uniqueId)
						angelTeam[target.uniqueId] = "Angels"
					}

				}else if (targetTeam == "Angels"){
					target.inventory.helmet = C_Items().halo_item()
					defaultChat.replace(target.uniqueId,"Angel")
					potionEffectsManager.applyPotionEffects(target)
					portalManager.teleportToAngels(target)

					if (mortalTeam.containsKey(target.uniqueId)){
						mortalTeam.remove(target.uniqueId)
						angelTeam[target.uniqueId] = "Angels"
					}

				}else{
					target.inventory.helmet = C_Items().devil_horns()
					defaultChat.replace(target.uniqueId,"Devil")
					potionEffectsManager.applyPotionEffects(target)
					portalManager.teleportToDevils(target)

					if(mortalTeam.containsKey(target.uniqueId)){
						mortalTeam.remove(target.uniqueId)
						devilTeam[target.uniqueId] = "Devils"
					}

				}
				Bukkit.getOnlinePlayers().filter { onlinePlayer ->
					val onlineTeams = PStats.getPlayerTeam(onlinePlayer.uniqueId.toString())
					onlineTeams == "Mortals"
				}.forEach { mortalPlayer ->
					if (Glassesutils().hasGlassesOn(mortalPlayer)){
						mortalPlayer.showPlayer(plugin, target)
					}else{
						mortalPlayer.hidePlayer(plugin, target)
					}
				}
			} else {
				if (Halo().isWearingHalo(target) || Horns().isWearingHorns(target)){
					target.inventory.helmet = ItemStack(Material.AIR)
					defaultChat.replace(target.uniqueId,"Mortal")
					potionEffectsManager.removePotionEffects(target)
					portalManager.teleportToMortals(target)

					if (angelTeam.containsKey(target.uniqueId)){
						angelTeam.remove(target.uniqueId)
					}else if(devilTeam.containsKey(target.uniqueId)){
						devilTeam.remove(target.uniqueId)
					}

					mortalTeam[target.uniqueId] = "Mortals"


				}
				Bukkit.getOnlinePlayers().forEach { onlinePlayer ->
					onlinePlayer.showPlayer(plugin, target)
				}
			}

			target.sendMessage("$systemStr Your team has been changed to $targetTeam")
		})
	}
}