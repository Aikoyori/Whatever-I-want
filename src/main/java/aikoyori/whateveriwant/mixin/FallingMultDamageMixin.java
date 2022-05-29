package aikoyori.whateveriwant.mixin;

import aikoyori.whateveriwant.Whateveriwant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import org.checkerframework.common.reflection.qual.Invoke;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class FallingMultDamageMixin {
    /*

    @Redirect(method = "handleFallDamage",at=@At(target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",value = "INVOKE"))
    private boolean fallDamageMultMixin(LivingEntity instance, DamageSource source, float amount)
    {
        return instance.damage(source,amount*instance.getServer().getGameRules().getInt(Whateveriwant.fallDamageMultPercentage)/100.0f);
    }*/
    /*
    @Inject(method = "computeFallDamage",at=@At("RETURN"))
    private void calcFall(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir)
    {
        LivingEntity liv = (LivingEntity)(Object)this;
        StatusEffectInstance statusEffectInstance = liv.getStatusEffect(StatusEffects.JUMP_BOOST);
        float f = statusEffectInstance == null ? 0.0f : (float)(statusEffectInstance.getAmplifier() + 1);
        cir.setReturnValue(MathHelper.ceil(((fallDistance - 3.0f - f) * damageMultiplier)*liv.getServer().getGameRules().getInt(Whateveriwant.fallDamageMultPercentage)/100.0f));
    }*/

    @Shadow protected abstract int computeFallDamage(float fallDistance, float damageMultiplier);

    @Redirect(method = "handleFallDamage",at=@At(target = "Lnet/minecraft/entity/LivingEntity;computeFallDamage(FF)I",value = "INVOKE"))
    private int fallDamageMultMixin(LivingEntity instance, float fallDistance, float damageMultiplier)
    {
        LivingEntity liv = instance;

        if(!liv.world.isClient) {
            StatusEffectInstance statusEffectInstance = liv.getStatusEffect(StatusEffects.JUMP_BOOST);
            float f = statusEffectInstance == null ? 0.0f : (float) (statusEffectInstance.getAmplifier() + 1);

            int damage = (MathHelper.ceil(((fallDistance * liv.getServer().getGameRules().getInt(Whateveriwant.fallDamageMultPercentage) / 100.0f) - 3.0f - f) * damageMultiplier));
            //System.out.println(damage);
            return damage;
        }
        return this.computeFallDamage(fallDistance,damageMultiplier);
    }


}
