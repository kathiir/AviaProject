package service.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return format.parse(date);
        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
            return null;
        }
    }

    public static Date toLandingDate(Date quote, Date list) {
        Date date = new Date();

        date.setYear(list.getYear());
        date.setMonth(list.getMonth());
        date.setDate(list.getDate());

        date.setHours(quote.getHours());
        date.setMinutes(quote.getMinutes());
        date.setSeconds(quote.getSeconds());

        if (list.getTime()<date.getTime()) {
            date.setHours(quote.getHours()+7);
        }
        return date;
    }
}
