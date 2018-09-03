package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.boosters.*;
import co.carrd.andwhat5.sts.commands.CommandSTS;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "sts",
        name = "STS",
        authors = "AnDwHaT5",
        version = "1.0.0"
)
public class STS {

    private static STS instance;
    @Inject
    public PluginContainer container;

    public static STS getInstance() {
        return instance;
    }

    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

    public STSConfig config;

    private CommentedConfigurationNode node;

    public static List<IBooster> boosters = new ArrayList<>();

    public void loadConfig() throws IOException, ObjectMappingException {
        //Config
        this.node = this.configLoader.load();
        TypeToken<STSConfig> type = TypeToken.of(STSConfig.class);
        this.config = node.getValue(type, new STSConfig());
        node.setValue(type, this.config);
        this.configLoader.save(node);
        //End config
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        instance = this;
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        //Loads the premade boosters into ram.
        boosters.add(new BoosterMoneyPerLevel());
        boosters.add(new BoosterShiny());
        boosters.add(new CustomTextureBooster());
        boosters.add(new HiddenAbilityBooster());
        boosters.add(new IVBooster());
        boosters.add(new LegendaryBooster());
        boosters.add(new PerfectIVBooster());

        CommandSpec sts = CommandSpec.builder()
                .description(Text.of("Opens the Sell to Server GUI."))
                .permission("sts.sts.base")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("reload"))))
                .executor(new CommandSTS())
                .build();
        Sponge.getCommandManager().register(this, sts, "sts");

    }
}
