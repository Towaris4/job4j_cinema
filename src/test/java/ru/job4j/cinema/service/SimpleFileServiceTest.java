package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.repository.file.Sql2oFileRepository;
import ru.job4j.cinema.service.file.SimpleFileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleFileServiceTest {

    private static SimpleFileService service;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void initService() {
        service = new SimpleFileService(
                new Sql2oFileRepository(DbTestHelper.getSql2o()),
                tempDir.toString());
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFileExistsThenGetContent() throws IOException {
        var filePath = tempDir.resolve("test.jpg");
        Files.write(filePath, new byte[]{1, 2, 3});
        var fileId = DbTestHelper.insertFile("test.jpg", filePath.toString());
        var found = service.getFileById(fileId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("test.jpg");
        assertThat(found.get().getContent()).isEqualTo(new byte[]{1, 2, 3});
    }

    @Test
    public void whenFileNotFoundThenEmpty() {
        assertThat(service.getFileById(999)).isEmpty();
    }
}
