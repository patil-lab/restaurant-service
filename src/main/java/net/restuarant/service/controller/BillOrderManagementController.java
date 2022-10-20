package net.restuarant.service.controller;

import io.swagger.annotations.ApiOperation;
import net.restuarant.service.dto.BillOrderDto;
import net.restuarant.service.dto.ListResource;
import net.restuarant.service.dto.OrderItem;
import net.restuarant.service.exception.ItemNotFoundException;
import net.restuarant.service.service.BillOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class BillOrderManagementController {

	@Autowired
	private BillOrderManagementService billOrderManagementService;

	@ApiOperation(value = "Create Order ")
	@PostMapping(value = "/billOrder")
	public ResponseEntity<BillOrderDto> createBillOrder(@Valid @RequestBody BillOrderDto billOrderDto) throws ItemNotFoundException {

		BillOrderDto billOrder = billOrderManagementService.createBillOrder(billOrderDto);
		return ResponseEntity.ok(billOrder);
	}

	@ApiOperation(value = "Update	Order")
	@PutMapping(value = "/billOrder/{id}")
	public ResponseEntity<BillOrderDto> updateBillOrder(@Valid @RequestBody BillOrderDto billOrderDto, @PathVariable("id") Long id) throws ItemNotFoundException {

		BillOrderDto updateBillOrder = billOrderManagementService.updateBillOrder(billOrderDto, id);

		return ResponseEntity.ok(updateBillOrder);
	}

	@ApiOperation(value = "Get	Menu	Items")
	@GetMapping(value = "/billOrder/menu/{id}")
	public ResponseEntity<List<OrderItem>> getMenuItems(@PathVariable("id") Long id) throws ItemNotFoundException {

		List<OrderItem> menuItems = billOrderManagementService.getMenuItems(id);

		return ResponseEntity.ok(menuItems);
	}


	@ApiOperation(value = "Get	Total	Price	of	Items")
	@GetMapping(value = "/billOrder/price/{id}")
	public ResponseEntity<BigDecimal> getTotalPriceOfItems(@PathVariable("id") Long id) throws ItemNotFoundException {

		BigDecimal totalPrice = billOrderManagementService.getTotalPrice(id);

		return ResponseEntity.ok(totalPrice);
	}

	@ApiOperation(value = "Get	All BillOrders")
	@GetMapping(value = "/billOrder/all")
	public ResponseEntity<ListResource<BillOrderDto>> getTotalPriceOfItems(@RequestParam(required = false) Long id, @RequestParam int currentPage, @RequestParam int perPage) {

		ListResource<BillOrderDto> allMenuItem = billOrderManagementService.getAllMenuItem(id, currentPage, perPage);

		return ResponseEntity.ok(allMenuItem);
	}


}
