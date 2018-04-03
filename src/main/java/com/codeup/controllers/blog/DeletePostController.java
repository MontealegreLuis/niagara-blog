/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.blog;

import com.codeup.blog.actions.DeletePost;
import com.codeup.infrastructure.web.FlashMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DeletePostController {
    private DeletePost action;

    public DeletePostController(DeletePost action) {
        this.action = action;
    }

    @PostMapping("/posts/{id}/delete")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirect) {
        action.deletePostWithId(id);

        redirect.addFlashAttribute("message", FlashMessage.success("Your post has been deleted"));
        return "redirect:/posts";
    }
}
