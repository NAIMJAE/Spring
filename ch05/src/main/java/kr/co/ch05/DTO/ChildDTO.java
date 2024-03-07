package kr.co.ch05.DTO;

public class ChildDTO {
    private String cid;
    private String name;
    private String hp;
    private String parent;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ChildDTO{" +
                "cid='" + cid + '\'' +
                ", name='" + name + '\'' +
                ", hp='" + hp + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
