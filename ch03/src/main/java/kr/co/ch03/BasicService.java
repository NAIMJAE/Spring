package kr.co.ch03;

import org.springframework.stereotype.Component;

@Component
public class BasicService {

    public void insert(){
        System.out.println("핵심 기능 - insert()...");
    }
    public void select(String id){
        System.out.println("핵심 기능 - select()...");
        if (id.equals("a101")){
            System.out.println("핵심 기능 -id : " + id);
        }
    }
    public void update(){
        System.out.println("핵심 기능 - update()...");
    }
    public void delete(){
        System.out.println("핵심 기능 - delete()...");
    }

}
