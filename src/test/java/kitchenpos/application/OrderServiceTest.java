package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderType;
import kitchenpos.mock.TestContainer;
import kitchenpos.mock.client.FakeKitchenridersClient;
import kitchenpos.mock.fixture.OrderFixture;
import kitchenpos.mock.persistence.FakeMenuGroupRepository;
import kitchenpos.mock.persistence.FakeMenuRepository;
import kitchenpos.mock.persistence.FakeOrderRepository;
import kitchenpos.mock.persistence.FakeOrderTableRepository;
import kitchenpos.mock.persistence.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
            menuRepository);
    }

    @DisplayName("배달 주문을 생성할 수 있다.")
    @Test
    void create() {
        // given
        OrderType orderType = OrderType.DELIVERY;
        List<OrderLineItem> orderLineItems = List.of(
            testContainer.getOrderLineItem(1));
        String deliveryAddress = "서울시 주소";
        OrderTable orderTable = null;
        Order orderRequest = OrderFixture.create(orderType, orderLineItems, deliveryAddress,
            orderTable);

        // when
        Order savedOrder = orderService.create(orderRequest);

        // then
        assertThat(savedOrder.getType()).isEqualTo(orderType);
    }
}
