package com.re.bt3.controller;

import com.re.bt3.model.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class FoodController {
    private static final String UPLOAD_DIR = "C:/RikkeiFood_Temp/";
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png");
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    public static final List<Food> FOODS = Collections.synchronizedList(new ArrayList<>());

    @GetMapping({"/", "/foods/new"})
    public String showCreateForm(Model model) {
        prepareForm(model);
        return "food-form";
    }

    @PostMapping("/foods")
    public String createFood(@RequestParam("name") String name,
                             @RequestParam("category") String category,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam("image") MultipartFile file,
                             Model model) throws IOException {
        if (file.isEmpty()) {
            return reject(model, "Vui lòng đính kèm ảnh món ăn.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        String contentType = file.getContentType();

        if (!ALLOWED_EXTENSIONS.contains(extension)
                || contentType == null
                || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            return reject(model, "File ảnh không hợp lệ. Chỉ chấp nhận .jpg, .jpeg, .png.");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return reject(model, "Giá tiền phải lớn hơn hoặc bằng 0.");
        }

        File uploadDirectory = new File(UPLOAD_DIR);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String storedFileName = UUID.randomUUID() + extension;
        File destination = new File(uploadDirectory, storedFileName);
        file.transferTo(destination);

        Food food = new Food(
                ID_GENERATOR.getAndIncrement(),
                name,
                category,
                price,
                description,
                storedFileName
        );
        FOODS.add(food);

        System.out.println("Mon vua them: " + food);
        System.out.println("Tong so mon hien co: " + FOODS.size());

        prepareForm(model);
        model.addAttribute("success", "Thêm món ăn thành công.");
        model.addAttribute("food", food);
        model.addAttribute("totalFoods", FOODS.size());
        return "food-form";
    }

    private String reject(Model model, String message) {
        prepareForm(model);
        model.addAttribute("error", message);
        model.addAttribute("totalFoods", FOODS.size());
        return "food-form";
    }

    private void prepareForm(Model model) {
        model.addAttribute("categories", List.of("Khai vị", "Món chính", "Đồ uống", "Tráng miệng", "Món ăn kèm"));
        model.addAttribute("totalFoods", FOODS.size());
    }

    private String getExtension(String filename) {
        if (filename == null) {
            return "";
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0) {
            return "";
        }

        return filename.substring(dotIndex).toLowerCase(Locale.ROOT);
    }
}
