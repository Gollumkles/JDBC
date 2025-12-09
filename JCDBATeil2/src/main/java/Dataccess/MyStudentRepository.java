package Dataccess;

import java.util.List;
import java.util.Optional;

public interface MyStudentRepository extends BaseRepository {

    @Override
    Optional insert(Object entity);

    @Override
    Optional getById(Object id);

    @Override
    List getAll();

    @Override
    Optional update(Object entity);

    @Override
    void deleteById(Object id);

    void searchByVorame(Object vorname);

    void searchByBirthday(Object birthday);

    void searchByNachname(Object nachname);


}
