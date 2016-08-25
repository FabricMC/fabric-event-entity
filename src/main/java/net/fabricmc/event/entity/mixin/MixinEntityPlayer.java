/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.event.entity.mixin;

import net.fabricmc.base.Fabric;
import net.fabricmc.event.entity.PlayerArmorTickEvent;
import net.fabricmc.event.entity.PlayerTrySleepEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class MixinEntityPlayer extends EntityLiving {

	@Shadow private InventoryPlayer inventory;

	public MixinEntityPlayer(World a1) {
		super(a1);
	}

	@Inject(method = "trySleep(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/player/EntityPlayer$SleepResult;", at = @At("HEAD"), cancellable = true)
	public void onTrySleep(BlockPos pos, CallbackInfoReturnable<EntityPlayer.SleepResult> ci) {
		PlayerTrySleepEvent event = new PlayerTrySleepEvent((EntityPlayer)(Object)this, pos);
		Fabric.getEventBus().publish(event);
		EntityPlayer.SleepResult result = event.getResult();
		if (result != null) {
			ci.setReturnValue(result);
		}
	}

	@Inject(method = "update()V", at = @At("HEAD"))
	public void onUpdate(CallbackInfo ci) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() == EquipmentSlot.Type.ARMOR) {
				ItemStack stack = inventory.getArmorStack(slot.h);
				if (stack != ItemStack.NULL_STACK) {
					PlayerArmorTickEvent event = new PlayerArmorTickEvent((EntityPlayer)(Object)this, stack, slot);
					Fabric.getEventBus().publish(event);
				}
			}
		}
	}

}
