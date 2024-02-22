package useless.moonsteel.mixin.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.interfaces.IMoonGrav;
import useless.moonsteel.interfaces.IStarZombie;
import useless.moonsteel.MoonSteel;

@Mixin(value = EntityArmoredZombie.class, remap = false)
public class EntityZombieArmoredMixin extends EntityZombie implements IMoonGrav, IStarZombie {
	@Shadow
	@Final
	public int[] armorBreakPoints;
	@Shadow
	@Final
	private boolean isHoldingSword;
	@Unique
	public boolean isStarZombie = false;
	@Override
	public void spawnInit(){
		super.spawnInit();
		if (MoonSteel.isStarTime(this.world)){
			isStarZombie = true;
		}
	}

	public EntityZombieArmoredMixin(World world) {
		super(world);
	}

	@Override
	public double moonsteel$getGravScalar() {
		if (isStarZombie){
			double scalar = 1d;
			for (int i = 0; i < 4; ++i) {
				if (this.health > this.armorBreakPoints[i]) scalar -= 0.125d;
			}
			return scalar;
		}
		return 1d;
	}

	@Override
	public boolean moonsteel$isStarZombie() {
		return isStarZombie;
	}
	@Inject(method = "getHeldItem()Lnet/minecraft/core/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private void sword(CallbackInfoReturnable<ItemStack> cir){
		if (isHoldingSword && isStarZombie){
			cir.setReturnValue(MoonSteel.starZombieSword);
		}
	}

	@Redirect(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/monster/EntityArmoredZombie;spawnAtLocation(Lnet/minecraft/core/item/ItemStack;F)Lnet/minecraft/core/entity/EntityItem;"))
	private EntityItem spawnRedirect(EntityArmoredZombie instance, ItemStack stack, float v){
		if (isStarZombie){
			return spawnAtLocation(MoonSteel.fallenStar.getDefaultStack(), v);
		}
		return spawnAtLocation(stack, v);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("moonsteel$starzombie", isStarZombie);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.isStarZombie = tag.getBoolean("moonsteel$starzombie");
	}
}
