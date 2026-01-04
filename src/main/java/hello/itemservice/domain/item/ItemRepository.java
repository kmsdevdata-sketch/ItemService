package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    //멀티 스레딩 환경 고려시에는 ConcurrentHashMap,AtomicLong 을 원래는 사용해야됌
    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static Long sequence = 0L; //static

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        //정석적으로는 DTO객체 생성해서 집어넣는게 맞음(cause:getId는 사용안하나?)
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
