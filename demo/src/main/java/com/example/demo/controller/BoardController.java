package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostRepository postRepository;

    // 생성자 주입
    public BoardController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "board/list";
    }

    // 새 글 작성 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new Post());
        return "board/new";
    }

    // 새 글 등록 처리
    @PostMapping
    public String create(@ModelAttribute Post post) {
        postRepository.save(post);
        return "redirect:/board";
    }

    // 상세 보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post found = postRepository.findById(id).orElse(null);
        model.addAttribute("post", found);
        return "board/detail";
    }

    // ✅ 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post found = postRepository.findById(id).orElse(null);
        model.addAttribute("post", found);
        return "board/edit"; // templates/board/edit.html
    }

    // ✅ 수정 처리
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Post form) {
        Post existing = postRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setTitle(form.getTitle());
            existing.setContent(form.getContent());
            postRepository.save(existing); // 수정된 내용 저장
        }
        return "redirect:/board/" + id;
    }

    // ✅ 삭제 처리
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/board";
    }
}
