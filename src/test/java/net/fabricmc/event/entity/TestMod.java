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

import net.fabricmc.api.Hook;
import net.fabricmc.base.Fabric;
import net.fabricmc.base.loader.Init;
import net.minecraft.entity.player.EntityPlayer;

public class TestMod {

	@Init
	public void init() {
		Fabric.getEventBus().subscribe(this);
		System.out.println("Test mod loaded");
	}

	@Hook(name = "test:onTrySleep", before = {}, after = {})
	public void onTrySleep(PlayerTrySleepEvent event) {
		event.setResult(EntityPlayer.SleepResult.NOT_SAFE);
	}

	@Hook(name = "test:onArmorTick", before = {}, after = {})
	public void onArmorTick(PlayerArmorTickEvent event) {
		System.out.println("Armor being ticked: " + event.getStack());
	}

}
