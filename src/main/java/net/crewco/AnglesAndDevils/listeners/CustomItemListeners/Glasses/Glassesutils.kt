package net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import org.bukkit.entity.Player

class Glassesutils {
	private val customItems = C_Items()
	private val glasses = customItems.glasses()
	fun hasGlassesOn(player:Player): Boolean {
		val inventory = player.inventory
		try {
			return inventory.helmet!!.itemMeta.customModelData == 4 && glasses.itemMeta.customModelData == 4
		}catch (_:NullPointerException){
		}
		return false
	}
}