package useless.moonsteel.fx;

import net.minecraft.client.entity.fx.EntityFlameFX;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.world.World;

import static useless.moonsteel.MoonSteel.MOD_ID;

public class EntityStarFX extends EntityFlameFX {
	private static final IconCoordinate star = TextureRegistry.getTexture(MOD_ID + ":item/particle_star");
	public float originalScale;
	public EntityStarFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5, Type.ORANGE);
		this.particleTexture = star;
		particleScale *= 2.25f;
		originalScale = particleScale;
	}
	@Override
	public void renderParticle(Tessellator t, float partialTick, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY) {
		float f6 = ((float)this.particleAge + partialTick) / (float)this.particleMaxAge;
		this.particleScale = originalScale * (1.0f - f6 * f6 * 0.5f);
		if (this.particleTexture != null) {
			float texMinU = (float)this.particleTexture.getIconUMin();
			float texMaxU = (float)this.particleTexture.getSubIconU(1.0);
			float texMinV = (float)this.particleTexture.getIconVMin();
			float texMaxV = (float)this.particleTexture.getSubIconV(1.0);
			float scale = 0.1F * this.particleScale;
			float posX = (float)(this.xo + (this.x - this.xo) * (double)partialTick - lerpPosX);
			float posY = (float)(this.yo + (this.y - this.yo) * (double)partialTick - lerpPosY);
			float posZ = (float)(this.zo + (this.z - this.zo) * (double)partialTick - lerpPosZ);
			float brightness = 1.0F;
			if (LightmapHelper.isLightmapEnabled()) {
				t.setLightmapCoord(this.getLightmapCoord(partialTick));
			} else {
				brightness = this.getBrightness(partialTick);
			}

			t.setColorOpaque_F(this.particleRed * brightness, this.particleGreen * brightness, this.particleBlue * brightness);
			t.addVertexWithUV(
                posX - rotationX * scale - rotationYZ * scale,
                posY - rotationXZ * scale,
                posZ - rotationZ * scale - rotationXY * scale,
                texMaxU,
                texMaxV
			);
			t.addVertexWithUV(
                posX - rotationX * scale + rotationYZ * scale,
                posY + rotationXZ * scale,
                posZ - rotationZ * scale + rotationXY * scale,
                texMaxU,
                texMinV
			);
			t.addVertexWithUV(
                posX + rotationX * scale + rotationYZ * scale,
                posY + rotationXZ * scale,
                posZ + rotationZ * scale + rotationXY * scale,
                texMinU,
                texMinV
			);
			t.addVertexWithUV(
                posX + rotationX * scale - rotationYZ * scale,
                posY - rotationXZ * scale,
                posZ + rotationZ * scale - rotationXY * scale,
                texMinU,
                texMaxV
			);
		}
	}
	@Override
	public void tick() {
		super.tick();
		this.particleTexture = star;
	}
	@Override
	public int getFXLayer() {
		return 2;
	}
}
