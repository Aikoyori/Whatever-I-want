package aikoyori.whateveriwant.mixin;

import aikoyori.whateveriwant.Whateveriwant;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public class UnfallingBlock {
    @Inject(method = "tick",at=@At("TAIL"))
    private void sus(CallbackInfo ci)
    {
        Entity fall = (Entity)(Object)this;
        if(!fall.world.isClient)
        {
            int percent = fall.getServer().getGameRules().getInt(Whateveriwant.movementModifier);
            Random rand = new Random();
            if (!fall.hasNoGravity() && !fall.isOnGround()) {
                Vec3d vecVel = new Vec3d(rand.nextDouble()-0.5d,rand.nextDouble()-0.5d,rand.nextDouble()-0.5d ).multiply(0.5*(percent/100.0));
                fall.addVelocity(vecVel.x,vecVel.y+(vecVel.y + 0.02)*percent/100.0,vecVel.z);
            }
        }
    }
}
