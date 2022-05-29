package aikoyori.whateveriwant;

import aikoyori.whateveriwant.mixin.GameRules$IntRuleAccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.GameRules;

import static aikoyori.whateveriwant.mixin.GameRulesMixin.invokeRegister;

public class Whateveriwant implements ModInitializer {
    public static GameRules.Key<GameRules.IntRule> movementModifier = invokeRegister("movementModifier", GameRules.Category.MOBS, GameRules$IntRuleAccessor.invokeCreate(0));
    public static GameRules.Key<GameRules.IntRule> fallDamageMultPercentage = invokeRegister("fallDamageMultiplierPercentage", GameRules.Category.MISC, GameRules$IntRuleAccessor.invokeCreate(100));
    public static GameRules.Key<GameRules.IntRule> bounceFactorPercent = invokeRegister("bounceFactorPercent", GameRules.Category.MISC, GameRules$IntRuleAccessor.invokeCreate(0));

    @Override
    public void onInitialize() {


    }
}
