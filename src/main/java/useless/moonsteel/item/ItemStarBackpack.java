package useless.moonsteel.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import tosutosu.betterwithbackpacks.BetterWithBackpacks;
import tosutosu.betterwithbackpacks.item.ItemBackpack;
import useless.moonsteel.StarBackpackInventory;
import useless.moonsteel.interfaces.IStarBackpack;

public class ItemStarBackpack extends ItemBackpack {
	public ItemStarBackpack(String name, int id) {
		super(name, id, StarBackpackInventory.starBackpackSize);
	}
	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isClientSide && BetterWithBackpacks.ENABLE_BACKPACKS) {
			((IStarBackpack)entityplayer).moonsteel$displayGuiStarBackpack();
		}

		return itemstack;
	}
}
