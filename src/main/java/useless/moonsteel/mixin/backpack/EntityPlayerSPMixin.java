package useless.moonsteel.mixin.backpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import useless.moonsteel.GuiStarBackpack;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin extends EntityPlayerMixin {
	@Shadow
	protected Minecraft mc;
	@Unique
	public EntityPlayerSP thisAs = (EntityPlayerSP) (Object)this;

	@Override
	public void moonsteel$displayGuiStarBackpack() {
		this.mc.displayGuiScreen(new GuiStarBackpack(this.thisAs));
	}
}
