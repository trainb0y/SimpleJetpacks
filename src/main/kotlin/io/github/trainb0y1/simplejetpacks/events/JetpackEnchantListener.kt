package io.github.trainb0y1.simplejetpacks.events

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import org.bukkit.event.inventory.PrepareAnvilEvent

class JetpackEnchantListener : Listener {
    /*
    Prevents a jetpack from being enchanted with Unbreaking or Mending
     */
    @EventHandler
    fun jetpackTableEnchant(event: PrepareItemEnchantEvent) {
        // VERY new to kotlin, this is probably a mess
        val itemMeta = event.item.itemMeta
        if (!SimpleJetpacks.isJetpack(itemMeta)) {
            return
        }
        // We need to deny mending and unbreaking from the enchantment table
        // Having them doesn't break the plugin, but
        // neither do anything when applied to a jetpack
        val blockedEnchants = listOf(
            Enchantment.MENDING,
            Enchantment.DURABILITY
        )
        for (i in 0..2) {
            val offer = event.offers[i]
            if (offer != null) { // ignore this, it is sometimes null. check doc on event.offers
                for (enchant in blockedEnchants) {
                    if (offer.enchantment == enchant) {
                        //SimpleJetpacks.getPlugin().logger.warning("Blocked $enchant")
                        event.offers[i] = null //.setEnchantment(null)
                        // AGAIN, IGNORE THIS
                    }
                }
            }
        }
    }

    @EventHandler
    fun jetpackAnvilEnchant(event: PrepareAnvilEvent) {
        val itemMeta = event.inventory.firstItem?.itemMeta
        if (itemMeta == null || !SimpleJetpacks.isJetpack(itemMeta)) {
            return
        }
        val blockedEnchants = listOf(
            Enchantment.MENDING,
            Enchantment.DURABILITY
        )
        if (event.result == null) {
            return
        }
        //SimpleJetpacks.getPlugin().logger.warning("Checking enchants")
        for (enchant in blockedEnchants) {
            if (enchant in event.result!!.enchantments.keys) {
                //SimpleJetpacks.getPlugin().logger.warning("Blocked $enchant")
                event.result = null
            }
        }
    }
}