package useless.moonsteel.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.MoonSteel;
import useless.moonsteel.interfaces.IStarBackpack;

@Mixin(value= NetClientHandler.class,remap = false)
public abstract class NetClientHandlerMixin {
	@Final
	@Shadow
	private Minecraft mc;

	@Inject(method="handleOpenWindow",at=@At("TAIL"))
	public void inject(Packet100OpenWindow packet100openwindow, CallbackInfo ci) {
		if (packet100openwindow.inventoryType == MoonSteel.GUI_ID) {
			((IStarBackpack)(mc.thePlayer)).moonsteel$displayGuiStarBackpack();
			this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		}
	}
}
