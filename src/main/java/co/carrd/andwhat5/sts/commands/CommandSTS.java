package co.carrd.andwhat5.sts.commands;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.ui.UISTS;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.io.IOException;

public class CommandSTS implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(src instanceof Player)
        {
            if(args.hasAny("reload"))
            {
                if(((String)args.getOne("reload").get()).equalsIgnoreCase("reload")) {
                    if (src.hasPermission("sts.sts.admin")) {
                        try {
                            STS.getInstance().loadConfig();
                            src.sendMessage(Text.of("[STS] Reloaded config!"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ObjectMappingException e) {
                            e.printStackTrace();
                        }
                        return CommandResult.success();
                    } else {
                        src.sendMessage(Text.of("[STS] You do not have permission to use this."));
                        return CommandResult.success();
                    }
                }
            }
            Player player = (Player)src;
            PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
            if(storage == null)
            {
                player.sendMessage(Text.of("[STS] Could not load your party."));
                return CommandResult.success();
            }
            UISTS  ui = new UISTS(player, storage.partyPokemon);
            ui.displayGUI();
        }
        return CommandResult.success();
    }
}
