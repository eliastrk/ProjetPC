import java.math.BigDecimal;
import java.util.Objects;

public class Customer {
    private final String id;
    private String name;
    private String address;

    public Customer(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getAddress());
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer d = (Customer) o;
        return Objects.equals(id, d.id) && Objects.equals(name, d.name) && Objects.equals(address, d.address);
    }
}
