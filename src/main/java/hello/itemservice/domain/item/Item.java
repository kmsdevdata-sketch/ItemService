package hello.itemservice.domain.item;

import lombok.Data;

//원래는 @Data쓰는건 위험함 예제니까 그냥 사용
@Data
public class Item {

    private Long id;
    private String itemName;

    //null이 들어갈수도 있으니 Integer사용
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
