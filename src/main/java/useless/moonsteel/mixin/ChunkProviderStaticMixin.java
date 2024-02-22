package useless.moonsteel.mixin;

import net.minecraft.client.world.chunk.provider.ChunkProviderStatic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.MoonSteel;

@Mixin(value = ChunkProviderStatic.class, remap = false)
public class ChunkProviderStaticMixin {
	@Inject(method = "canChunkExist(II)Z", at = @At("HEAD"), cancellable = true)
	private void overrideChunk(int i, int j, CallbackInfoReturnable<Boolean> cir){
		if (MoonSteel.forceChunkLoads){
			cir.setReturnValue(true);
		}
	}
}
