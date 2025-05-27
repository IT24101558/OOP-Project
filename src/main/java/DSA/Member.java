package DSA;

import java.util.Objects;

public class Member {
    private String id;
    private String name;
    private String renewalDue; // Format: YYYY-MM-DD

    // Default constructor for Jackson deserialization
    public Member() {
    }

    public Member(String id, String name, String renewalDue) {
        this.id = id;
        this.name = name;
        this.renewalDue = renewalDue;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getRenewalDue() { return renewalDue; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRenewalDue(String renewalDue) { this.renewalDue = renewalDue; }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", renewalDue='" + renewalDue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(name, member.name) &&
                Objects.equals(renewalDue, member.renewalDue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, renewalDue);
    }
}
