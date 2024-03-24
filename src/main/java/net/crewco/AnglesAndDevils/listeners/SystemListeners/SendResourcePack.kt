package net.crewco.AnglesAndDevils.listeners.SystemListeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class SendResourcePack: Listener {
	@EventHandler
	fun onJoin(e:PlayerJoinEvent){
		e.player.setResourcePack("https://resource.crewco.org/resource-pack/Angles&Devils.zip")
	}
}