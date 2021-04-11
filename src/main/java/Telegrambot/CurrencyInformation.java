package Telegrambot;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CurrencyInformation {
    public static String getCurrencyInformation(String message, Model model) throws IOException {
        URL url = new URL("https://api.binance.com/api/v3/ticker/24hr?symbol=" + message);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setSymbol(object.getString("symbol"));
        model.setPriceChange(object.getString("priceChange"));
        model.setPriceChangePercent(object.getString("priceChangePercent"));
        model.setWeightedAvgPrice(object.getString("weightedAvgPrice"));
        model.setPrevClosePrice(object.getString("prevClosePrice"));
        model.setLastPrice(object.getString("lastPrice"));
        model.setLastQty(object.getString("lastQty"));
        model.setBidPrice(object.getString("bidPrice"));
        model.setAskPrice(object.getString("askPrice"));
        model.setAskQty(object.getString("askQty"));
        model.setOpenPrice(object.getString("openPrice"));
        model.setHighPrice(object.getString("highPrice"));
        model.setLowPrice(object.getString("lowPrice"));
        model.setVolume(object.getString("volume"));
        model.setQuoteVolume(object.getString("quoteVolume"));
        model.setOpenTime(object.getLong("openTime"));
        model.setCloseTime(object.getLong("closeTime"));
        model.setCount(object.getInt("count"));

        return "Валютная пара: " + model.getSymbol() + "\n" + "\n" +
                "Изменение цены: " + model.getPriceChange() + "\n" +
                "В процентах: " + model.getPriceChangePercent() + "%" + "\n"  + "\n" +
                "Средняя цена: " + model.getWeightedAvgPrice() + "\n" +
                "Предыдущая цена закрытия: " + model.getPrevClosePrice() + "\n" +
                "Последняя цена: " + model.getLastPrice() + "\n" +
                "Объем последней сделки: " + model.getLastQty() + "\n" + "\n" +
                "Продать за: " + model.getBidPrice() + "\n" +
                "Количество: " + model.getBidQty() + "\n" + "\n" +
                "Купить за: " + model.getAskPrice() + "\n" +
                "Количество: " + model.getAskQty() + "\n" + "\n" +
                "Цена открытия: " + model.getOpenPrice() + "\n" +
                "Самая высокая цена: " + model.getHighPrice() + "\n" +
                "Самая низкая цена: " + model.getLowPrice() + "\n" +
                "Объем: " + model.getVolume() + "\n" + "\n" +
                "Время открытия: " + getDate(model.getOpenTime()) + "\n" +
                "Время закрытия: " + getDate(model.getCloseTime()) + "\n" + "\n" +
                "Количество сделок: " + model.getCount();
    }

    private static String getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }
}
