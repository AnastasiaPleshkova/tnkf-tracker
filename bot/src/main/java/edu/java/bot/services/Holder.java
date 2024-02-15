package edu.java.bot.services;

import edu.java.bot.services.commands.Commandable;
import edu.java.bot.services.commands.Unknown;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.util.function.UnaryOperator.identity;

@Component
public class Holder implements Handleable {
    private final Map<String, Commandable> commandMap;

    @Autowired
    public Holder(List<Commandable> commandList) {
        this.commandMap = commandList.stream()
            .collect(Collectors.toMap(Commandable::getCommandName, identity()));
    }

    @Override
    public List<? extends Commandable> getCommands() {
        return commandMap.values().stream().toList();
    }

    public Commandable getHandler(String command) {
        return commandMap.getOrDefault(command, new Unknown());
    }

}
