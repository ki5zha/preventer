package com.dashomi.preventer.listeners;

import com.dashomi.preventer.PreventerClient;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Objects;

import static com.dashomi.preventer.PreventerClient.config;

public class AttackBlockEvent {
	
	public static ActionResult attackBlockListener(PlayerEntity playerEntity, World world, Hand hand, BlockPos pos, Direction direction) {
		Block targetBlock = world.getBlockState(pos).getBlock();
		String targetblockName = targetBlock.getName().toString().toLowerCase();
		if (!PreventerClient.overrideKeyPressed) {
			if(config.debugmode) System.out.println(targetblockName + " attempting to break with override off");
			if (config.onlyMatureCropHarvest) {
				if (targetBlock instanceof CropBlock) {
					if (!((CropBlock) targetBlock).isMature(world.getBlockState(pos))) {
						if (config.onlyMatureCropHarvest_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.onlyMatureCropHarvest.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
				if (targetBlock instanceof NetherWartBlock) {
					if (world.getBlockState(pos).get(NetherWartBlock.AGE) < 3) {
						if (config.onlyMatureCropHarvest_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.onlyMatureCropHarvest.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventBuddingAmethystBreaking) {
				if (targetBlock instanceof BuddingAmethystBlock) {
					if (config.preventBuddingAmethystBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventBuddingAmethystBreaking.text"), true);
					}
					return ActionResult.FAIL;
				}
			}
			
			if (config.preventStemBreaking) {
				if (targetBlock instanceof AttachedStemBlock || targetBlock instanceof StemBlock) {
					if (config.preventStemBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventStemBreaking.text"), true);
					}
					return ActionResult.FAIL;
				}
			}
			
			if (config.preventGlassBreaking) {
				if (targetBlock instanceof TransparentBlock || targetBlock instanceof PaneBlock) {
					if (!Objects.equals(targetBlock.getTranslationKey(), "block.minecraft.iron_bars")) {
						if (config.preventGlassBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventGlassBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventSuspiciousBlockBreaking) {
				if (targetBlock instanceof BrushableBlock) {
					if (config.preventSuspiciousBlockBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventSuspiciousBlockBreaking.text"), true);
					}
					return ActionResult.FAIL;
				} else if (config.enhancedSuspiciousBlockBreakingPrevention) {
					if (world.getBlockState(pos.offset(Direction.Axis.Y, 1)).getBlock() instanceof BrushableBlock) {
						if (config.preventSuspiciousBlockBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.enhancedSuspiciousBlockBreakingPrevention.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventEnderChestBreaking) {
				if (targetBlock instanceof EnderChestBlock) {
					if (playerEntity.getMainHandStack().getItem() instanceof PickaxeItem) {
						if (!playerEntity.getMainHandStack().getEnchantments().getEnchantmentEntries().toString().contains("minecraft:silk_touch")) {
							if (config.preventEnderChestBreaking_msg) {
								playerEntity.sendMessage(Text.translatable("config.preventer.preventEnderChestBreaking.text"), true);
							}
							return ActionResult.FAIL;
						}
					}
				}
			}
			
			if (config.preventSpawnerBreaking) {
				if (targetBlock instanceof SpawnerBlock) {
					if (config.preventSpawnerBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventSpawnerBreaking.text"), true);
					}
					return ActionResult.FAIL;
				}
			}
			
			if (config.preventSugarCaneBreaking) {
				if (targetBlock instanceof SugarCaneBlock || targetBlock instanceof BambooBlock || targetBlock instanceof CactusBlock) {
					Block block = world.getBlockState(pos.offset(Direction.Axis.Y, -1)).getBlock();
					if (!(block instanceof SugarCaneBlock) && !(block instanceof BambooBlock) && !(block instanceof CactusBlock)) {
						if (config.preventSugarCaneBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventSugarCaneBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventChestBreaking) {
				if (targetBlock instanceof ChestBlock) {
					if (playerEntity.getMainHandStack().getItem() instanceof AxeItem) {
						if (config.preventChestBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventChestBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventCarpetBreaking) {
				if (targetBlock instanceof CarpetBlock) {
					if (config.preventCarpetBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventCarpetBreaking.text"), true);
					}
					return ActionResult.FAIL;
				}
			}
			
			//if (checkDurabilityProtection(playerEntity, hand)) return ActionResult.FAIL;
			
			//additional features
			if (config.preventSlimeBlockBreaking) {
				if (targetBlock instanceof SlimeBlock) {
					if (config.preventSlimeBlockBreaking_msg) {
						playerEntity.sendMessage(Text.translatable("config.preventer.preventSlimeBlockBreaking.text"), true);
					}
					return ActionResult.FAIL;
					
				}
			}
			if (config.preventHoneyBlockBreaking) {
				if (targetBlock instanceof HoneyBlock) {
					playerEntity.sendMessage(Text.translatable("config.preventer.preventHoneyBlockBreaking.text"), true);
				return ActionResult.FAIL;
				}
			}
			if (config.preventdiamondOreBreaking) {
				if (targetblockName.contains("diamond") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventdiamondOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventdiamondOreBreaking.text"), true);
						}
							return ActionResult.FAIL;
					}
				}
			}
			if (config.preventemeraldOreBreaking) {
				if (targetblockName.contains("emerald") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventemeraldOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventemeraldOreBreaking.text"), true);
						}
							return ActionResult.FAIL;
					}
				}
			}
			if (config.preventredstoneOreBreaking) {
				if (targetblockName.contains("redstone") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventredstoneOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventredstoneOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if (config.preventlapisOreBreaking) {
				if (targetblockName.contains("lapis") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventlapisOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventlapisOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if (config.preventquartzOreBreaking) {
				if (targetblockName.contains("quartz") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventquartzOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventquartzOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if (config.preventgoldOreBreaking) {
				if ((targetblockName.contains("gold") || targetblockName.contains("gilded")) && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventgoldOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventgoldOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if (config.preventironOreBreaking) {
				if (targetblockName.contains("iron") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventironOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventironOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if (config.preventcopperOreBreaking) {
				if (targetblockName.contains("copper") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventcopperOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventcopperOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if (config.preventcoalOreBreaking) {
				if (targetblockName.contains("coal") && targetblockName.contains("ore")) {
					if (!playerEntity.getMainHandStack().getEnchantments().toString().toLowerCase().contains("silk")) {
						if (config.preventcoalOreBreaking_msg) {
							playerEntity.sendMessage(Text.translatable("config.preventer.preventcoalOreBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			if(config.preventJarBreaking){
				if(targetblockName.contains("jar")){
					String itemname = playerEntity.getMainHandStack().getItem().getName().toString().toLowerCase();
					if((itemname.contains("axe")
							|| itemname.contains("pickaxe")
				|| itemname.contains("shovel")
							|| itemname.contains("hoe"))) {
						if(config.preventJarBreaking_msg){
							playerEntity.sendMessage(Text.translatable("config.preventer.preventJarBreaking.text"), true);
						}
						return ActionResult.FAIL;
					}
				}
			}
			
			if(config.debugmode) System.out.println("end of method line 251");
			
		}
		if(config.debugmode) System.out.println("end of method line 255");
		return ActionResult.PASS;
	}
}