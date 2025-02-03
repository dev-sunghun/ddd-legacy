package kitchenpos.application;

import kitchenpos.mock.client.FakePurgomalumClient;
import kitchenpos.mock.persistence.FakeMenuRepository;
import kitchenpos.mock.persistence.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.client.RestTemplateBuilder;

class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        FakeProductRepository productRepository = new FakeProductRepository();
        FakeMenuRepository menuRepository = new FakeMenuRepository();
        FakePurgomalumClient purgomalumClient = new FakePurgomalumClient(new RestTemplateBuilder());
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
    }

}
