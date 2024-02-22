package useless.moonsteel.mixin;

import net.minecraft.client.render.entity.ArmoredZombieRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.interfaces.IStarZombie;

@Mixin(value = ArmoredZombieRenderer.class, remap = false)
public class ArmorZombieRendererMixin extends MobRenderer<EntityArmoredZombie> {
	public ArmorZombieRendererMixin(ModelBiped model, float shadowSize) {
		super(model, shadowSize);
	}

	@Inject(method = "setArmorModel(Lnet/minecraft/core/entity/monster/EntityArmoredZombie;IF)Z",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ArmoredZombieRenderer;loadTexture(Ljava/lang/String;)V", shift = At.Shift.AFTER))
	private void starZombie(EntityArmoredZombie zombie, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		if (((IStarZombie)zombie).moonsteel$isStarZombie()){
			this.loadTexture("/assets/moonsteel/armor/moonsteel_" + (renderPass != 2 ? 1 : 2) + ".png");
		}
	}
}
