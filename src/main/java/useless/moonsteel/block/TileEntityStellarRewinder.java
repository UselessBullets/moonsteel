package useless.moonsteel.block;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.chunk.Chunk;
import useless.moonsteel.MoonSteel;

public class TileEntityStellarRewinder extends TileEntity {
	public boolean inUse = false;
	public long checkCode = 0;
	public Side side = Side.NORTH;

	public ItemStack linkStar(ItemStack stack){
		checkCode = worldObj.rand.nextLong();
		side = Side.getSideById(worldObj.getBlockMetadata(x, y, z));
		if (stack.getData().getBoolean("moonsteel$has_location")){
			int destX = stack.getData().getInteger("moonsteel$x");
			int destY = stack.getData().getInteger("moonsteel$y");
			int destZ = stack.getData().getInteger("moonsteel$z");
			MoonSteel.forceChunkLoads = true;
			Chunk chunk = worldObj.getChunkProvider().provideChunk(destX >> 4, destZ >> 4);
			MoonSteel.forceChunkLoads = false;
			TileEntity te = chunk.getTileEntity(destX &0xF, destY, destZ &0xF);
			if (te instanceof TileEntityStellarRewinder && ((TileEntityStellarRewinder) te).canTeleport(stack)){
				((TileEntityStellarRewinder) te).setInUse(false);
			}
		}
		stack.getData().putBoolean("moonsteel$has_location", true);
		stack.getData().putInt("moonsteel$x", x );
		stack.getData().putInt("moonsteel$y", y );
		stack.getData().putInt("moonsteel$z", z );
		stack.getData().putInt("moonsteel$dimension", worldObj.dimension.id);
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
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		this.inUse = tag.getBoolean("inuse");
		this.checkCode = tag.getLong("checkcode");
		this.side = Side.getSideById(tag.getInteger("side"));
	}

	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		tag.putBoolean("inuse", inUse);
		tag.putLong("checkcode", checkCode);
		tag.putInt("side", side.getId());
	}
}
