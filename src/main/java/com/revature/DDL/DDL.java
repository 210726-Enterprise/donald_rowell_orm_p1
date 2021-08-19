package com.revature.DDL;

public class DDL {

    private Alter alter;
    private Create create;
    private Drop drop;

    public DDL(){
        alter = new Alter();
        create = new Create();
        drop = new Drop();
    }

    public Alter getAlter() {
        return alter;
    }

    public void setAlter(Alter alter) {
        this.alter = alter;
    }

    public Create getCreate() {
        return create;
    }

    public void setCreate(Create create) {
        this.create = create;
    }

    public Drop getDrop() {
        return drop;
    }

    public void setDrop(Drop drop) {
        this.drop = drop;
    }
}
