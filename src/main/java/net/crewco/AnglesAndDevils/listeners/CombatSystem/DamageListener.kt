package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.Glasses.Glassesutils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class DamageListener : Listener {
	@EventHandler
	fun onDamage(e: EntityDamageByEntityEvent) {
		if (e.damager is Player && e.entity is Player) {
			val damager = e.damager as Player
			val target = e.entity as Player
			val glassesutils = Glassesutils()

			val damagerTeam = PStats.getPlayerTeam(damager.uniqueId.toString())
			val targetTeam = PStats.getPlayerTeam(target.uniqueId.toString())

			if (damagerTeam == "Mortals" && targetTeam == "Mortals") {
				e.isCancelled = false // Mortals can hurt each other

				if (!combatLogManager.isInCombat(damager)){
					combatLogManager.logCombatStart(target,damager)
					combatLogManager.logCombatStart(damager,target)
				}else{
					combatLogManager.logCombatStart(target, damager)
					combatLogManager.logCombatStart(damager,target)
				}

			} else if (damagerTeam == "Mortals" && targetTeam == "Angels" || targetTeam == "Devils") {
				e.isCancelled = false // Mediums can hurt Mortals, Angels, and Devils

				if (!combatLogManager.isInCombat(damager)){
					combatLogManager.logCombatStart(target,damager)
					combatLogManager.logCombatStart(damager,target)
				}else{
					combatLogManager.logCombatStart(target, damager)
					combatLogManager.logCombatStart(damager,target)
				}

			} else if (damagerTeam == "Angels" && targetTeam == "Mortals") {
				// Angels can't hurt Mortals without the glasses on
				if (!glassesutils.hasGlassesOn(target)){
					e.isCancelled = true
				}else{
					e.damage += 5 // Angels deal more damage to Mediums and Devils
					if (!combatLogManager.isInCombat(damager)){
						combatLogManager.logCombatStart(target,damager)
						combatLogManager.logCombatStart(damager,target)
					}else{
						combatLogManager.logCombatStart(target, damager)
						combatLogManager.logCombatStart(damager,target)
					}
				}

			} else if (damagerTeam == "Devils" && targetTeam == "Mortals") {
				//Devils can't hurt Mortals without the glasses on
				if (!glassesutils.hasGlassesOn(target)){
					e.isCancelled = true
				}else{
					e.damage += 5 // Angels deal more damage to Mediums and Devils
					if (!combatLogManager.isInCombat(damager)){
						combatLogManager.logCombatStart(target,damager)
						combatLogManager.logCombatStart(damager,target)
					}else{
						combatLogManager.logCombatStart(target, damager)
						combatLogManager.logCombatStart(damager,target)
					}
				}

			} else if (damagerTeam == "Angels" && targetTeam == "Angels" || damagerTeam == "Devils" && targetTeam == "Devils") {
				e.isCancelled = true // Angels can't hurt Angels
			} else if (damagerTeam == "Devils" && targetTeam == "Angels") {
				e.damage += 5 // Devils deal more damage to Mediums and Angels
				if (!combatLogManager.isInCombat(damager)){
					combatLogManager.logCombatStart(target,damager)
					combatLogManager.logCombatStart(damager,target)
				}else{
					combatLogManager.logCombatStart(target, damager)
					combatLogManager.logCombatStart(damager,target)
				}
			} else if (damagerTeam == "Angels" && targetTeam == "Devils") {
				e.damage += 5 // Angels deal more damage to Mediums and Devils
				if (!combatLogManager.isInCombat(damager)){
					combatLogManager.logCombatStart(target,damager)
					combatLogManager.logCombatStart(damager,target)
				}else{
					combatLogManager.logCombatStart(target, damager)
					combatLogManager.logCombatStart(damager,target)
				}
			}
		}
	}
}