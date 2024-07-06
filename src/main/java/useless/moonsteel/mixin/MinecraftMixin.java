package useless.moonsteel.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.interfaces.IStarBackpack;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Shadow
	public EntityPlayerSP thePlayer;

	@Inject(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;setGamemode(Lnet/minecraft/core/player/gamemode/Gamemode;)V", shift = At.Shift.AFTER))
	public void keepBackpack(boolean flag, int i, CallbackInfo ci, @Local EntityPlayer previousPlayer){
		((IStarBackpack)thePlayer).moonsteel$setStarBackpackInventory(((IStarBackpack)previousPlayer).moonsteel$getStarBackpackInventory());
	}
}
