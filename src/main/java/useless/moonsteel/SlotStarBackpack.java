package useless.moonsteel;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import tosutosu.betterwithbackpacks.item.ItemBackpack;

public class SlotStarBackpack extends Slot {
	public SlotStarBackpack(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return itemstack.getItem() != null && !(itemstack.getItem() instanceof ItemBackpack);
	}
}
