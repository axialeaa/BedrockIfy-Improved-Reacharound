package me.juancarloscp52.bedrockify.client.features.quickArmorSwap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

public class ArmorReplacer {

    public static TypedActionResult<ItemStack> tryChangeArmor(PlayerEntity playerEntity, Hand hand){
        ItemStack newArmor = playerEntity.getStackInHand(hand);
        if (newArmor.getItem() == Items.ELYTRA || newArmor.getItem() instanceof ArmorItem){
            ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
            if(interactionManager != null && MinecraftClient.getInstance().mouse.wasRightButtonClicked()){
                EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(newArmor);

                if(!playerEntity.getEquippedStack(equipmentSlot).getItem().equals(Items.AIR)){
                    int slotIndex = 8-equipmentSlot.getEntitySlotId();
                    interactionManager.clickSlot(playerEntity.playerScreenHandler.syncId,slotIndex,playerEntity.inventory.main.indexOf(newArmor), SlotActionType.SWAP,playerEntity);
                    playerEntity.playSound(newArmor.getItem() == Items.ELYTRA ? SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA : ((ArmorItem)newArmor.getItem()).getMaterial().getEquipSound(), 1.0F,1.0F);
                    return TypedActionResult.success(playerEntity.getStackInHand(hand));
                }
            }
        }
        return TypedActionResult.pass(newArmor);
    }

}
