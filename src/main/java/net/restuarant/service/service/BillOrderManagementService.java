package net.restuarant.service.service;

import com.querydsl.jpa.impl.JPAQuery;
import net.restuarant.service.dto.BillOrderDto;
import net.restuarant.service.dto.ListResource;
import net.restuarant.service.dto.OrderItem;
import net.restuarant.service.entity.BillOrder;
import net.restuarant.service.entity.QBillOrder;
import net.restuarant.service.exception.ItemNotFoundException;
import net.restuarant.service.repository.BillOrderManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillOrderManagementService {

	@Autowired
	private BillOrderManagementRepository billOrderManagementRepository;

	@Autowired
	private MenuManagementService menuManagementService;

	@Autowired
	private EntityManager entityManager;

	public static final String ITEM_NOT_FOUND = "Item	not	found";

	public BillOrderDto createBillOrder(BillOrderDto billOrderDto) throws ItemNotFoundException {

		List<OrderItem> orderItems = billOrderDto.getOrderItems();
		Map<String, Integer> mapOfMenuItems = lisToMap(orderItems);

		BigDecimal totalPrice = getTotalPrice(orderItems);
		BillOrder billOrder = BillOrder.builder().menuItems(mapOfMenuItems).totalPrice(totalPrice).build();
		billOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
		billOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		billOrderManagementRepository.save(billOrder);
		return billOrder.toResource();
	}

	private BigDecimal getTotalPrice(List<OrderItem> orderItems) throws ItemNotFoundException {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (OrderItem o : orderItems) {
			totalPrice = totalPrice.add(menuManagementService.getPriceOfMenuItem(o.getMenuItem()).multiply(new BigDecimal(o.getQuantity())));
		}
		return totalPrice;
	}

	private Map<String, Integer> lisToMap(List<OrderItem> orderItems) {
		return orderItems.stream().collect(
				Collectors.toMap(OrderItem::getMenuItem, OrderItem::getQuantity));
	}

	public BillOrderDto updateBillOrder(BillOrderDto billOrderDto, Long id) throws ItemNotFoundException {

		Optional<BillOrder> optional = billOrderManagementRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND);
		}
		BillOrder billOrder = optional.get();
		if (billOrder.getId().equals(id)) {
			List<OrderItem> orderItems = billOrderDto.getOrderItems();
			Map<String, Integer> mapOfMenuItems = lisToMap(orderItems);

			BigDecimal totalPrice = getTotalPrice(orderItems);
			billOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			billOrder.setTotalPrice(totalPrice);
			billOrder.setMenuItems(mapOfMenuItems);
		}
		billOrderManagementRepository.save(billOrder);

		return billOrder.toResource();
	}

	public List<OrderItem> getMenuItems(Long id) throws ItemNotFoundException {

		BillOrderDto billOrderDto = getBillOrderDto(id);
		return billOrderDto.getOrderItems();
	}


	public BigDecimal getTotalPrice(Long id) throws ItemNotFoundException {
		BillOrderDto billOrderDto = getBillOrderDto(id);

		return billOrderDto.getTotalPrice();
	}

	private BillOrderDto getBillOrderDto(Long id) throws ItemNotFoundException {
		Optional<BillOrder> optional = billOrderManagementRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND);
		}
		return optional.get().toResource();
	}

	public ListResource<BillOrderDto> getAllMenuItem(Long id, int currentPage, int perPage) {
		JPAQuery<BillOrder> query = getQuery();
		QBillOrder billOrder = QBillOrder.billOrder;
		query.from(billOrder);


		if (id != null)
			query.where(billOrder.id.eq(id));

		query.offset((currentPage - 1) * perPage).limit(perPage);
		List<BillOrder> list = query.fetch();
		long totalPages = ((query.fetchCount() - 1) / perPage) + 1;
		return ListResource.<BillOrderDto>builder().
				content(list.stream().map(BillOrder::toResource).collect(Collectors.toList())).
				totalPage(Math.toIntExact(totalPages)).build();
	}


	JPAQuery<BillOrder> getQuery() {
		return new JPAQuery<>(entityManager);
	}
}
