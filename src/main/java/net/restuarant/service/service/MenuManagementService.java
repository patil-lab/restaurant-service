package net.restuarant.service.service;

import com.google.gson.Gson;
import com.querydsl.jpa.impl.JPAQuery;
import net.restuarant.service.constants.IngreDient;
import net.restuarant.service.dto.ListResource;
import net.restuarant.service.dto.MenuItemDto;
import net.restuarant.service.entity.MenuItem;
import net.restuarant.service.entity.QMenuItem;
import net.restuarant.service.exception.ItemNotFoundException;
import net.restuarant.service.repository.MenuManagementRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuManagementService {

	@Autowired
	private MenuManagementRepository menuManagementRepository;
	@Autowired
	private EntityManager entityManager;

	public static final String ITEM_NOT_FOUND = "Item	Not	Found";

	public static final String ITEM_EXISTS = "Item	exits";

	public MenuItemDto createMenuItem(MenuItemDto menuItemDto, List<IngreDient> ingreDients) throws ItemNotFoundException {

		Optional<MenuItem> optional = menuManagementRepository.findByName(menuItemDto.getName());
		if (optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_EXISTS);
		}
		List<String> stringList = ingreDients.stream().map(Enum::toString).collect(Collectors.toList());
		String[] stringsEnum = stringList.toArray(new String[ingreDients.size()]);
		menuItemDto.setIngredients(stringsEnum);
		MenuItem saveMenuItem = MenuItem.fromResource(menuItemDto);
		saveMenuItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
		saveMenuItem.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		MenuItem menuItem = menuManagementRepository.save(saveMenuItem);

		return menuItem.toResource();
	}

	public MenuItemDto updateMenuItem(MenuItemDto menuItemDto, List<IngreDient> ingreDients, Long id) throws ItemNotFoundException {

		Optional<MenuItem> optional = menuManagementRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND);
		}
		MenuItem menuItemDb = optional.get();
		if (menuItemDb.getId().equals(id)) {
			menuItemDb.setName(menuItemDto.getName());
			menuItemDb.setDescription(menuItemDto.getDescription());
			menuItemDb.setImage(menuItemDto.getImage());
			menuItemDb.setPrice(menuItemDto.getPrice());
			List<String> stringList = ingreDients.stream().map(Enum::toString).collect(Collectors.toList());
			String[] stringsEnum = stringList.toArray(new String[ingreDients.size()]);
			menuItemDb.setIngredients(new Gson().toJson(stringsEnum));
			menuItemDb.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		}
		menuManagementRepository.save(menuItemDb);

		return menuItemDb.toResource();
	}

	public ListResource<MenuItemDto> getAllMenuItem(String name, String description, List<IngreDient> ingredients, int currentPage, int perPage) {
		JPAQuery<MenuItem> query = getQuery();
		QMenuItem menuItem = QMenuItem.menuItem;
		query.from(menuItem);
		List<String> stringList = new ArrayList<>();
		if (ingredients != null) {
			stringList = ingredients.stream().map(Enum::toString).collect(Collectors.toList());
		}

		if (!StringUtils.isBlank(name))
			query.where(menuItem.name.contains(name));
		if (!StringUtils.isBlank(description))
			query.where(menuItem.description.contains(description));

		if (!CollectionUtils.isEmpty(ingredients)) {
			query.where(menuItem.ingredients.in(stringList));
		}

		query.offset((currentPage - 1) * perPage).limit(perPage);
		List<MenuItem> list = query.fetch();
		long totalPages = ((query.fetchCount() - 1) / perPage) + 1;
		return ListResource.<MenuItemDto>builder().
				content(list.stream().map(MenuItem::toResource).collect(Collectors.toList())).
				totalPage(Math.toIntExact(totalPages)).build();
	}

	public void deleteMenuItem(Long id) throws ItemNotFoundException {
		Optional<MenuItem> optional = menuManagementRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND);
		}
		menuManagementRepository.deleteById(id);
	}

	public BigDecimal getPriceOfMenuItem(String name) throws ItemNotFoundException {
		Optional<MenuItem> optional = menuManagementRepository.findByName(name);
		if (!optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND);
		}

		return optional.get().getPrice();
	}

	JPAQuery<MenuItem> getQuery() {
		return new JPAQuery<>(entityManager);
	}
}
