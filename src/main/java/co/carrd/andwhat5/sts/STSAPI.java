package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.interfaces.IBooster;

import java.util.List;

public class STSAPI {

    /**
     * Adds a booster to the list of active boosters.
     * @param booster A class that implements {@link IBooster}.
     */
    public static void addBooster(IBooster booster)
    {
        STS.boosters.add(booster);
    }

    /**
     * Removes a booster from the list of active boosters. If the booster is not in the list, this does nothing.
     * @param booster A class that implements {@link IBooster}.
     */
    public static void removeBooster(IBooster booster)
    {
        if(STS.boosters.contains(booster))
            STS.boosters.remove(booster);
    }

    /**
     * Gets a list of all registered boosters.
     * @return A List containing {@link IBooster} instances.
     */
    public static List<IBooster> getBoosters()
    {
        return STS.boosters;
    }
}
