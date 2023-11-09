package sklep.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

public class OrderProduct {
	// Ponieważ te obiekty w XML są zawsze umieszczone wewnątrz konkretnego zamówienia,
	// to nie ma sensu umieszczać informacji o id tego zamówienia.
	// Uwaga - tak można sobie upraszczać, gdy mówimy o odczycie danych (komunikacja jendokierunkowa).
	@XmlTransient
	// @XmlAttribute(name="order-id")
	private Integer orderId;
	
	@XmlAttribute(name="product-id")
	private Integer productId;
	
	private int quantity;

	@XmlElement(name="price")
	private BigDecimal actualPrice;

	public OrderProduct() {
	}

	public OrderProduct(Integer orderId, Integer productId, int quantity, BigDecimal actualPrice) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.actualPrice = actualPrice;
	}
	
	public static OrderProduct of(Integer orderId, Product product, int quantity) {
		return new OrderProduct(orderId, product.getProductId(), quantity, product.getPrice());
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	@Override
	public String toString() {
		return "OrderProduct [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity
				+ ", actualPrice=" + actualPrice+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId, quantity, actualPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProduct other = (OrderProduct) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId)
				&& quantity == other.quantity
				&& Objects.equals(actualPrice, other.actualPrice);
	}
	
}
