package useless.moonsteel.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import useless.moonsteel.IMoonGrav;
import useless.moonsteel.MoonSteel;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IMoonGrav {
	@Shadow
	public InventoryPlayer inventory;
	@Override
	public double moonsteel$getGravScalar() {
		double mult = 0d;
		if (inventory.armorItemInSlot(3) != null && inventory.armorItemInSlot(3).getItem() == MoonSteel.helmetMoonSteel) mult += 0.25d;
		if (inventory.armorItemInSlot(2) != null && inventory.armorItemInSlot(2).getItem() == MoonSteel.chestplateMoonSteel) mult += 0.25d;
		if (inventory.armorItemInSlot(1) != null && inventory.armorItemInSlot(1).getItem() == MoonSteel.leggingsMoonSteel) mult += 0.25d;
		if (inventory.armorItemInSlot(0) != null && inventory.armorItemInSlot(0).getItem() == MoonSteel.bootsMoonSteel) mult += 0.25d;
		return 1d - (0.5d * mult);
	}
}
