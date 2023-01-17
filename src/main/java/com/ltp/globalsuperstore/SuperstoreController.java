package com.ltp.globalsuperstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuperstoreController {

    private List<Item> items = new ArrayList<Item>();

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        int index = getIndexFromId(id);
        model.addAttribute("item", index == Constants.NOT_FOUND ? new Item() : items.get(index));
        model.addAttribute("categories", Constants.CATEGORIES);
        // System.out.println("id: " + id);
        return "form";
    }

    @PostMapping("/submitItem") 
    public String handleSubmit(Item item) {
        // System.out.println(item.getId());
        int index = getIndexFromId(item.getId());
        if (index == Constants.NOT_FOUND) {
            items.add(item);
        } else {
            items.set(index, item);
        }
        // items.add(item);
        return "redirect:/inventory";
    }
    
    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("items", items);
        return "inventory";
    }

    public int getIndexFromId(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

}
