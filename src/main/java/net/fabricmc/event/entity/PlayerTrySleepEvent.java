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

package net.fabricmc.event.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PlayerTrySleepEvent extends PlayerEvent {

	/**
	 * The result of the sleep check. Leave {@code null} for Vanilla behavior.
	 */
	private EntityPlayer.SleepResult result;

	/**
	 * The position the player is attempting to sleep at
	 */
	private final BlockPos pos;

	public PlayerTrySleepEvent(EntityPlayer player, BlockPos pos) {
		super(player);
		this.pos = pos;
	}

	public EntityPlayer.SleepResult getResult() {
		return result;
	}

	public void setResult(EntityPlayer.SleepResult result) {
		this.result = result;
	}

	public BlockPos getPos() {
		return pos;
	}

}
