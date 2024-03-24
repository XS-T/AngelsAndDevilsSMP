package net.crewco.AnglesAndDevils.CustomItems

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

class C_Items {

	// Devil Horns
	fun devil_horns():ItemStack{
		val devilHorns = ItemStack(Material.GOLDEN_HOE)
		val devilHornsMeta = devilHorns.itemMeta

		//Meta
		devilHornsMeta.setDisplayName("${ChatColor.DARK_RED} Devil's Horns")

		//Setting custom model data
		devilHornsMeta.setCustomModelData(1)


		// Special Meta
		devilHornsMeta.addEnchant(Enchantment.BINDING_CURSE,1,true)
		devilHornsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeModifier(UUID.randomUUID(),"+20 Armor", 20.0,AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD))
		devilHornsMeta.itemFlags.add(ItemFlag.HIDE_ENCHANTS)
		devilHornsMeta.itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)

		devilHorns.itemMeta = devilHornsMeta

		return devilHorns
	}


	// Halo
	fun halo_item(): ItemStack{
		val halo_item = ItemStack(Material.GOLDEN_HOE)
		val haloItemMeta = halo_item.itemMeta

		haloItemMeta.setDisplayName("${ChatColor.GOLD} Angle's Halo")

		//Setting custom model data
		haloItemMeta.setCustomModelData(3)

		haloItemMeta.addEnchant(Enchantment.BINDING_CURSE,1,true)
		haloItemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeModifier(UUID.randomUUID(),"+20 Armor", 20.0,AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD))
		haloItemMeta.itemFlags.add(ItemFlag.HIDE_ENCHANTS)
		haloItemMeta.itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)

		halo_item.itemMeta = haloItemMeta
		return halo_item
	}

	//Mixed
	fun halo_horns(): ItemStack{
		val halo_horns = ItemStack(Material.GOLDEN_HOE)
		val haloHornsMeta = halo_horns.itemMeta

		//Setting custom model data
		haloHornsMeta.setCustomModelData(2)

		haloHornsMeta.addEnchant(Enchantment.BINDING_CURSE,1,true)
		haloHornsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeModifier(UUID.randomUUID(),"+40 Armor", 40.0,AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD))
		haloHornsMeta.itemFlags.add(ItemFlag.HIDE_ENCHANTS)
		haloHornsMeta.itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)

		halo_horns.itemMeta = haloHornsMeta
		return halo_horns
	}

	fun glasses():ItemStack{
		val glasses = ItemStack(Material.GOLDEN_HOE)
		val glassesMeta = glasses.itemMeta

		glassesMeta.setDisplayName("${ChatColor.BLUE} Glasses of the Other Side")

		//Setting custom model data
		glassesMeta.setCustomModelData(4)

		glassesMeta.addEnchant(Enchantment.MENDING,1,true)
		glassesMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeModifier(UUID.randomUUID(),"+6 Armor", 40.0,AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD))
		glassesMeta.itemFlags.add(ItemFlag.HIDE_ENCHANTS)
		glassesMeta.itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)

		glasses.itemMeta = glassesMeta
		return glasses
	}
}