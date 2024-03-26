package net.crewco.AnglesAndDevils.Utils

import net.crewco.AnglesAndDevils.Startup.Companion.PStats
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Halo
import net.crewco.AnglesAndDevils.listeners.CustomItemListeners.SystemItems.Horns
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration

class potionEffectHandler {

	private val angelPotionEffects = mutableListOf<PotionEffectType>()
	private val devilPotionEffects = mutableListOf<PotionEffectType>()

	fun intilize(){
		//Angel Effects
		angelPotionEffects.add(PotionEffectType.ABSORPTION)
		angelPotionEffects.add(PotionEffectType.DOLPHINS_GRACE)
		angelPotionEffects.add(PotionEffectType.JUMP)
		angelPotionEffects.add(PotionEffectType.REGENERATION)
		angelPotionEffects.add(PotionEffectType.HERO_OF_THE_VILLAGE)
		angelPotionEffects.add(PotionEffectType.DAMAGE_RESISTANCE)
		angelPotionEffects.add(PotionEffectType.INCREASE_DAMAGE)

		//Devil Effects
		devilPotionEffects.add(PotionEffectType.ABSORPTION)
		devilPotionEffects.add(PotionEffectType.REGENERATION)
		devilPotionEffects.add(PotionEffectType.BAD_OMEN)
		devilPotionEffects.add(PotionEffectType.FAST_DIGGING)
		devilPotionEffects.add(PotionEffectType.JUMP)
		devilPotionEffects.add(PotionEffectType.DAMAGE_RESISTANCE)
		devilPotionEffects.add(PotionEffectType.INCREASE_DAMAGE)
		devilPotionEffects.add(PotionEffectType.FIRE_RESISTANCE)
	}

	fun addPotionEffect(player: Player) {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		val halo = Halo()
		val horns = Horns()
		if (team == "Angels" && halo.isWearingHalo(player)){
			angelPotionEffects.forEachIndexed { _, potionEffectType ->
				player.addPotionEffect(PotionEffect(potionEffectType,999999999,0,true,false))
			}

		}else if(team == "Devils" && horns.isWearingHorns(player)){
			devilPotionEffects.forEachIndexed { _, potionEffectType ->
				player.addPotionEffect(PotionEffect(potionEffectType,999999999,0,true,false))
			}
		}
	}

	fun hasPotionEffects(player: Player): Boolean {
		val team = PStats.getPlayerTeam(player.uniqueId.toString())
		if (team == "Angels"){
			angelPotionEffects.forEachIndexed { _, potionEffectType ->
				val hasEffects = player.hasPotionEffect(potionEffectType)
				if (hasEffects){
					return true
				}
			}
		}else if (team == "Devils"){
			devilPotionEffects.forEachIndexed { _, potionEffectType ->
				val hasEffects = player.hasPotionEffect(potionEffectType)
				if (hasEffects){
					return true
				}
			}
		}
		return  false
	}

}