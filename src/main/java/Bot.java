import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.telegram.telegrambots.logging.BotLogger.log;


public class Bot extends TelegramWebhookBot {
    private static Bot bot;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        return handleUpdate(update);
    }
    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;
        if(update.hasMessage()) {
            if(update.getMessage().getText().toLowerCase().equals("hello")){
                replyMessage = sendInlineKeyBoardMessage(update.getMessage().getChatId(), update);
            } else {
                String message = update.getMessage().getText();
                if (message.equals("Помощь") || message.equals("Привет")) {
                    replyMessage = sendMsg(update.getMessage().getChatId().toString(), "Нахожусь в стадии тестирования, скоро отвечу.");
                } else

                    replyMessage = sendMsg(update.getMessage().getChatId().toString(), "Невероятно, со мной разговариваю люди!");
            }
        } else  if(update.hasCallbackQuery()) {
            return new SendMessage().setText(
                    update.getCallbackQuery().getData())
                    .setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        return replyMessage;
    }
    /*@Override
        public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            if(update.getMessage().getText().toLowerCase().equals("hello")){
                try {
                    execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                String message = update.getMessage().getText();
                if (message.equals("Помощь") || message.equals("Привет")) {
                    sendMsg(update.getMessage().getChatId().toString(), "Нахожусь в стадии тестирования, скоро отвечу.");
                } else

                sendMsg(update.getMessage().getChatId().toString(), "Невероятно, со мной разговариваю люди!");
            }
        } else  if(update.hasCallbackQuery()) {
            try {
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized SendMessage sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);

        setButtons(sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
        return sendMessage;
    }



    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "papoffBot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "1083085389:AAE05uxN-yWvsPnDdRy5ehDnPGaeWr7K20Q";
    }

    @Override
    public String getBotPath() {
        return "https://telegrambot32.herokuapp.com";
    }


    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId, Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        String text = "Здравствуйте " + update.getMessage().getFrom().getUserName();
        return new SendMessage().setChatId(chatId).setText(text).setReplyMarkup(inlineKeyboardMarkup);
    }

    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(Bot.getInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
    public static Bot getInstance() {
        if (bot == null) {
            bot = new Bot();
        }
        return bot;
    }
}
