package stern.dev.testfruit.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import stern.dev.testfruit.Testfruit;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class BrawlerBoostAbility extends Ability {
    private static final ITextComponent[] DESCRIPTION = (ITextComponent[]) AbilityHelper.registerDescriptionText(Testfruit.MOD_ID,"boost_brawler", new Pair[] { (Pair)ImmutablePair.of("L'utilisateur renforce son corps par sa volonté ce qui lui octroie une résistance hors du commun", null) });
    public static final AbilityCore<BrawlerBoostAbility> INSTANCE = new AbilityCore.Builder<>("Boost Brawler", AbilityCategory.STYLE, BrawlerBoostAbility::new)
                                                                        .addDescriptionLine(DESCRIPTION)
                                                                        .build();

    private final ChargeComponent chargeComponent;


    public BrawlerBoostAbility(AbilityCore<? extends IAbility> core) {
        super(core);
        chargeComponent = new ChargeComponent(this);
        this.addComponents(chargeComponent);
        this.isNew = true;
        addUseEvent(this::useEvent);
    }

    private void useEvent (LivingEntity entity, IAbility ability){
        this.chargeComponent.startCharging(entity, 5);
        float pourcentHP = (entity.getHealth()/entity.getMaxHealth())*100;
        if(pourcentHP < 50.00){
            entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 500, 2, true, true));
        }
        entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 500, 0, true, true));
        this.cooldownComponent.startCooldown(entity, 600);
    }

    private void onUse(LivingEntity entity, IAbility ability) {
        this.chargeComponent.startCharging(entity, 5);
    }

    private void onEnd(LivingEntity entity, IAbility ability) {
        this.cooldownComponent.startCooldown(entity, 600);
    }
}
