package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.text.Text;

public class CustomTextureBooster implements IBooster {
    @Override
    public int getMoney(NBTTagCompound pokemon) {
        return pokemon.hasKey(NbtKeys.CUSTOM_TEXTURE) ? STSConfig.General.customTextureBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Custom Texture Booster: $";
    }
}
