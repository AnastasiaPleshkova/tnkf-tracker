package edu.java.bot.services.holder;

import edu.java.bot.services.commands.Command;
import java.util.Collection;

public interface Holder {
    Collection<Command> getCommands();

    Command getHandler(String command);

}
