package com.vanphong.foodnfitbe.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class firebaseConfig {
    @PostConstruct
    public void initFirebase() throws IOException {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String path = dotenv.get("FIREBASE_CONFIG_PATH");

        FileInputStream serviceAccount =
                new FileInputStream(path);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized successfully.");
        }
    }
}
