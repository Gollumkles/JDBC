package Dataccess;

import java.util.List;
import java.util.Optional;

public class MyStudentRepository implements BaseRepository{

    @Override
    public Optional insert(Object entity) {
        return Optional.empty();
    }

    @Override
    public Optional getById(Object id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public Optional update(Object entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Object id) {

    }

    public void searchByVorame(Object vorname){

    }

    public void searchByBirthday(Object birthday){

    }

    public void searchByNachname(Object nachname){

    }
}
