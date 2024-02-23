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
import useless.moonsteel.interfaces.IFallenStar;
import useless.moonsteel.MoonSteel;

import java.util.Iterator;
import java.util.Random;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixin {
	@Shadow
	public abstract Chunk getChunkFromChunkCoords(int x, int z);

	@Shadow
	@Final
	public WorldType worldType;

	@Shadow
	protected int updateLCG;

	@Shadow
	public abstract EntityItem dropItem(int x, int y, int z, ItemStack itemStack);

	@Shadow
	public Random rand;
	@Inject(method = "updateBlocksAndPlayCaveSounds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getChunkFromChunkCoords(II)Lnet/minecraft/core/world/chunk/Chunk;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void makeTheStarsFall(CallbackInfo ci, Iterator var1, ChunkCoordinate coordinate, int chunkBlockX, int chunkBlockZ){
		if (!MoonSteel.isStarTime((World) (Object)this)) return;
		Chunk chunk = this.getChunkFromChunkCoords(coordinate.x, coordinate.z);
		if (rand.nextInt(2500) == 0){
			this.updateLCG = this.updateLCG * 3 + 1013904223;
			int randVal = this.updateLCG >> 2;
			int blockX = chunk.xPosition * 16 + (randVal & 0xF);
			int blockZ = chunk.zPosition * 16 + (randVal / 256 & 0xF);

			((IFallenStar)dropItem(blockX, worldType.getMaxY() + 32, blockZ, MoonSteel.fallenStar.getDefaultStack())).moonsteel$setDaylightSensitive(true);
		}
	}
}
