package edu.java.bot.services.holder;

import edu.java.bot.services.commands.Command;
import edu.java.bot.services.commands.Unknown;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.util.function.UnaryOperator.identity;

@Component
public class CommandHolder implements Holder {
    private final Map<String, Command> commandMap;

    @Autowired
    public CommandHolder(List<Command> commandList) {
        this.commandMap = commandList.stream()
            .collect(Collectors.toMap(Command::getCommandName, identity()));
    }

    @Override
    public Collection<Command> getCommands() {
        return commandMap.values();
    }

    public Command getHandler(String command) {
        return commandMap.getOrDefault(command, new Unknown());
    }

}
