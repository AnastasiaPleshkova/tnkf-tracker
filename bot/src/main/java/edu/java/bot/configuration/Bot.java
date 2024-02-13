package edu.java.bot.configuration;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.services.commands.Commandable;
import java.util.List;

public interface Bot extends AutoCloseable {

    void setUpdatesListener(UpdatesListener listener);

    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    void setBotMenu(List<? extends Commandable> commandList);

    void close();
}
