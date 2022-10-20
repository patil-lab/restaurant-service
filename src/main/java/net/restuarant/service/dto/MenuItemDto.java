package net.restuarant.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemDto	extends	BaseResource {

	private	String	name;

	private	String	description;

	private String	image;

	private BigDecimal	price;

	@JsonProperty(value = "ingredients")
	@ApiModelProperty(value = "ingredients", example = "Italian", readOnly = true)
	String[]	ingredients;

}
