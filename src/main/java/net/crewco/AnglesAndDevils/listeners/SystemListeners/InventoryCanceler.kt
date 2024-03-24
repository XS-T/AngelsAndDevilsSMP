package net.crewco.AnglesAndDevils.listeners.SystemListeners

import net.crewco.AnglesAndDevils.Startup.Companion.customItems
import net.crewco.AnglesAndDevils.Startup.Companion.systemStr
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryCanceler:Listener {
	@EventHandler
	fun onCancelClick(e:InventoryClickEvent){
		val player = e.whoClicked as Player
		val inventory = e.clickedInventory

		//Prevents the Player from taking off the Halo/Horns or the Mixed one
		if (player.inventory == inventory){
			if (player.inventory.contains(customItems.halo_item()) && player.equipment.helmet == customItems.halo_item() || player.inventory.contains(
					customItems.devil_horns()) && player.equipment.helmet == customItems.devil_horns()){
				e.isCancelled = true
				player.sendMessage("$systemStr You can not take this off")
			}
		}
	}
}