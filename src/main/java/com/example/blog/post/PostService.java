package com.example.blog.post;

import com.example.blog.post.exceptions.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private static Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostDAO postDAO;
    public List<Post> getAllPosts() throws PostNotFoundException{
        List<Post> postList = postDAO.findAll();

        if(postList.isEmpty()){
            throw new PostNotFoundException("{\"message\":\"Post's list is empty\"}");
        }else{
            return postList;
        }

    }

    public Post createPost(Post post)throws PostNotFoundException {

        if(post.getDescription().isEmpty() || post.getImage().isEmpty() || post.getCategory().isEmpty()
        || post.getTitle().isEmpty() || post.getDate()==null){
            throw new PostNotFoundException("Post values cannot be empty");
        }
        else{
            return postDAO.save(post);
        }

    }



    public Post updatePostById(Integer id, Post post) throws PostNotFoundException {
        Optional<Post> postById = postDAO.findById(id);
        Post updatedPost = new Post();

        if(postById.isPresent()){
            updatedPost.setTitle(post.getTitle());
            updatedPost.setDescription(post.getCategory());
            updatedPost.setDate(post.getDate());
            updatedPost.setImage(post.getImage());
            postDAO.save(updatedPost);
            return updatedPost;
        }
        else{
            throw new PostNotFoundException("Post doesn't exist");
        }

    }

    public void deletePostById(Integer id) throws PostNotFoundException{
        Post postById;
        if(postDAO.findById(id).isEmpty()){
            throw new PostNotFoundException("Post doesnt exist");
        }
        else{
            postDAO.deleteById(id);
        }
    }

    public Post getPostById(Integer id) throws PostNotFoundException {
        Post postById;
        if(postDAO.findById(id).isEmpty()){
            throw new PostNotFoundException("{\"message\" : \"Post dont exist\"}");
        }else{
            postById = postDAO.findById(id).get();
        }
        return postById;
    }
}

