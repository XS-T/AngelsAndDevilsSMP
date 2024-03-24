package net.crewco.AnglesAndDevils.listeners.CombatSystem

import net.crewco.AnglesAndDevils.Startup
import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.Startup.Companion.angelTeam
import net.crewco.AnglesAndDevils.Startup.Companion.combatLogManager
import net.crewco.AnglesAndDevils.Startup.Companion.devilTeam
import net.crewco.AnglesAndDevils.Startup.Companion.mediumTeam
import net.crewco.AnglesAndDevils.Startup.Companion.mortalTeam
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityCategory
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.entity.SpawnCategory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class DamageListener: Listener {
	@EventHandler
	fun onDamage(e:EntityDamageByEntityEvent){
		if (e.damager is Player){
			val player = e.damager as Player
			val target = e.entity

			if (target is Player){
				if (angelTeam.containsKey(target.uniqueId) && angelTeam.containsKey(player.uniqueId)){
					e.isCancelled = true
				}else if (devilTeam.containsKey(target.uniqueId) && devilTeam.containsKey(player.uniqueId)){
					e.isCancelled = true
				}else if (!angelTeam.containsKey(target.uniqueId) && !mortalTeam.containsKey(target.uniqueId) && devilTeam.containsKey(target.uniqueId) && PStats.getPlayerTeam(player.uniqueId.toString()) == "Angles"){ // Checks to see if the target is a Devil/Medium
					val damage = e.damage + 5
					e.damage = damage

					// Adds them to the combat log
					if (!combatLogManager.isInCombat(target)){
						combatLogManager.logCombatStart(target,player)
					}else{
						combatLogManager.logCombatStart(target,player)
					}
				}else if (!devilTeam.containsKey(target.uniqueId) && !mortalTeam.containsKey(target.uniqueId) && angelTeam.containsKey(target.uniqueId) && PStats.getPlayerTeam(player.uniqueId.toString()) == "Devils"){ // Checks to see if the target is an Angel/Medium
					val damage = e.damage + 5
					e.damage = damage

					//Adds them to the combat log
					if (!combatLogManager.isInCombat(target)){
						combatLogManager.logCombatStart(target,player)
					}else{
						combatLogManager.logCombatStart(target,player)
					}
				}else if (!devilTeam.containsKey(target.uniqueId) && !angelTeam.containsKey(target.uniqueId) && mortalTeam.containsKey(target.uniqueId) && !mediumTeam.containsKey(target.uniqueId)){ //Checks to see if the player is a Mortal or not
					e.isCancelled = true
				}
			}
		}
	}
}