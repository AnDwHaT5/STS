package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.text.Text;


public class BoosterMoneyPerLevel implements IBooster {

    @Override
    public int getMoney(NBTTagCompound pokemon) {
        int level = pokemon.getInteger(NbtKeys.LEVEL);
        return level * STSConfig.General.moneyPerLevel;
    }

    @Override
    public String getItemLore() {
        return "Money from Level : $";
    }
}
