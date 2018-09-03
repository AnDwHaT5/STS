package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import com.pixelmonmod.pixelmon.util.helpers.SpriteHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

public class Utilities {

    /**
     * A List of Legendary Pokemon.
     */
    public static List<String> legends = Lists.newArrayList(
            "zapdos", "moltres", "articuno", "mew", "mewtwo", "ho-oh",
            "lugia", "entei", "raikou", "suicune", "celebi", "registeel", "regice",
            "regirock", "latios", "latias", "kyogre", "groudon", "rayquaza", "jirachi",
            "deoxys", "uxie", "mespret", "azelf", "manaphy", "dialga", "palkia",
            "giratina", "arceus", "darkrai", "cresselia", "shaymin", "victini",
            "cobalion", "terrakion", "verizion" , "zekrom", "reshiram", "kyurem",
            "thundorus", "tornadus", "landorus", "melotta", "keldeo", "genesect",
            "yveltal", "xerneas", "zygarde", "hoopa", "volcanion", "diance",
            "type:null", "silvally", "lunala", "solgaleo", "marshadow", "tapu bulu",
            "tapu koko", "tapu lele", "tapu fini", "cosmog", "cosmoem", "magearna", "zeraora"

    );

    /**
     * Gets an {@link ItemStack} of the Pokemon sprite specified.
     * @param species The species of the Pokemon.
     * @param form The form of the Pokemon
     * @param isEgg Whether or not the Pokemon is an egg.
     * @param isShiny Whether or not the Pokemon is shiny.
     * @return
     */
    public static ItemStack getPokemonItem(EnumPokemon species, int form, boolean isEgg, boolean isShiny) {
        net.minecraft.item.ItemStack nativeItem = new net.minecraft.item.ItemStack(PixelmonItems.itemPixelmonSprite);
        NBTTagCompound nbt = new NBTTagCompound();
        String idValue = String.format("%03d", species.getNationalPokedexInteger());
        if (isEgg){
            switch(species) {
                case Manaphy:
                case Togepi:
                    nbt.setString(NbtKeys.SPRITE_NAME, "pixelmon:sprites/eggs/manaphy1");
                    break;
                default:
                    nbt.setString(NbtKeys.SPRITE_NAME, "pixelmon:sprites/eggs/egg1");
                    break;
            }
        } else {
            if (isShiny) {
                nbt.setString(NbtKeys.SPRITE_NAME, "pixelmon:sprites/shinypokemon/" + idValue + SpriteHelper.getSpriteExtra(
                        species.name, form)
                );
            } else {
                nbt.setString(NbtKeys.SPRITE_NAME, "pixelmon:sprites/pokemon/" + idValue + SpriteHelper.getSpriteExtra(
                        species.name, form)
                );
            }
        }
        nativeItem.setTagCompound(nbt);
        return (ItemStack) (Object) nativeItem;
    }

    /**
     * Loops through all of the registered boosters and determines the price of the Pokemon.
     * @param pokemon The {@link NBTTagCompound} of the Pokemon the player is selling.
     * @return
     */
    public static int getPrice(NBTTagCompound pokemon)
    {
        int money = 0;
        for(IBooster booster : STS.boosters)
            money += booster.getMoney(pokemon);
        return money;
    }

    /**
     * Sells the specified Pokemon to the server.
     * @param player The player selling the Pokemon
     * @param pokemon The {@link NBTTagCompound} of the Pokemon the player is selling.
     * @param price How much the Pokemon is worth.
     * @return
     */
    public static boolean sellPokemon(Player player, NBTTagCompound pokemon, int price)
    {
        PlayerStorage ps = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
        if(ps == null)
            return false;
        int slot = 0;
        for(NBTTagCompound nbt : ps.partyPokemon)
        {
            if(nbt == pokemon) {
                ps.removeFromPartyPlayer(slot);
                ps.changeMoney(price);
                return true;
            }
            slot += 1;
        }
        return true;
    }
}
