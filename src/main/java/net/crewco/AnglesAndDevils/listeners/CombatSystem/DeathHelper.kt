package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.CustomItems.C_Items
import org.bukkit.inventory.ItemStack

class DeathHelper {
	val customItems = C_Items()
	fun isCustomItem(droppedItem:ItemStack): Boolean {
		if (customItems.AngelPortalItem().itemMeta.displayName == droppedItem.itemMeta.displayName){
			return true
		}else if (customItems.DevilPortalItem().itemMeta.displayName == droppedItem.itemMeta.displayName){
			return true
		}
		return false
	}

	fun isHalo(droppedItem: ItemStack): Boolean {
		if (customItems.devil_horns().itemMeta.displayName == droppedItem.itemMeta.displayName) {
			return true
		} else if (customItems.halo_item().itemMeta.displayName == droppedItem.itemMeta.displayName) {
			return true
		}
		return false
	}
	fun isGlasses(droppedItem: ItemStack): Boolean {
		return customItems.glasses().itemMeta.displayName == droppedItem.itemMeta.displayName
	}
}