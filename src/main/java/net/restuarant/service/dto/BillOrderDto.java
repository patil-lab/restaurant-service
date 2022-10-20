package net.restuarant.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillOrderDto extends BaseResource {

	private List<OrderItem> orderItems;

	@ApiModelProperty(readOnly = true, value = "totalPrice")
	private BigDecimal totalPrice;
}

