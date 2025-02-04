package kitchenpos.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.Product;
import kitchenpos.mock.fixture.MenuFixture;
import kitchenpos.mock.fixture.MenuGroupFixture;
import kitchenpos.mock.fixture.MenuProductFixture;
import kitchenpos.mock.fixture.ProductFixture;
import kitchenpos.mock.persistence.FakeMenuGroupRepository;
import kitchenpos.mock.persistence.FakeMenuRepository;
import kitchenpos.mock.persistence.FakeProductRepository;

public class TestContainer {

    public static final BigDecimal PRICE = BigDecimal.valueOf(28_000);
    public static final BigDecimal MINUS_PRICE = BigDecimal.valueOf(-100);

    private final FakeProductRepository productRepository;
    private final FakeMenuRepository menuRepository;
    private final MenuGroup defaultMenuGroup;

    public TestContainer(FakeMenuGroupRepository menuGroupRepository,
        FakeProductRepository productRepository, FakeMenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.defaultMenuGroup = menuGroupRepository.save(MenuGroupFixture.create("추천 메뉴"));
        this.menuRepository = menuRepository;
    }

    public MenuGroup getMenuGroup() {
        return defaultMenuGroup;
    }

    public Product getProduct(String name, BigDecimal price) {
        return productRepository.save(ProductFixture.create(name, price));
    }

    public Product getProduct() {
        String name = "치킨";
        BigDecimal price = BigDecimal.valueOf(20000);
        return productRepository.save(ProductFixture.create(name, price));
    }

    public List<MenuProduct> getMenuProducts(int quantity) {
        List<MenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(MenuProductFixture.create(getProduct(), quantity));
        return menuProducts;
    }

    public List<MenuProduct> getMenuProducts(Product product, int quantity) {
        List<MenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(MenuProductFixture.create(product, quantity));
        return menuProducts;
    }

    public Menu getMenu() {
        int quantity = 2;
        Menu menu = MenuFixture.create("두마리 치킨", PRICE, true, getMenuGroup(),
            getMenuProducts(quantity));
        return menuRepository.save(menu);
    }

    public OrderLineItem getOrderLineItem(int quantity) {
        Menu menu = getMenu();
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setMenu(menu);
        orderLineItem.setMenuId(menu.getId());
        orderLineItem.setQuantity(quantity);
        orderLineItem.setPrice(PRICE);
        return orderLineItem;
    }
}
