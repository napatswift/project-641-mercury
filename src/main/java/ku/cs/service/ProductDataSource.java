package ku.cs.service;

import ku.cs.models.CsvReader;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.Store;

import java.io.IOException;
import java.util.Arrays;

public class ProductDataSource {
    private String filePath;
    private String header;
    private ProductList productList;

    public ProductDataSource(String filePath){
        this.filePath = filePath;
    }

    public void parse(String sep) throws IOException {
        ProductList productList = new ProductList();
        String[] lines = CsvReader.getLinesWithHeader(filePath);
        String[] header = lines[0].split(sep);
        lines = Arrays.copyOfRange(lines, 1, lines.length);
        for (String line : lines) {
            String[] entry = line.split(sep);
            int entry_len = entry.length;

            //name,picturePath,details,price,stock,id,rating,review,rolloutDate
            String name = entry[0];
            String id = entry[1];
            double price = Double.parseDouble(entry[2]) / 100;
            Store store = new Store(entry[3]);
            int stock = Integer.parseInt(entry[4]);
            String details = entry[5];
            double rating = Double.parseDouble(entry[6]);
            int review = Integer.parseInt(entry[7]);
            String picturePath = entry[8];
            String rolloutDate = entry[9];

            Product newProduct =
                    new Product(name, picturePath, details,
                            price, stock, id, rating, review, rolloutDate, store);
            for (int idx = 10; idx < entry_len; idx++) {
                String[] col = header[idx].split("-");
                newProduct.addSubCategory(col[0], col[1], entry[idx]);
            }
            productList.addProduct(newProduct);
        }
        this.productList = productList;
    }

    public ProductList getProductList() {
        return productList;
    }
}
