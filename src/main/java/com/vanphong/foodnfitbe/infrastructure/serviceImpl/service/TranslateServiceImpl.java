package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.TranslateService;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TranslateServiceImpl implements TranslateService {

    // Simple in-memory cache để giảm gọi lại với chuỗi giống nhau
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(TranslateServiceImpl.class);

    @Override
    public String translateToVietnamese(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        // Kiểm tra cache trước
        if (cache.containsKey(text)) {
            return cache.get(text);
        }

        try {
            URI uri = new URIBuilder("https://translate.googleapis.com/translate_a/single")
                    .addParameter("client", "gtx")
                    .addParameter("sl", "auto")
                    .addParameter("tl", "vi")
                    .addParameter("dt", "t")
                    .addParameter("q", text)
                    .build();

            String response = Request.Get(uri)
                    .connectTimeout(3000)
                    .socketTimeout(3000)
                    .execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);

            // Parse kết quả theo format JSON: [[[ "Dịch", "Original", ... ]]]
            String translated = extractTranslatedText(response);
            cache.put(text, translated);
            return translated;

        } catch (Exception e) {
            log.error("Lỗi khi dịch '{}': {}", text, e.getMessage());
            return "[Lỗi dịch]";
        }
    }

    private String extractTranslatedText(String json) {
        // JSON dạng: [[[ "Dịch", "Gốc", ... ]], ...]
        try {
            int start = json.indexOf("\"") + 1;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "[Không đọc được kết quả dịch]";
        }
    }
}
