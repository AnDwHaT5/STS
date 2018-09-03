package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.text.Text;

public class BoosterShiny implements IBooster {
    @Override
    public int getMoney(NBTTagCompound pokemon) {
        boolean isShiny = pokemon.getBoolean(NbtKeys.IS_SHINY);
        return isShiny ? STSConfig.General.shinyBooster : 0;
    }

    @Override
    public String getItemLore()
    {
        return "Shiny Pokemon Booster: $";
    }
}
