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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.MoonSteel;
import useless.moonsteel.interfaces.IMoonGrav;
import useless.moonsteel.interfaces.IStarZombie;

@Mixin(value = EntityArmoredZombie.class, remap = false)
public abstract class EntityZombieArmoredMixin extends EntityZombie implements IMoonGrav, IStarZombie {
	@Shadow
	@Final
	private boolean isHoldingSword;

	@Shadow
	public abstract int[] getArmorBreakPoints();

	public EntityZombieArmoredMixin(World world) {
		super(world);
	}
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo ci){
		entityData.define(20, (byte)0);
	}
	@Override
	public void spawnInit(){
		super.spawnInit();
		if (MoonSteel.isStarTime(this.world)){
			entityData.set(20, (byte)1);
		}
	}
	@Override
	public double moonsteel$getGravScalar() {
		if (moonsteel$isStarZombie()){
			double scalar = 1d;
			int[] breakPoints = this.getArmorBreakPoints();
			for (int i = 0; i < 4; ++i) {
				if (this.getHealth() > breakPoints[i]) scalar -= 0.125d;
			}
			return scalar;
		}
		return 1d;
	}

	@Override
	public boolean moonsteel$isStarZombie() {
		return entityData.getByte(20) == 1;
	}
	@Inject(method = "getHeldItem()Lnet/minecraft/core/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private void sword(CallbackInfoReturnable<ItemStack> cir){
		if (isHoldingSword && moonsteel$isStarZombie()){
			cir.setReturnValue(MoonSteel.starZombieSword);
		}
	}

	@Redirect(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/monster/EntityArmoredZombie;spawnAtLocation(Lnet/minecraft/core/item/ItemStack;F)Lnet/minecraft/core/entity/EntityItem;"))
	private EntityItem spawnRedirect(EntityArmoredZombie instance, ItemStack stack, float v){
		if (moonsteel$isStarZombie()){
			return spawnAtLocation(MoonSteel.fallenStar.getDefaultStack(), v);
		}
		return spawnAtLocation(stack, v);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("moonsteel$starzombie", moonsteel$isStarZombie() ? (byte) 1 : (byte)0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(20, tag.getByte("moonsteel$starzombie"));
	}
}
