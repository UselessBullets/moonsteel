package useless.moonsteel.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.interfaces.IMoonGrav;
import useless.moonsteel.MoonSteel;
import useless.moonsteel.mixin.accessor.ItemToolSwordAccessor;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {
	public EntityLivingMixin(World world) {
		super(world);
	}

	@Shadow
	protected abstract void dropFewItems();
	@Redirect(method = "moveEntityWithHeading(FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/EntityLiving;yd:D", opcode = Opcodes.PUTFIELD))
	private void entityGravity(EntityLiving entity, double yd){ //Probably terrible way of modifying gravity by a scalar
		if (entity instanceof IMoonGrav){
			double offset = -(yd - this.yd);
			double scalar = ((IMoonGrav) entity).moonsteel$getGravScalar();
			if ((0.021 > offset && offset > 0.019) || (0.081 > offset && offset > 0.079)){ // If falling in water or in air
				entity.yd -= offset * scalar;
			} else if ((-0.251 < yd && yd < -0.249)) { // Terminal velocity
				entity.yd = yd * scalar;
			} else { // Else regular behavior
				entity.yd = yd;
			}
		} else {
			entity.yd = yd;
		}
	}

	@Inject(method = "onDeath(Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityLiving;dropFewItems()V"))
	private void multiplyDrop(Entity entity, CallbackInfo ci){
		if (entity instanceof EntityPlayer){
			ItemStack heldStack = ((EntityPlayer) entity).getHeldItem();
			if (heldStack != null && heldStack.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) heldStack.getItem()).getMaterial() == MoonSteel.moonSteelTool){
				for (int i = 0; i < random.nextInt(3); i++) {
					dropFewItems();
				}
			}
		}
	}
}
