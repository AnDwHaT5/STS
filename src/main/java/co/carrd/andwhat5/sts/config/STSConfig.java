package co.carrd.andwhat5.sts.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
@ConfigSerializable
public class STSConfig {

    @Setting
    General general = new General();

    @ConfigSerializable
    public static class General {
        @Setting(comment = "The amount of money per level of the Pokemon.")
        public static int moneyPerLevel = 15;

        @Setting(comment = "The amount of money per % of the Pokemons iv. Example: $5 for a 1% IV Pokemon, $10 for a 2% IV Pokemon.")
        public static int moneyPerIV = 5;

        @Setting(comment = "The amount of money given if the Pokemon has a Hidden Ability.")
        public static int hiddenAbilityBooster = 1000;

        @Setting(comment = "The amount of money given if the Pokemon has perfect IVs.")
        public static int perfectIVBooster = 1000;

        @Setting(comment = "The amount of money given if the Pokemon is shiny.")
        public static int shinyBooster = 2000;

        @Setting(comment = "The amount of money given if the Pokemon is a legendary.")
        public static int legendaryBooster = 4000;

        @Setting(comment = "The amount of money given to the player if the Pokemon has a custom texture.")
        public static int customTextureBooster = 1000;



    }
}
