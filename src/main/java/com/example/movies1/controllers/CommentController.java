package com.example.movies1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movies1.models.Comment;
import com.example.movies1.services.CommentService;
import com.example.movies1.services.MovieService;
import com.example.movies1.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CommentController {

    @Autowired
    private MovieService mServ;

    @Autowired
    private UserService uServ;

    @Autowired
    private CommentService cServ;

	@PostMapping("/addComment")
	public String addComment(
	        @Valid @ModelAttribute("commentForm") Comment comment, 
	        BindingResult result,
	        @RequestParam("movieId") Long movieId, 
	        HttpSession session, 
	        RedirectAttributes redirectAttributes) {

	    if (result.hasErrors()) {
	        redirectAttributes.addFlashAttribute("error", "Please enter a valid comment.");
	        return "redirect:/view/movies" + movieId;
	    }

	    Long userId = (Long) session.getAttribute("userId");
	    comment.setUser(uServ.getUserById(userId));
	    comment.setMovie(mServ.getOneMovie(movieId));
	    cServ.createComment(comment);

	    return "redirect:/view/movies/" + movieId;
	}


	@DeleteMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable("commentId") Long commentId, HttpSession session) {

		Comment comment = cServ.getCommentById(commentId);
		Long sessionUserId = (Long) session.getAttribute("userId");
		Long commentUserId = comment.getUser().getId();

		if (!commentUserId.equals(sessionUserId)) {
			return "redirect:/unauthorizedUser";
		}

		cServ.deleteComment(commentId);
		return "redirect:/view/movies/" + comment.getMovie().getId();
	}




    @GetMapping("/editComment/{commentId}")
    public String editComment(@PathVariable("commentId") Long commentId, Model model, HttpSession session) {
        Comment comment = cServ.getCommentById(commentId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        // Check if comment exists, user is logged in, and owns the comment
        if (comment == null || sessionUserId == null || !comment.getUser().getId().equals(sessionUserId)) {
            return "redirect:/unauthorizedUser";
        }

        model.addAttribute("commentForm", comment);
        return "editComment";
    }


    @PostMapping("/movies/{movieId}/comments/update/{commentId}")
    public String updateComment(
            @Valid @ModelAttribute("commentForm") Comment updatedComment,
            BindingResult result,
            @PathVariable("commentId") Long commentId,
            @PathVariable("movieId") Long movieId,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            return "editComment";
        }

        Comment comment = cServ.getCommentById(commentId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (sessionUserId == null || !comment.getUser().getId().equals(sessionUserId)) {
            return "redirect:/unauthorizedUser";
        }

        comment.setText(updatedComment.getText());
        cServ.updateComment(comment); // Assuming updateComment exists

        return "redirect:/view/movies/" + movieId;
    }


}
