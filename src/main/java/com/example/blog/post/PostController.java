package com.example.blog.post;

import com.example.blog.post.exceptions.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity getAllPosts() throws PostNotFoundException{
        try{
            return new ResponseEntity(postService.getAllPosts(),HttpStatus.OK);
        }catch(PostNotFoundException ex){
            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity createPost(@RequestBody Post post) throws PostNotFoundException{
       try{
           return new ResponseEntity(postService.createPost(post),HttpStatus.OK);
       }catch(PostNotFoundException ex){
            return  new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Integer id) throws PostNotFoundException {
        try{
            return new ResponseEntity(postService.getPostById(id),HttpStatus.OK);
        }catch(PostNotFoundException ex){
            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity updatePostById(@PathVariable Integer id, @RequestBody Post post){
        try{
            return new ResponseEntity(postService.updatePostById(id,post),HttpStatus.OK);
        }catch(PostNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity deletePostById(@PathVariable Integer id)throws PostNotFoundException{
        try{
            postService.deletePostById(id);
             return new ResponseEntity("post deleted succesfullt",HttpStatus.OK);
        }catch(PostNotFoundException ex){
            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
