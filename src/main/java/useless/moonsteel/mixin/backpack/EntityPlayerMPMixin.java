package useless.moonsteel.mixin.backpack;

import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import useless.moonsteel.ContainerStarBackpack;
import useless.moonsteel.MoonSteel;

@Mixin(value = EntityPlayerMP.class, remap = false)
public abstract class EntityPlayerMPMixin extends EntityPlayerMixin {
	@Shadow
	protected abstract void getNextWindowId();

	@Shadow
	private int currentWindowId;
	@Unique
	public EntityPlayerMP thisAs = (EntityPlayerMP) (Object)this;

	@Override
	public void moonsteel$displayGuiStarBackpack() {
		this.getNextWindowId();
		ContainerStarBackpack backpack = new ContainerStarBackpack(thisAs);
		this.thisAs
			.playerNetServerHandler
			.sendPacket(
				new Packet100OpenWindow(this.currentWindowId, MoonSteel.GUI_ID, "moonsteel$StarBackpack", backpack.backpackInventory.getSizeInventory())
			);
		this.thisAs.craftingInventory = backpack;
		this.thisAs.craftingInventory.windowId = this.currentWindowId;
		this.thisAs.craftingInventory.onContainerInit(this.thisAs);
	}
}
