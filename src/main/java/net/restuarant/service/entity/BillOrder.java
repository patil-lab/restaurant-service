package net.restuarant.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.restuarant.service.dto.BillOrderDto;
import net.restuarant.service.dto.OrderItem;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@Table(name = "bill_order")
@AllArgsConstructor
@NoArgsConstructor
public class BillOrder extends BaseEntity {

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "menu_order")
	@MapKeyColumn(name = "menu_name")
	@Column(name = "menu_quantity")
	private Map<String, Integer> menuItems = new HashMap<>();

	@Column(name = "total_price")
	private BigDecimal totalPrice;


	public BillOrderDto toResource() {

		List<OrderItem> orderItems = this.menuItems.entrySet()
				.stream()
				.map(m -> new OrderItem(m.getKey(), m.getValue()))
				.collect(Collectors.toList());


		BillOrderDto billOrderDto = BillOrderDto.builder()
				.orderItems(orderItems)
				.totalPrice(this.totalPrice)
				.build();
		billOrderDto.setId(this.id);
		billOrderDto.setCreateTime(this.createTime);
		billOrderDto.setUpdateTime(this.updateTime);
		return billOrderDto;
	}
}
