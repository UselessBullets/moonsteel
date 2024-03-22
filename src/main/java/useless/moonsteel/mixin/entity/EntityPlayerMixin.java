package useless.moonsteel.mixin.entity;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import useless.moonsteel.MoonSteel;
import useless.moonsteel.interfaces.IMoonGrav;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IMoonGrav {
	@Shadow
	public InventoryPlayer inventory;
	@Override
	public double moonsteel$getGravScalar() {
		double scalar = 0d;
		if (inventory.armorItemInSlot(3) != null && inventory.armorItemInSlot(3).getItem() == MoonSteel.helmetMoonSteel) scalar += 0.167d;
		if (inventory.armorItemInSlot(2) != null && inventory.armorItemInSlot(2).getItem() == MoonSteel.chestplateMoonSteel) scalar += 0.334d;
		if (inventory.armorItemInSlot(1) != null && inventory.armorItemInSlot(1).getItem() == MoonSteel.leggingsMoonSteel) scalar += 0.167d;
		if (inventory.armorItemInSlot(0) != null && inventory.armorItemInSlot(0).getItem() == MoonSteel.bootsMoonSteel) scalar += 0.334d;
		return (1/(scalar + 1));
	}
}
