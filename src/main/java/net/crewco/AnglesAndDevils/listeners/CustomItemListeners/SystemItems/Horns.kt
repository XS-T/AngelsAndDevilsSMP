package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Horns {

	private val customItems = C_Items()
	private val horns = customItems.devil_horns()

	fun isWearingHorns(player: Player): Boolean {
		val inventory = player.inventory
		try {
			return inventory.helmet!!.itemMeta.customModelData == 1  && horns.itemMeta.customModelData == 1
		}catch (_:NullPointerException){

		}
		return false
	}

	fun getHorns(): ItemStack {
		return customItems.devil_horns()
	}

}