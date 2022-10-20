package net.restuarant.service.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.restuarant.service.dto.MenuItemDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "menu_item")
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem extends BaseEntity{

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "image_url")
	private String image;

	@Column(name = "price")
	private BigDecimal price;

	@Lob
	@Column(name = "ingredients")
	private String ingredients;


	public static MenuItem fromResource(MenuItemDto menuItemDto) {
		return MenuItem.builder()
				.name(menuItemDto.getName())
				.description(menuItemDto.getDescription())
				.image(menuItemDto.getImage())
				.price(menuItemDto.getPrice())
				.ingredients(new Gson().toJson(menuItemDto.getIngredients()))
				.build();

	}

	public MenuItemDto toResource() {
		MenuItemDto dto = MenuItemDto.builder()
				.ingredients(new Gson().fromJson(this.ingredients, String[].class))
				.name(this.name)
				.description(this.description)
				.image(this.image)
				.price(this.price)
				.build();
		dto.setId(this.id);
		dto.setCreateTime(this.createTime);
		dto.setUpdateTime(this.updateTime);
		return dto;
	}
}
