package kitchenpos.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import kitchenpos.mock.fixture.MenuGroupFixture;
import kitchenpos.mock.fixture.MenuProductFixture;
import kitchenpos.mock.fixture.ProductFixture;
import kitchenpos.mock.persistence.FakeMenuGroupRepository;
import kitchenpos.mock.persistence.FakeProductRepository;

public class TestContainer {

    public static final BigDecimal PRICE = BigDecimal.valueOf(28_000);
    public static final BigDecimal MINUS_PRICE = BigDecimal.valueOf(-100);

    private final FakeProductRepository productRepository;
    private final MenuGroup defaultMenuGroup;

    public TestContainer(FakeMenuGroupRepository menuGroupRepository,
        FakeProductRepository productRepository) {
        this.productRepository = productRepository;
        this.defaultMenuGroup = menuGroupRepository.save(MenuGroupFixture.create("추천 메뉴"));
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
}
