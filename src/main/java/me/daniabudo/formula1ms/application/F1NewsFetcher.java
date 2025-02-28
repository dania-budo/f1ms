package me.daniabudo.formula1ms.application;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class F1NewsFetcher {
    private static final String API_KEY = "438035c533ac487ca7e8783d8c1564c2";  // Replace with a valid NewsAPI key
    private static final String URL = "https://newsapi.org/v2/everything?q=Formula+1 OR F1&language=en&sortBy=publishedAt&pageSize=10&sources=bbc-news,espn,sky-sports&apiKey=" + API_KEY;

    public static List<String> fetchNews() {
        List<String> newsList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray articles = jsonObject.getAsJsonArray("articles");

                for (int i = 0; i < Math.min(10, articles.size()); i++) {  // Show top 10 news articles
                    JsonObject article = articles.get(i).getAsJsonObject();
                    String title = article.get("title").getAsString();
                    String url = article.get("url").getAsString();
                    newsList.add("📰 " + title + "\n🔗 " + url);
                }
            }
        } catch (IOException e) {
            newsList.add("⚠️ Error fetching news: " + e.getMessage());
        }
        return newsList;
    }
}
