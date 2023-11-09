package sklep.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="products")
public class ProductList {
    @XmlElement(name="product")
    private final List<Product> products = new ArrayList<>();

    public ProductList() {
        // zostawia pustą listę
    }

    public ProductList(Collection<Product> products) {
        this.products.addAll(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public void setProducts(Collection<Product> products) {
        this.products.clear();
        this.products.addAll(products);
    }

    @Override
    public String toString() {
        return this.products.toString();
    }
}


