package hello.itemservice.web.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                      @RequestParam int price,
                        @RequestParam Integer quantity,
                        Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(quantity);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

    //@ModelAttribute 사용하면 Model.addAttribute도 생략가능한지 처음 앎,,,
    //@ModelAttribute("item")으로 받으면 받은 값들이 "item"으로 들어가기때문에 바꾸면 안된다.
    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        //이름을 정하지않고 받으면 클래스객체의 이름 앞글자를 소문자로 바꿔 받는다.
        //Item -> item 그리고 바로 addAttribute 자동추가된다.

        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     *  @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     * @param item
     * @return
     */
    // @PostMapping("/add")
    public String addItemV4(Item item){
        //Item -> item
        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV5(Item item){
        //Item -> item
        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능
        return "redirect:/basic/items/"+item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}
