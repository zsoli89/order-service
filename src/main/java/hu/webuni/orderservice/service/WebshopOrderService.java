package hu.webuni.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webuni.commonlib.dto.OrderDto;
import hu.webuni.orderservice.model.dto.OrderProductDto;
import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.entity.Address;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import hu.webuni.orderservice.model.enums.OrderStatus;
import hu.webuni.orderservice.model.mapper.OrderProductMapper;
import hu.webuni.orderservice.model.mapper.WebshopOrderMapper;
import hu.webuni.orderservice.repository.WebshopOrderRepository;
import hu.webuni.webclientservice.WebClientServiceInterface;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebshopOrderService {

    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderService.class);
    private final ObjectMapper objectMapper;

    private final WebshopOrderRepository webshopOrderRepository;
    private final WebshopOrderMapper webshopOrderMapper;
    private final WebClientServiceInterface webClientService;
    private final OrderProductService orderProductService;
    private final AddressService addressService;
    private final OrderProductMapper orderProductMapper;
    private final ShippingService shippingService;

    @Transactional
    public List<WebshopOrderDto> findByUsername(String username) {
        List<WebshopOrder> webshopOrder = webshopOrderRepository.findAllWithAddress();
        List<Long> idList = webshopOrder.stream().map(WebshopOrder::getId).toList();
        webshopOrder = webshopOrderRepository.findByIdWithProducts(idList);
        webshopOrder = webshopOrder.stream()
                .filter(o -> o.getUsername().equals(username)).toList();
        logger.info("{} Webshop Order entity found by username: {}", webshopOrder.size(), username);
        return webshopOrderMapper.webshopOrderListToDtoList(webshopOrder);
    }

    @Transactional
    public WebshopOrderDto placeOrder(OrderDto orderDto) throws JsonProcessingException {
        String webClientBody = objectMapper.writeValueAsString(orderDto);
        String jsonResponse = webClientService
                .callPostMicroserviceGetString("8080", "api/warehouse/get-current-product-quantity", webClientBody);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String productPerQuantityString = String.valueOf(jsonObject.get("response"));
        Map<Long, Long> map = objectMapper.readValue(productPerQuantityString, Map.class);

        if (productPerQuantityString.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (map.size() != orderDto.getProducts().size())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (map.containsValue(0L))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return createOrder(orderDto.getAddressId(), webClientBody);
    }

    @Transactional
    public WebshopOrderDto changeOrderStatus(Long id, OrderStatus status) {
        Optional<WebshopOrder> orderOptional = webshopOrderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            logger.error("Couldn't found Webshop Order entity by id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!orderOptional.get().getOrderStatus().equals(OrderStatus.PENDING)) {
            logger.error("It is forbidden to modify status once It has been modified from PENDING");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        orderOptional.get().setOrderStatus(status);
        WebshopOrder modifiedOrder = webshopOrderRepository.save(orderOptional.get());

        if (modifiedOrder.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            String shippingAddressRequest = getAddressRequest(modifiedOrder, null);
            List<String> productListRequest = getProductListRequest(modifiedOrder);
            Address pickingUpAddress = addressService.findWebshopAdress();
            String pickingUpAddressRequest = getAddressRequest(null, pickingUpAddress);

            shippingService.entrustDeliveryOrder(modifiedOrder.getId(), shippingAddressRequest, productListRequest, pickingUpAddressRequest);
        }
        return webshopOrderMapper.webshopOrderToDto(modifiedOrder);
    }


    private WebshopOrderDto createOrder(Long addressId, String webClientBody) {
        Address address = addressService.findById(addressId);
        WebshopOrder order = webshopOrderRepository.save(WebshopOrder.builder()
                .address(address)
                .orderStatus(OrderStatus.PENDING)
                .username(MDC.get("username"))
                .build());
        List<OrderProduct> orderProductList = getDataToCreateOrderProducts(order, webClientBody);
        List<OrderProductDto> savedProductDtoList = orderProductService.createAll(orderProductList);
        Set<OrderProduct> orderProductsSet = new HashSet<>(orderProductMapper.dtoSummariesToProductList(savedProductDtoList));
        order.setOrderProducts(orderProductsSet);
        logger.info("Order and Order Products saved in repository.");
        return webshopOrderMapper.entityToDto(order);
    }


    private List<OrderProduct> getDataToCreateOrderProducts(WebshopOrder order, String webClientBody) {
        String jsonResponse = webClientService
                .callPostMicroserviceGetString("8080", "api/product/findAll/product-id-list", webClientBody);
        return createOrderProductListFromJson(jsonResponse, order);
    }

    private List<OrderProduct> createOrderProductListFromJson(String jsonResponse, WebshopOrder order) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        logger.info("Order Product Json Array list size: {}", jsonArray.length());
        JSONObject jsonObject;
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Long id = jsonObject.getLong("id");
            String brand = jsonObject.getString("brand");
            String name = jsonObject.getString("name");
            Long quantity = jsonObject.getLong("quantity");
            Double price = jsonObject.getDouble("price");
            Long size = jsonObject.getLong("size");
            String amountUnits = jsonObject.getString("amountUnits");
            String description = jsonObject.getString("description");
            String color = jsonObject.getString("color");
            Long orderedQuantity = jsonObject.getLong("orderedQuantity");
            OrderProduct product = OrderProduct.builder()
                    .orderId(order)
                    .productId(id)
                    .brand(brand)
                    .productName(name)
                    .productPcsQuantity(quantity)
                    .price(price)
                    .size(size)
                    .amountUnits(amountUnits)
                    .description(description)
                    .color(color)
                    .quantity(orderedQuantity).
                    build();
            orderProductList.add(product);
        }
        return orderProductList;
    }

    private String getAddressRequest(WebshopOrder order, Address address) {
        StringBuilder shippingAddress = new StringBuilder();
        if (order != null) {
            shippingAddress
                    .append(order.getAddress().getZip()).append(" ")
                    .append(order.getAddress().getCity()).append(" ")
                    .append(order.getAddress().getStreet()).append(" ")
                    .append(order.getAddress().getHouseNumber()).append(" ")
                    .append(order.getAddress().getDoor());
            return shippingAddress.toString();
        } else {
            shippingAddress
                    .append(address.getZip()).append(" ")
                    .append(address.getCity()).append(" ")
                    .append(address.getStreet()).append(" ")
                    .append(address.getHouseNumber()).append(" ")
                    .append(address.getDoor());
            return shippingAddress.toString();
        }
    }

    private List<String> getProductListRequest(WebshopOrder order) {
        List<String> productList = new ArrayList<>();
        for (OrderProduct product : order.getOrderProducts()) {
            StringBuilder builder = new StringBuilder();
            builder = builder
                    .append(product.getOrderId()).append(", ")
                    .append(product.getProductId()).append(", ")
                    .append(product.getQuantity()).append(", ")
                    .append(product.getPrice()).append(", ")
                    .append(product.getAmount()).append(", ")
                    .append(product.getBrand()).append(", ")
                    .append(product.getProductName()).append(", ")
                    .append(product.getProductPcsQuantity()).append(", ")
                    .append(product.getSize()).append(", ")
                    .append(product.getAmountUnits()).append(", ")
                    .append(product.getColor()).append(", ")
                    .append(product.getDescription()).append(", ")
                    .append(product.getOrderId());
            productList.add(builder.toString());
        }
        return productList;
    }

    public void updateShippingStatus(Long orderId, String status, String shippingId) {
        Optional<WebshopOrder> orderOptional = webshopOrderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            logger.error("Couldn't find Webshop Order by id {}", orderId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        orderOptional.get().setShippingId(shippingId);
        switch (status) {
            case "DELIVERED" -> orderOptional.get().setOrderStatus(OrderStatus.DELIVERED);
            case "SHIPMENT_FAILED" -> orderOptional.get().setOrderStatus(OrderStatus.SHIPMENT_FAILED);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        webshopOrderRepository.save(orderOptional.get());
    }
}
