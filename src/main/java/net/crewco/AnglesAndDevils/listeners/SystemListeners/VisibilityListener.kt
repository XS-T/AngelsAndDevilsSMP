package net.crewco.AnglesAndDevils.listeners.SystemListeners

import com.google.inject.Inject
import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class VisibilityListener @Inject constructor(private val plugin: Startup) : Listener {
	@EventHandler
	fun onSee(e:PlayerJoinEvent){
		val player = e.player

		//Hides the Angles and Devils from the Mortal Players
		if (PStats.getPlayerTeam(player.uniqueId.toString()) == "Mortals"){
			for(deitys in Bukkit.getOnlinePlayers()){
				if(deitys == player){
					continue
				}else{
					if (PStats.getPlayerTeam(deitys.uniqueId.toString()) == "Devils" || PStats.getPlayerTeam(deitys.uniqueId.toString()) == "Angles"){
						player.hidePlayer(plugin,deitys)
					}
				}
			}
		}else{
			//Does the same thing but for a angle or devil is joining
			if (PStats.getPlayerTeam(player.uniqueId.toString()) != "Mortals")
			for (mortal in Bukkit.getOnlinePlayers()){
				if (mortal == player){
					continue
				}else{
					if (PStats.getPlayerTeam(mortal.uniqueId.toString()) == "Mortals"){
						player.hidePlayer(plugin,player)
					}
				}
			}
		}
	}
}