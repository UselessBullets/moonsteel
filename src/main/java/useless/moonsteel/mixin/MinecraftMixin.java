package useless.moonsteel.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.MoonSteel;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Inject(method = "runTick", at = @At("HEAD"))
	private void tick(CallbackInfo ci){
		if (MoonSteel.soundDelay > 0){
			MoonSteel.soundDelay--;
		}
	}
}
