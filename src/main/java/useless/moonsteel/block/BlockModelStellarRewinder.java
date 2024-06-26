package useless.moonsteel.block;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import useless.moonsteel.MoonSteel;

public class BlockModelStellarRewinder<T extends Block> extends BlockModelHorizontalRotation<T> {
	private static IconCoordinate icon_front = TextureRegistry.getTexture(MoonSteel.MOD_ID + ":block/stellarrewinder_front");
	private static IconCoordinate icon_front_active = TextureRegistry.getTexture(MoonSteel.MOD_ID + ":block/stellarrewinder_front_active");
	public BlockModelStellarRewinder(Block block) {
		super(block);
	}
	@Override
	public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
		IconCoordinate iconCoordinate = super.getBlockTexture(blockAccess, x, y, z, side);
		TileEntityStellarRewinder rewinder = (TileEntityStellarRewinder) blockAccess.getBlockTileEntity(x,y,z);
		if (rewinder.inUse && iconCoordinate == icon_front){
			iconCoordinate = icon_front_active;
		}
		return iconCoordinate;
	}
}
