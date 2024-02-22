package useless.moonsteel.mixin;

import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkCoordinate;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import useless.moonsteel.MoonSteel;

import java.util.Iterator;
import java.util.Random;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixin {
	@Shadow
	public abstract Chunk getChunkFromChunkCoords(int x, int z);

	@Shadow
	public abstract long getWorldTime();

	@Shadow
	@Final
	public WorldType worldType;

	@Shadow
	public abstract boolean isDaytime();

	@Shadow
	protected int updateLCG;

	@Shadow
	public abstract EntityItem dropItem(int x, int y, int z, ItemStack itemStack);

	@Shadow
	public Random rand;

	@Unique
	public int soundDelay = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci){
		if (soundDelay > 0){
			soundDelay--;
		}
	}
	@Inject(method = "updateBlocksAndPlayCaveSounds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getChunkFromChunkCoords(II)Lnet/minecraft/core/world/chunk/Chunk;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void makeTheStarsFall(CallbackInfo ci, Iterator var1, ChunkCoordinate coordinate, int chunkBlockX, int chunkBlockZ){
		if (worldType.hasCeiling()) return;
		if (this.isDaytime()) return;
		if (this.getWorldTime() % 2000 > 200) return;
		Chunk chunk = this.getChunkFromChunkCoords(coordinate.x, coordinate.z);
		if (rand.nextInt(1500) == 0){
			this.updateLCG = this.updateLCG * 3 + 1013904223;
			int randVal = this.updateLCG >> 2;
			int blockX = chunk.xPosition * 16 + (randVal & 0xF);
			int blockZ = chunk.zPosition * 16 + (randVal / 256 & 0xF);

			dropItem(blockX, worldType.getMaxY() + 32, blockZ, MoonSteel.fallenStar.getDefaultStack()).viewScale = 5;
			if (soundDelay <= 0){
				MoonSteel.playSound("moonsteel.starspawn", SoundType.ENTITY_SOUNDS, blockX, worldType.getMaxY() + 32, blockZ, 750.0f, 1f + rand.nextFloat() * 0.1f);
				soundDelay = rand.nextInt(10) + 3;
			}
		}
	}
}
