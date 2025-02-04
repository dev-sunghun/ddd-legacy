package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderType;
import kitchenpos.mock.TestContainer;
import kitchenpos.mock.client.FakeKitchenridersClient;
import kitchenpos.mock.fixture.MenuFixture;
import kitchenpos.mock.fixture.OrderFixture;
import kitchenpos.mock.persistence.FakeMenuGroupRepository;
import kitchenpos.mock.persistence.FakeMenuRepository;
import kitchenpos.mock.persistence.FakeOrderRepository;
import kitchenpos.mock.persistence.FakeOrderTableRepository;
import kitchenpos.mock.persistence.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class OrderServiceTest {

    private OrderService orderService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        FakeOrderRepository orderRepository = new FakeOrderRepository();
        FakeMenuRepository menuRepository = new FakeMenuRepository();
        FakeOrderTableRepository orderTableRepository = new FakeOrderTableRepository();
        FakeKitchenridersClient kitchenridersClient = new FakeKitchenridersClient();
        FakeMenuGroupRepository menuGroupRepository = new FakeMenuGroupRepository();
        FakeProductRepository productRepository = new FakeProductRepository();
        this.orderService = new OrderService(orderRepository, menuRepository, orderTableRepository,
            kitchenridersClient);
        this.testContainer = new TestContainer(menuGroupRepository, productRepository,
            menuRepository, orderTableRepository);
    }

    @DisplayName("주문 유형 없이 주문 생성 시, 예외가 발생한다.")
    @Test
    void createTypeException() {
        // given
        Order order = OrderFixture.create(null, testContainer.getOrderLineItems(),
            null,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 없이 주문 생성 시, 예외가 발생한다.")
    @Test
    void createOrderLineItemsException() {
        // given
        Order order = OrderFixture.create(OrderType.DELIVERY, null,
            null,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목과 메뉴 개수가 불일치하게 주문 생성 시, 예외가 발생한다.")
    @Test
    void createSizeException() {
        // given
        List<OrderLineItem> orderLineItems = testContainer.getOrderLineItems();
        orderLineItems.add(new OrderLineItem());
        Order order = OrderFixture.create(OrderType.DELIVERY, orderLineItems,
            null,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨김 처리된 메뉴를 주문 시, 예외가 발생한다.")
    @Test
    void createMenuHideException() {
        // given
        Menu menu = testContainer.getSavedMenu(
            MenuFixture.create("두마리 치킨", BigDecimal.valueOf(28_000), false,
                testContainer.getSavedMenuGroup(),
                testContainer.getMenuProducts(1)));
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(testContainer.getOrderLineItem(menu));
        Order order = OrderFixture.create(OrderType.DELIVERY, orderLineItems,
            null,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 항목 가격과 메뉴 가격이 불일치하면, 예외가 발생한다.")
    @Test
    void createMenuPriceException() {
        // given
        Menu menu = testContainer.getSavedMenu();
        OrderLineItem orderLineItem = testContainer.getOrderLineItem(menu);
        orderLineItem.setPrice(BigDecimal.ZERO);
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem);

        Order order = OrderFixture.create(OrderType.DELIVERY, orderLineItems,
            null,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목을 마이너스 개수로 주문 생성 시, 예외가 발생한다.")
    @Test
    void createOrderLineItemQuantityException() {
        // given
        int quantity = -1;
        OrderLineItem minusOrderLineItem = testContainer.getOrderLineItem(quantity);
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(minusOrderLineItem);
        Order order = OrderFixture.create(OrderType.DELIVERY, orderLineItems,
            null,
            testContainer.getSavedOrderTable());

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주문을 생성할 수 있다.")
    @Test
    void createDelivery() {
        // given
        Order order = createDeliveryOrder();

        // when
        Order savedOrder = orderService.create(order);

        // then
        assertThat(savedOrder.getType()).isEqualTo(order.getType());
        assertThat(savedOrder.getOrderLineItems().size()).isEqualTo(
            order.getOrderLineItems().size());
        assertThat(savedOrder.getDeliveryAddress()).isEqualTo(order.getDeliveryAddress());
    }

    @DisplayName("배달 주소 없이 배달 주문 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    @EmptySource
    void createDeliveryAddressException(String deliveryAddress) {
        // given
        Order order = OrderFixture.create(OrderType.DELIVERY, testContainer.getOrderLineItems(),
            deliveryAddress,
            null);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 식사 주문을 생성할 수 있다.")
    @Test
    void createEatIn() {
        // given
        Order order = createEatInOrder();

        // when
        Order savedOrder = orderService.create(order);

        // then
        assertThat(savedOrder.getType()).isEqualTo(order.getType());
        assertThat(savedOrder.getOrderLineItems().size()).isEqualTo(
            order.getOrderLineItems().size());
        assertThat(savedOrder.getOrderTable().getId()).isEqualTo(order.getOrderTable().getId());
    }

    @DisplayName("착석 안된 테이블로 매장 식사 주문 생성 시, 예외가 발생한다.")
    @Test
    void createOrderTableNotOccupiedException() {
        // given
        OrderTable orderTable = testContainer.getSavedOrderTable();
        orderTable.setOccupied(false);
        Order order = OrderFixture.create(OrderType.EAT_IN, testContainer.getOrderLineItems(), null,
            orderTable);

        // when then
        assertThatThrownBy(() -> orderService.create(order))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("테이크아웃 주문을 생성할 수 있다.")
    @Test
    void createTakeout() {
        // given
        Order order = createTakeoutOrder();

        // when
        Order savedOrder = orderService.create(order);

        // then
        assertThat(savedOrder.getType()).isEqualTo(order.getType());
        assertThat(savedOrder.getOrderLineItems().size()).isEqualTo(
            order.getOrderLineItems().size());
    }

    private Order createDeliveryOrder() {
        String deliveryAddress = "서울시 주소";
        return OrderFixture.create(OrderType.DELIVERY, testContainer.getOrderLineItems(),
            deliveryAddress,
            null);
    }

    private Order createEatInOrder() {
        OrderTable orderTable = testContainer.getSavedOrderTable();
        return OrderFixture.create(OrderType.EAT_IN, testContainer.getOrderLineItems(), null,
            orderTable);
    }

    private Order createTakeoutOrder() {
        return OrderFixture.create(OrderType.TAKEOUT, testContainer.getOrderLineItems(),
            null,
            null);
    }
}
