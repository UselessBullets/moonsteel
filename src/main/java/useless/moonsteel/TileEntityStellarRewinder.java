package useless.moonsteel;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;

public class TileEntityStellarRewinder extends TileEntity {
	public boolean inUse = false;
	public long checkCode = 0;

	public ItemStack linkStar(ItemStack stack){
		checkCode = worldObj.rand.nextLong();
		Side side = Side.getSideById(worldObj.getBlockMetadata(x, y, z));
		stack.getData().putBoolean("moonsteel$has_location", true);
		stack.getData().putInt("moonsteel$x", x + side.getOffsetX());
		stack.getData().putInt("moonsteel$y", y + side.getOffsetY());
		stack.getData().putInt("moonsteel$z", z + side.getOffsetZ());
		stack.getData().putLong("moonsteel$checkcode", checkCode);
		inUse = true;
		return stack;
	}
}
