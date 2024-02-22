package useless.moonsteel.mixin;

import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.server.world.chunk.provider.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.MoonSteel;

@Mixin(value = ChunkProviderServer.class, remap = false)
public class ChunkProviderServerMixin {
	@Shadow
	public boolean chunkLoadOverride;

	@Inject(method = "provideChunk(II)Lnet/minecraft/core/world/chunk/Chunk;", at = @At("HEAD"))
	private void overrideChunks(int chunkX, int chunkZ, CallbackInfoReturnable<Chunk> cir){
		this.chunkLoadOverride = MoonSteel.forceChunkLoads;
	}
}
