package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
//addItemV1~V3
//    @PostMapping("/add")
//    public String save(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//        return "basic/item";
//    }
//
//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//        return "basic/item";
//    }
//
//    //모델 어트리뷰트가 해당 객체 초기화(setName,setPrice...등등) 및 model에 추가까지 해줌
//    //@ModelAttribute에 설정하는 이름을 뷰에서 사용하는 변수명과 통일해줘야함
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item,Model model) {
//        itemRepository.save(item);
//
//        model.addAttribute("item", item); 자동추가로 인해 생략
//        return "basic/item";
//    }
//
//    //모델 어트리뷰트값을 따로 설정하지 않을시에 클래스명 첫번쨰 글자를 소문자로 자동치환하여 사용됌
//    //ex.Item -> @ModelAttribute("item")과 동일하게 설정
//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item) {
//
//        itemRepository.save(item);
//        return "basic/item";
//    }
    //심지어 @ModelAttribute도 생략이 가능함
    /* 문제점
    * 현재 문제는 사용자가 새로고침시에 마지막으로 보냈던 요청인 Post요청을 다시날려서
    * 상품이 계속 등록되는 문제가 생김
    * 해결하기위해서 PRG(Post/Redirect/Get)을 도입하여
    * 뷰반환이 아닌 리다이렉트 요청을 하여 마지막요청을 Post가 아닌 새로운페이지 조회로 변경하여
    * 새로고침을 눌러도 문제가 발생하지 않게 한다
     * */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }



    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
