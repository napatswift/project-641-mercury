package ku.cs.models.report;

import ku.cs.models.Product;

public class ProductReport extends Report{
    private Product product;
    public enum Type {ANIMAL_ABUSE, HATE_SPEECH, VIOLENCE}
    private Type type;

    public ProductReport(Product product, String detail, Type type){
        this.product = product;
        this.type = type;
        setDetail(detail);
    }

    public Product getProduct() {
        return product;
    }

    public Type getType() {
        return type;
    }
}
