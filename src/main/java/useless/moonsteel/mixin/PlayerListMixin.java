package useless.moonsteel.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.interfaces.IStarBackpack;

@Mixin(value = PlayerList.class, remap = false)
public class PlayerListMixin {
	@Inject(method = "recreatePlayerEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/InventoryPlayer;transferAllContents(Lnet/minecraft/core/player/inventory/InventoryPlayer;)V", shift = At.Shift.AFTER))
	public void keepBackpackMP(EntityPlayerMP previousPlayer, int i, CallbackInfoReturnable<EntityPlayerMP> cir, @Local(name = "newPlayer") EntityPlayerMP newPlayer){
		((IStarBackpack)newPlayer).moonsteel$setStarBackpackInventory(((IStarBackpack)previousPlayer).moonsteel$getStarBackpackInventory());
	}
}
