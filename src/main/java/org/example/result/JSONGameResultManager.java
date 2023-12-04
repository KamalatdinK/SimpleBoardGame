package org.example.result;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.example.JacksonHelper.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JSONGameResultManager implements GameResultManager {

    private Path filePath;

    public JSONGameResultManager(@NonNull Path filePath) {
        this.filePath = filePath;
    }

    @SneakyThrows
    @Override
    public List<GameResult> add(@NonNull GameResult result) throws IOException {
        var results = getAll();
        results.add(result);
        try (var out = Files.newOutputStream(filePath)) {
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    public List<GameResult> getAll() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<GameResult>();
        }
        try (var in = Files.newInputStream(filePath)) {
            return JacksonHelper.readList(in, GameResult.class);
        }
    }

}