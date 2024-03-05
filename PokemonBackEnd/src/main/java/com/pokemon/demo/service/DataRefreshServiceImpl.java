package com.pokemon.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

@Service
public class DataRefreshServiceImpl implements DataRefreshService {

    private static final Logger logger = LoggerFactory.getLogger(DataRefreshServiceImpl.class);

    @Async
    @Override
    public CompletableFuture<String> refreshData() {

        String pythonScriptPath =
                "/Users/anthonylam/Desktop/FullStack Pokemon Price Project/PokemonPricePython/src/scraper.py";
        String[] command = new String[]{"python", pythonScriptPath};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File("../PokemonPricePython/src"));
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            // Capture the output from the script
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("Python script executed successfully");
                logger.info("Script output: {}", output);
                return CompletableFuture.completedFuture("success");
            } else {
                logger.error("Python script execution failed with exit code: {}", exitCode);
                logger.error("Script output: {}", output);
                return CompletableFuture.completedFuture("failure");
            }
        } catch (IOException e) {
            logger.error("An IOException was thrown during script execution", e);
            return CompletableFuture.completedFuture("Error: IOException encountered");
        } catch (InterruptedException e) {
            logger.error("The script execution was interrupted", e);
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Error: Execution was interrupted");
        } catch (Exception e) {
            logger.error("An unexpected error occurred during script execution", e);
            return CompletableFuture.completedFuture("Error: Unexpected exception encountered");
        }
    }
}
