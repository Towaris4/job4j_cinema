package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.repository.file.Sql2oFileRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository repository;

    @BeforeAll
    public static void initRepository() {
        repository = new Sql2oFileRepository(DbTestHelper.getSql2o());
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetFile() {
        var fileId = DbTestHelper.insertFile("poster.jpg", "posters/poster.jpg");
        var found = repository.findById(fileId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("poster.jpg");
        assertThat(found.get().getPath()).isEqualTo("posters/poster.jpg");
    }

    @Test
    public void whenFindByWrongIdThenEmpty() {
        assertThat(repository.findById(999)).isEmpty();
    }
}
