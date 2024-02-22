package useless.moonsteel;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.slot.Slot;
import useless.moonsteel.interfaces.IStarBackpack;

import java.util.List;

public class ContainerStarBackpack extends Container {
	public StarBackpackInventory backpackInventory;
	public ContainerStarBackpack(EntityPlayer player) {
		backpackInventory = ((IStarBackpack)player).moonsteel$getStarBackpackInventory();
		int slotsNum = this.backpackInventory.getSizeInventory();
		int rows = (int)Math.ceil((double)slotsNum / 9.0);

		for(int i = 0; i < rows; ++i) {
			int width = 9;
			if (i == rows - 1) {
				width = slotsNum - 9 * i;
			}

			for(int k = 0; k < width; ++k) {
				this.addSlot(new SlotStarBackpack(this.backpackInventory, k + i * 9, 8 + k * 18, 18 + 18 * i));
			}
		}

		for(int i = 0; i < 3; ++i) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for(int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(player.inventory, j, 8 + j * 18, 142));
		}
	}

	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		int chestSize = this.backpackInventory.getSizeInventory();
		if (slot.id >= 0 && slot.id < chestSize) {
			return this.getSlots(0, chestSize, false);
		} else {
			if (inventoryAction == InventoryAction.MOVE_ALL) {
				if (slot.id >= chestSize && slot.id < chestSize + 27) {
					return this.getSlots(chestSize, 27, false);
				}

				if (slot.id >= chestSize + 27 && slot.id < chestSize + 36) {
					return this.getSlots(chestSize + 27, 9, false);
				}
			} else if (slot.id >= chestSize && slot.id < chestSize + 36) {
				return this.getSlots(chestSize, 36, false);
			}

			return null;
		}
	}

	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		int chestSize = this.backpackInventory.getSizeInventory();
		return slot.id < chestSize ? this.getSlots(chestSize, 36, true) : this.getSlots(0, chestSize, false);
	}

	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return this.backpackInventory.canInteractWith(entityPlayer);
	}

}
