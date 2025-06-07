package com.vanphong.foodnfitbe.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class firebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(firebaseConfig.class);

    @Value("${firebase.config.path:}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initFirebase() {
        try {
            logger.info("🚀 Initializing Firebase...");
            InputStream serviceAccountStream;

            if (firebaseConfigPath != null && !firebaseConfigPath.trim().isEmpty()) {
                File configFile = new File(firebaseConfigPath);
                if (!configFile.exists()) {
                    logger.error("❌ Firebase config file not found at path: {}", firebaseConfigPath);
                    return;
                }
                logger.info("📁 Using Firebase config from file path: {}", firebaseConfigPath);
                serviceAccountStream = new FileInputStream(configFile);
            } else {
                String jsonEnv = System.getenv("FIREBASE_CONFIG_JSON");
                if (jsonEnv == null || jsonEnv.trim().isEmpty()) {
                    logger.error("❌ Neither 'firebase.config.path' nor 'FIREBASE_CONFIG_JSON' found. Firebase init aborted.");
                    return;
                }

                File tempFile = File.createTempFile("firebase", ".json");
                try (FileWriter writer = new FileWriter(tempFile)) {
                    writer.write(jsonEnv);
                }

                logger.info("📄 Loaded Firebase config from FIREBASE_CONFIG_JSON (env).");
                serviceAccountStream = new FileInputStream(tempFile);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("✅ Firebase initialized successfully.");
            } else {
                logger.info("ℹ️ Firebase already initialized.");
            }

        } catch (IOException e) {
            logger.error("❌ Failed to initialize Firebase: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("❌ Unexpected error during Firebase initialization: {}", e.getMessage(), e);
        }
    }
}