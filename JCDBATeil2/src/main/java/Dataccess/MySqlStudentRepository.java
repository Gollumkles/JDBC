package Dataccess;

import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository{
    public MySqlStudentRepository() {
    }

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

    @Override
    public void searchByVorame(Object vorname) {

    }

    @Override
    public void searchByBirthday(Object birthday) {

    }

    @Override
    public void searchByNachname(Object nachname) {

    }
}
