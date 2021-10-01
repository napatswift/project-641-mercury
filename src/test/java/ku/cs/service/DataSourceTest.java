package ku.cs.service;

import ku.cs.models.ProductList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceTest {

    @Test
    void testParsingProduct(){
        DataSource dataSource = new DataSource("data");
        dataSource.parseProduct();
        ProductList products = dataSource.getProducts();
        assertEquals(8, products.size());
        dataSource.saveCategory();
    }

}