package useless.moonsteel;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

public class ItemConnectedStar extends Item {
	public ItemConnectedStar(String name, int id) {
		super(name, id);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		if (itemstack.getData().getBoolean("moonsteel$has_location") && itemstack.consumeItem(entityplayer)){
			entityplayer.setPos(itemstack.getData().getInteger("moonsteel$x") + 0.5f, itemstack.getData().getInteger("moonsteel$y") + 2, itemstack.getData().getInteger("moonsteel$z") + 0.5f);
			itemstack.getData().putBoolean("moonsteel$has_location", false);
		}
		return false;
	}
	public int getIconIndex(ItemStack itemstack) {
		if (itemstack.getData().getBoolean("moonsteel$has_location")){
			return TextureHelper.getOrCreateItemTextureIndex(MoonSteel.MOD_ID, "connected_star.png");
		}
		return this.getIconFromDamage(itemstack.getMetadata());
	}
}
