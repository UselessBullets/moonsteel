package useless.moonsteel;

import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import turniplabs.halplibe.helper.TextureHelper;

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
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		ItemStack heldItem = player.getHeldItem();
		TileEntityStellarRewinder rewinder = (TileEntityStellarRewinder) world.getBlockTileEntity(x,y,z);
		if (heldItem != null && heldItem.getItem() == MoonSteel.connectedStar){
			rewinder.linkStar(heldItem);
			world.notifyBlockChange(x, y, z, this.id);
			return true;
		}
		return false;
	}
	@Override
	public int getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
		int index = super.getBlockTexture(blockAccess, x, y, z, side);
		TileEntityStellarRewinder rewinder = (TileEntityStellarRewinder) blockAccess.getBlockTileEntity(x,y,z);
		if (rewinder.inUse && index == TextureHelper.getOrCreateBlockTextureIndex(MoonSteel.MOD_ID,"stellarrewinder_front.png")){
			index = TextureHelper.getOrCreateBlockTextureIndex(MoonSteel.MOD_ID, "stellarrewinder_front_active.png");
		}
		return index;
	}
}
