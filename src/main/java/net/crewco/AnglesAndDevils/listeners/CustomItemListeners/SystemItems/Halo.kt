package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Halo {
	private val customItems = C_Items()
	private val halo = customItems.halo_item()

	fun isWearingHalo(player:Player): Boolean {
		val inventory = player.inventory
		try {
			return inventory.helmet!!.itemMeta.customModelData == 3  && halo.itemMeta.customModelData == 3
		}catch (_:NullPointerException){

		}
		return false
	}

	fun getHalo():ItemStack{
		return customItems.halo_item()
	}
}