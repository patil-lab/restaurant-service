package net.restuarant.service.controller;

import io.swagger.annotations.ApiOperation;
import net.restuarant.service.constants.IngreDient;
import net.restuarant.service.dto.ListResource;
import net.restuarant.service.dto.MenuItemDto;
import net.restuarant.service.exception.ItemNotFoundException;
import net.restuarant.service.service.MenuManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${server.version}")
public class MenuManagementController {

	@Autowired
	private MenuManagementService menuManagementService;

	@ApiOperation(value = "Create Menu Item")
	@PostMapping(value = "/menuItem")
	public ResponseEntity<MenuItemDto> createMenuItem(@Valid @RequestBody MenuItemDto menuItemDto, @RequestParam List<IngreDient>	ingreDients)	throws ItemNotFoundException {

		MenuItemDto itemDto = menuManagementService.createMenuItem(menuItemDto,ingreDients);
		return ResponseEntity.ok(itemDto);
	}

	@ApiOperation(value = "Update Menu	Item")
	@PutMapping(value = "/menuItem/{id}")
	public ResponseEntity<MenuItemDto> updateMenuItem(@RequestBody @Valid MenuItemDto menuItemDto,@RequestParam List<IngreDient>	ingreDients, @PathVariable	Long	id)
			throws ItemNotFoundException {

		MenuItemDto itemDto = menuManagementService.updateMenuItem(menuItemDto,ingreDients,id);

		return ResponseEntity.ok(itemDto);

	}

	@ApiOperation(value = "Get	Items	by	name,description,ingredients")
	@GetMapping(value = "/menuItem/all")
	public ResponseEntity<ListResource<MenuItemDto>> getMenuItems(@RequestParam(required = false) String name
			, @RequestParam(required = false) String description
			, @RequestParam(required = false) List<IngreDient> ingreDient
			, @RequestParam int currentPage
			, @RequestParam int perPage) {

		ListResource<MenuItemDto> menuItem = menuManagementService.getAllMenuItem(name, description, ingreDient, currentPage, perPage);

		return ResponseEntity.ok(menuItem);
	}

	@ApiOperation(value = "Delete	menu	Item	by 	id")
	@DeleteMapping(value = "/menuItem/{id}")
	public	ResponseEntity<String>	deleteMenuItem(@PathVariable	Long	id)	throws ItemNotFoundException{

		menuManagementService.deleteMenuItem(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
