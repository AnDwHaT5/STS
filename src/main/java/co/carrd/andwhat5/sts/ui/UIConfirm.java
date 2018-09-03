package co.carrd.andwhat5.sts.ui;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.function.Consumer;

public class UIConfirm {

    int price;
    NBTTagCompound pokemon;
    Player player;

    public UIConfirm(Player player, NBTTagCompound pokemon, int price)
    {
        this.price = price;
        this.pokemon = pokemon;
        this.player = player;
    }

    public void displayGUI() {
        Element blueGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element redGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.RED).build());
        Element blackGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());
        Element whiteGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.WHITE).build());
        Consumer<Action.Click> close = c ->
        {
            PlayerStorage s = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
            if(s == null)
            {
                player.closeInventory();
            }
            else {
                UISTS ui = new UISTS(player, s.partyPokemon);
                ui.displayGUI();
            }
        };
        Consumer<Action.Click> sell = s ->
        {
            if (Utilities.sellPokemon(player, pokemon, price)) {
                player.sendMessage(Text.of("[STS] You sold your " + pokemon.getString(NbtKeys.NAME) + " for $" + price + "!"));
                player.closeInventory();
            } else {
                player.sendMessage(Text.of("[STS] Error selling " + pokemon.getString(NbtKeys.NAME) + ". Is it still in your party?"));
                player.closeInventory();
            }
        };

        ItemStack rDye = ItemStack.builder().itemType(ItemTypes.DYE).add(Keys.DYE_COLOR, DyeColors.RED).build();
        ItemStack gDye = ItemStack.builder().itemType(ItemTypes.DYE).add(Keys.DYE_COLOR, DyeColors.GREEN).build();
        rDye.offer(Keys.DISPLAY_NAME, Text.of("\u2605 " + "Cancel" + " \u2605"));
        gDye.offer(Keys.DISPLAY_NAME, Text.of("\u2605 " + "Sell " + pokemon.getString(NbtKeys.NAME) + " for $" + price + "." + " \u2605"));

        Element redDye = Element.of(rDye, close);
        Element greenDye = Element.of(gDye, sell);


        Layout layout = Layout.builder()
                .set(blueGlass, 0, 1, 3, 4, 5, 7, 8)
                .set(redGlass, 2, 6)
                .set(blackGlass, 9, 17)
                .set(whiteGlass, 13, 18, 19, 20, 21, 22, 23, 24, 25, 26)
                .set(redDye, 12)
                .set(greenDye, 14)
                .build();
        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of("Sell to Server - Confirm"))).build(STS.getInstance().container);
        view.define(layout);
        view.open(player);
    }
}
