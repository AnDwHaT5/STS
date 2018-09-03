package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.text.Text;

public class HiddenAbilityBooster implements IBooster {
    @Override
    public int getMoney(NBTTagCompound pokemon) {
        return pokemon.getInteger(NbtKeys.ABILITY_SLOT) == 2 ? STSConfig.General.hiddenAbilityBooster : 0;
    }

    @Override
    public String getItemLore()
    {
        return "Hidden Ability Booster: $";
    }
}
