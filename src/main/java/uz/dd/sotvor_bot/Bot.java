package uz.dd.sotvor_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String userName;

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    sendMessage(message, true, false, String.format("Assalomu alaykum %s. Narsangizni sotmoqchi bo'lsangiz quyidagi bo'limlardan birini tanlang", message.getChat().getFirstName()));
                }
            } else if (message.hasPhoto()) {

            }

        } else if (update.hasCallbackQuery()) {

        } else if (update.hasInlineQuery()) {

        }
    }

    private void sendMessage(Message message, boolean withKeyboard, boolean withInlineButton, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        sendMessage.enableHtml(true);
        sendMessage.disableWebPagePreview();
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup(new ArrayList<>()));
        if (withKeyboard) {
            setKeyboard(message, sendMessage);
        } else if (withInlineButton) {
//            sendMessage.setReplyMarkup(setInlineButton(""));
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void setKeyboard(Message message, SendMessage sendMessage) {
        String text = message.getText();
        Long chatId = message.getChatId();
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(markup);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        if (text.startsWith("/start")) {
            KeyboardRow row = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            row.add(new KeyboardButton("Kitob \uD83D\uDCDA"));
            row.add(new KeyboardButton("Kiyim \uD83D\uDC57"));
            row.add(new KeyboardButton("Telefon \uD83D\uDCF1"));
            row2.add(new KeyboardButton("Avtomobil \uD83D\uDE97"));
            row2.add(new KeyboardButton("Uy \uD83C\uDFE1"));
            row2.add(new KeyboardButton("Jonivor \uD83D\uDC08"));
            keyboardRows.add(row);
            keyboardRows.add(row2);
        }
        markup.setKeyboard(keyboardRows);
    }


}
