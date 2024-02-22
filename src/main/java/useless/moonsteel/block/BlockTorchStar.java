package useless.moonsteel.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.block.BlockTorch;
import net.minecraft.core.world.World;
import useless.moonsteel.fx.EntityMagicSmokeFX;
import useless.moonsteel.fx.EntityStarFX;

import java.util.Random;

public class BlockTorchStar extends BlockTorch {
	public BlockTorchStar(String key, int id) {
		super(key, id);
	}
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (!Global.isServer){
			Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
			int l = world.getBlockMetadata(x, y, z);
			double d = (float)x + 0.5f;
			double d1 = (float)y + 0.7f;
			double d2 = (float)z + 0.5f;
			double d3 = -0.125;
			double d4 = 0;
			if (l == 1) {
				mc.effectRenderer.addEffect(new EntityMagicSmokeFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
				mc.effectRenderer.addEffect(new EntityStarFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
			} else if (l == 2) {
				mc.effectRenderer.addEffect(new EntityMagicSmokeFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
				mc.effectRenderer.addEffect(new EntityStarFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
			} else if (l == 3) {
				mc.effectRenderer.addEffect(new EntityMagicSmokeFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
				mc.effectRenderer.addEffect(new EntityStarFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
			} else if (l == 4) {
				mc.effectRenderer.addEffect(new EntityMagicSmokeFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
				mc.effectRenderer.addEffect(new EntityStarFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
			} else {
				mc.effectRenderer.addEffect(new EntityMagicSmokeFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
				mc.effectRenderer.addEffect(new EntityStarFX(world, d - d4, d1 + d3, d2, 0.0, 0.0, 0.0));
			}
		}
	}
}
