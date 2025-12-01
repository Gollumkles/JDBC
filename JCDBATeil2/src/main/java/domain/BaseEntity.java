package domain;

public abstract class BaseEntity {
    private Long id;
    public BaseEntity(Long id){
        setID(id);
    }

    public Long getID(){
        return this.id;
    }

    public void setID(Long id){
        if(id == null || id >= 0){
            this.id = id;
        } else {
            throw new InvalidValueException("Kurs-ID muss größer gleich 0 sein");
        }
    }


}
