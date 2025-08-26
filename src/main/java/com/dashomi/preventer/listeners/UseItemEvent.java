package com.dashomi.preventer.listeners;

import com.dashomi.preventer.PreventerClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import net.minecraft.world.World;

import static com.dashomi.preventer.PreventerClient.config;

public class UseItemEvent {
	public static ActionResult useItemListener(PlayerEntity player, World world, Hand hand) {
		if (!PreventerClient.overrideKeyPressed) {
			Item handItem = player.getStackInHand(hand).getItem();
			if (config.preventRenamedItemUsing) {
				if (handItem.getUseAction(player.getStackInHand(hand)) == UseAction.EAT || handItem.getUseAction(player.getStackInHand(hand)) == UseAction.DRINK) {
					if (player.getStackInHand(hand).getCustomName() != null) {
						if (config.preventRenamedItemUsing_msg) {
							player.sendMessage(Text.translatable("config.preventer.preventRenamedItemUsing.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
				
			}
			
		}
		
		if(config.debugmode) System.out.println("use itemevent listener fired with Pass");
		
		return ActionResult.PASS;
	}

}
