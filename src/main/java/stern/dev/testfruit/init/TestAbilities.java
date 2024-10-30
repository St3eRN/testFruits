package stern.dev.testfruit.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import stern.dev.testfruit.Testfruit;
import stern.dev.testfruit.abilities.BrawlerBoostAbility;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class TestAbilities {
    public static final DeferredRegister<AbilityCore<?>> ABILITIES = DeferredRegister.create(ModRegistries.ABILITIES, Testfruit.MOD_ID);
    private static HashMap<String, String> langMap = new HashMap<>();
    public static HashMap<String, String> getLangMap() {
        return langMap;
    }
    private static void registerAbilities(AbilityCore[] abilities) {
        Arrays.stream(abilities).filter(Objects::nonNull).forEach(TestAbilities::registerAbility);
    }

    public static void register(IEventBus eventBus){
        WyRegistry.registerAbility(BrawlerBoostAbility.INSTANCE);
    }

    private static <T extends IAbility>  void registerAbility(AbilityCore<T> core) {
        String resourceName = WyHelper.getResourceName(core.getId());
        ResourceLocation key = new ResourceLocation(Testfruit.MOD_ID, resourceName);
        RegistryObject<AbilityCore<?>> ret = RegistryObject.of(key, ModRegistries.ABILITIES);
        if (!ABILITIES.getEntries().contains(ret)) {
            ABILITIES.register(resourceName, () -> core);
            if (core.getIcon() == null) {
                core.setIcon(new ResourceLocation(key.getNamespace(), "textures/abilities/" + key.getPath() + ".png"));
            }
        }
    }


}
