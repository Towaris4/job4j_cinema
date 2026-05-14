package ru.job4j.cinema.Repository;

import ru.job4j.cinema.model.*;
import java.util.Optional;

public interface FileRepository {
    Optional<File> findById(int id);
}
