package edu.java.bot.services;

import edu.java.bot.services.commands.Commandable;
import java.util.List;

public interface Handleable {
    List<? extends Commandable> getCommands();

    Commandable getHandler(String command);

}
