package useless.moonsteel;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventorySorter;
import tosutosu.betterwithbackpacks.BetterWithBackpacks;

public class StarBackpackInventory implements IInventory {
	public static int starBackpackSize = 18;
	protected ItemStack[] backpackItemStacks;
	public EntityPlayer player;

	public StarBackpackInventory(EntityPlayer player) {
		this.player = player;
        this.backpackItemStacks = new ItemStack[starBackpackSize];
	}

	public int getSizeInventory() {
		return starBackpackSize;
	}

	public ItemStack getStackInSlot(int i) {
		return this.backpackItemStacks[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if (this.backpackItemStacks[i] != null) {
			if (this.backpackItemStacks[i].stackSize <= j) {
				ItemStack itemstack = this.backpackItemStacks[i];
				this.backpackItemStacks[i] = null;
				return itemstack;
			} else {
				ItemStack itemstack1 = this.backpackItemStacks[i].splitStack(j);
				if (this.backpackItemStacks[i].stackSize <= 0) {
					this.backpackItemStacks[i] = null;
				}

				return itemstack1;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.backpackItemStacks[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "Stardust Backpack";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {

	}

	public void readFromNBT(ListTag tagList) {
		this.backpackItemStacks = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < tagList.tagCount(); ++i) {
			CompoundTag nbttagcompound1 = (CompoundTag)tagList.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < this.backpackItemStacks.length) {
				this.backpackItemStacks[byte0] = ItemStack.readItemStackFromNbt(nbttagcompound1);
			}
		}
	}

	public ListTag writeToNBT(ListTag tagList) {
		for(int i = 0; i < this.backpackItemStacks.length; ++i) {
			if (this.backpackItemStacks[i] != null) {
				CompoundTag nbttagcompound1 = new CompoundTag();
				nbttagcompound1.putByte("Slot", (byte)i);
				this.backpackItemStacks[i].writeToNBT(nbttagcompound1);
				tagList.addTag(nbttagcompound1);
			}
		}

		return tagList;
	}

	public boolean canInteractWith(EntityPlayer entityPlayer) {
		if (!BetterWithBackpacks.ENABLE_BACKPACKS) {
			return false;
		} else if (entityPlayer.getHeldItem() == null) {
			return false;
		} else {
			ItemStack heldItem = entityPlayer.getHeldItem();
			return heldItem.getItem() == MoonSteel.cosmicBackpack;
		}
	}

	public void sortInventory() {
		InventorySorter.sortInventory(this.backpackItemStacks);
	}
}
