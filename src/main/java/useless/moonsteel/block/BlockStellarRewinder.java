package useless.moonsteel.block;

import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import useless.moonsteel.MoonSteel;

public class BlockStellarRewinder extends BlockTileEntityRotatable {
	//Uses BlockTileEntityRotatable for its rotation properties not because its a tileEntity
	public BlockStellarRewinder(String key, int id, Material material) {
		super(key, id, material);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntityStellarRewinder(); // Not a tileEntity
	}
	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		ItemStack heldItem = player.getHeldItem();
		TileEntityStellarRewinder rewinder = (TileEntityStellarRewinder) world.getBlockTileEntity(x,y,z);
		if (heldItem != null && heldItem.getItem() == MoonSteel.connectedStar){
			rewinder.linkStar(heldItem);
			return true;
		}
		return false;
	}
}
