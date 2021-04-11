package Telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinanceBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MiroshaBinanceBot";
    }

    @Override
    public String getBotToken() {
        return "botToken";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Model model = new Model();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendBotMessage(message, "Введите название валютной пары." + "\n" +
                            "Например: ZECUSDT, ethusdt и т.п. (регистр не важен).");
                    break;
                case "/start":
                    sendBotMessage(message, "Введите название валютной пары, чтобы получить информацию о ней за последние 24 часа.");
                    break;
                default:
                    try {
                        sendBotMessage(message, CurrencyInformation.getCurrencyInformation(message.getText().toUpperCase(), model));
                    } catch (IOException e) {
                        sendBotMessage(message, "Что-то пошло не так");
                    }
            }
        }
    }

    public void sendBotMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("/help"));

        keyboardRowList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
