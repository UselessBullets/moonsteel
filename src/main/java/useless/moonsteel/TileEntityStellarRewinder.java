package useless.moonsteel;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;

public class TileEntityStellarRewinder extends TileEntity {
	public boolean inUse = false;
	public long checkCode = 0;
	public Side side = Side.NORTH;

	public ItemStack linkStar(ItemStack stack){
		checkCode = worldObj.rand.nextLong();
		side = Side.getSideById(worldObj.getBlockMetadata(x, y, z));
		stack.getData().putBoolean("moonsteel$has_location", true);
		stack.getData().putInt("moonsteel$x", x );
		stack.getData().putInt("moonsteel$y", y );
		stack.getData().putInt("moonsteel$z", z );
		stack.getData().putLong("moonsteel$checkcode", checkCode);
		setInUse(true);
		return stack;
	}
	public boolean canTeleport(ItemStack stack){
		boolean can = true;
		can &= stack.getData().getInteger("moonsteel$x") == x;
		can &= stack.getData().getInteger("moonsteel$y") == y;
		can &= stack.getData().getInteger("moonsteel$z") == z;
		can &= stack.getData().getLong("moonsteel$checkcode") == checkCode;
		return can;
	}
	public void setInUse(boolean flag){
		inUse = flag;
		worldObj.notifyBlockChange(x, y, z, worldObj.getBlockId(x, y, z));
	}
}
