package net.crewco.AnglesAndDevils.listeners.SystemListeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class SendResourcePack: Listener {
	@EventHandler
	fun onJoin(e:PlayerJoinEvent){
		e.player.setResourcePack("https://www.mediafire.com/file/e4fv0deir9xt727/Angels%2526Devils.zip/file")
	}
}