package aikoyori.whateveriwant.mixin;

import aikoyori.whateveriwant.Whateveriwant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockLanderMixin {
    /*
    @Inject(method="onEntityLand",at = @At("HEAD"))
    private void entityland(BlockView world, Entity entity, CallbackInfo ci){

        if (entity.bypassesLandingEffects()) {
            ((Block)(Object)this).onEntityLand(world, entity);
        } else {
            Vec3d vec3d = entity.getVelocity();
            if (vec3d.y < 0.0) {
                double d = entity instanceof LivingEntity ? 1.0 : 0.8;
                d*=entity.getServer().getGameRules().getInt(Whateveriwant.bounceFactorPercent)/100.0f;
                entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
            }
        }
    }*/
    @Redirect(method = "onEntityLand",at=@At(value="INVOKE",target="Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    public void bounceStuff(Entity instance, Vec3d velocity){

        if(!instance.world.isClient) {
            if(!(instance instanceof PlayerEntity))
            {
                if(instance.getServer().getGameRules().getInt(Whateveriwant.bounceFactorPercent)!=0) {
                    Vec3d vec3d = instance.getVelocity();
                    if (vec3d.y < 0.0) {
                        double d = instance instanceof LivingEntity ? 1.0 : 0.8;
                        d *= instance.getServer().getGameRules().getInt(Whateveriwant.bounceFactorPercent) / 100.0f;
                        instance.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
                    }
                }
            }

        }
    }


}
